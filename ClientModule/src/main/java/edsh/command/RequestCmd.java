package edsh.command;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.CommandHelper;
import edsh.helpers.ConsolePrinter;
import edsh.helpers.MyScanner;
import edsh.helpers.Printer;
import edsh.mainclasses.Event;
import edsh.mainclasses.MainclassFactory;
import edsh.mainclasses.Ticket;
import edsh.network.AvailableCommand;
import edsh.network.AvailableCommand.*;
import edsh.network.Request;

import java.util.Collections;

public class RequestCmd extends AbstractCommandBase implements RequestCommand {
    private final AttachedObject attachment;
    private final ArgType[] argChecks;
    private final CommandHelper.Holder holder;

    public RequestCmd(AvailableCommand availableCommand, CommandHelper.Holder h) {
        super(availableCommand.getName(), availableCommand.getDescription());
        this.attachment = availableCommand.getAttachment();
        this.argChecks = availableCommand.getArgs();
        this.holder = h;
    }

    @Override
    public Request createRequest(String[] args) {
        Printer printer = new ConsolePrinter();

        int argError = checkArgs(args, printer);
        if(argError < 0) return null;
        else if(argError > 0) {
            String commandLine = getName() + " " + String.join(" ", args);
            int spaceCount = getName().length() + 1;
            for (int i = 0; i < argError-1; i++) {
                spaceCount += args[i].length();
            }
            int arrowCount = Math.max(args[argError-1].length(), 1);
            String errPointer = String.join("", Collections.nCopies(spaceCount, " ")) +
                    String.join("", Collections.nCopies(arrowCount, "^"));
            printer.errPrintln(commandLine);
            printer.errPrintln(errPointer);
            return null;
        }

        Request.RequestBuilder requestBuilder = Request.builder()
                .command(getName())
                .args(args);


        MainclassFactory factory = null;
        switch (attachment) {
            case NONE -> {
                return requestBuilder.build();
            }

            case TICKET -> factory = Ticket.getFactory();
            case EVENT -> factory = Event.getFactory();
        }

        try {
            requestBuilder.attachment(factory.create(holder.getScanner()));
        } catch (WrongFieldException e) {
            return null;
        }

        return requestBuilder.build();
    }

    /**
     * Проверяет корректность введённых аргументов
     * @param args аргументы для проверки
     * @return позиция ошибочного аргумента, 0 если ошибок нет, -1 если аргументов мало
     */
    private int checkArgs(String[] args, Printer printer) {
        if(args.length < argChecks.length) {
            printer.errPrintln("Слишком мало аргументов");
            return -1;
        }

        for(int i = 0; i < argChecks.length; i++) {
            switch (argChecks[i]) {
                case STRING -> {
                    if(args[i].isEmpty()) {
                        printer.errPrintln("Строка не должна быть пустой");
                        return i+1;
                    }
                }
                case INTEGER -> {
                    try {
                        Integer.parseInt(args[i]);
                    } catch (NumberFormatException e) {
                        printer.errPrintln("Ожидаемый тип арумента: Integer");
                        return i+1;
                    }
                }
                case LONG -> {
                    try {
                        Long.parseLong(args[i]);
                    } catch (NumberFormatException e) {
                        printer.errPrintln("Ожидаемый тип арумента: Long");
                        return i+1;
                    }
                }
                case FLOAT -> {
                    try {
                        Float test = Float.valueOf(args[i]);
                        if(test.isNaN() || test.isInfinite()) {
                            printer.errPrintln("Число не может быть NaN или Infinity");
                            return i+1;
                        }
                    } catch (NumberFormatException e) {
                        printer.errPrintln("Ожидаемый тип арумента: Float");
                        return i+1;
                    }
                }
                case DOUBLE -> {
                    try {
                        Double test = Double.valueOf(args[i]);
                        if(test.isNaN() || test.isInfinite()) {
                            printer.errPrintln("Число не может быть NaN или Infinity");
                            return i+1;
                        }
                    } catch (NumberFormatException e) {
                        printer.errPrintln("Ожидаемый тип арумента: Double");
                        return i+1;
                    }
                }
            }
        }

        return 0;
    }
}
