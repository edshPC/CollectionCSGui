package edsh;
import edsh.helpers.ArgsHelper;
import edsh.helpers.ClientCommandHelper;
import edsh.helpers.ConsolePrinter;
import edsh.helpers.Printer;
import edsh.network.NetworkHandler;

import java.util.HashMap;

public class RunClient
{
    public static void main( String[] args ) {

        Printer printer = new ConsolePrinter();
        HashMap<Character, String> arguments = new ArgsHelper(args).parseKeyArguments();
        String address = arguments.get('a');
        int port = -1;
        if(arguments.containsKey('p')) {
            try {
                port = Integer.parseInt(arguments.get('p'));
            } catch (NumberFormatException e) {
                printer.errPrintln("Ошибка в записи порта");
            }
        }
        NetworkHandler handler = new NetworkHandler(address, port);
        ClientCommandHelper commandHelper = new ClientCommandHelper(handler);
        commandHelper.registerAllCommands();

        if(!handler.connect() || !commandHelper.registerAvailableCommands())
            printer.println("Программа работает в оффлайн-режиме");

        do {
            printer.print("> ");
        } while (commandHelper.executeNextCommand());
        printer.println("Программа завершена");
    }
}
