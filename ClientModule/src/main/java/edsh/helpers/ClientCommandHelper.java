package edsh.helpers;

import edsh.command.RequestCommand;
import edsh.command.SimpleRequestCmd;
import edsh.network.AvailableCommand;
import edsh.network.NetworkHandler;
import edsh.network.Request;
import edsh.network.Response;

import java.util.HashMap;
import java.util.Scanner;

public class ClientCommandHelper extends CommandHelper {
    private final HashMap<String, RequestCommand> requestCommands = new HashMap<>();
    private final NetworkHandler handler;

    public ClientCommandHelper(NetworkHandler handler) {
        super(new MyScanner(new Scanner(System.in), true), null);
        this.handler = handler;
    }

    /**
     * Регистрирует данные команд с сервера, сохраняя ее имя и объект в {@link HashMap}
     */
    public void registerAvailableCommands() {
        Printer printer = new ConsolePrinter();
        Response response = handler.accept();
        if(response.getStatus() != Response.Status.AVAILABLE_COMMANDS) {
            printer.errPrintln("Некорректный пакет доспупных команд");
            handler.disconnect();
            return;
        }

        for(AvailableCommand availableCommand : response.getAvailableCommands().getCommands()) {
            RequestCommand cmd = new SimpleRequestCmd(availableCommand);
            requestCommands.put(cmd.getName(), cmd);
        }
    }

    @Override
    public boolean executeCommand(String commandStr, String[] args, Printer printer) {
        if(!requestCommands.containsKey(commandStr) || !handler.isConnected()) {
            return super.executeCommand(commandStr, args, printer);
        }
        RequestCommand command = requestCommands.get(commandStr);
        Request request = command.createRequest(args);
        if(request == null) {
            printer.errPrintln("Ошибка в генерации запроса");
            return false;
        }

        handler.send(request);
        Response response = handler.accept();
        response.print(printer);

        return true;
    }

}
