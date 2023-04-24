package edsh;

import edsh.command.ExitCmd;
import edsh.command.HelpCmd;
import edsh.command.ShowCmd;
import edsh.command.SimpleRequestCmd;
import edsh.helpers.ArgsHelper;
import edsh.helpers.ClientCommandHelper;
import edsh.helpers.ConsolePrinter;
import edsh.helpers.Printer;
import edsh.network.NetworkHandler;
import edsh.network.Request;
import edsh.network.Response;

import java.util.HashMap;
import java.util.Scanner;

public class RunClient
{
    public static void main( String[] args ) {

        Printer printer = new ConsolePrinter();
        String address = "localhost"; int port = 19825;
        HashMap<Character, String> arguments = new ArgsHelper(args).parseKeyArguments();
        if(arguments.containsKey('a'))
            address = arguments.get('a');
        if(arguments.containsKey('p')) {
            try {
                port = Integer.parseInt(arguments.get('p'));
            } catch (NumberFormatException e) {
                printer.errPrintln("Ошибка в записи порта");
            }
        }
        NetworkHandler handler = new NetworkHandler(address, port);
        if(!handler.connect()) printer.println("Программа работает в оффлайн-режиме");

        ClientCommandHelper commandHelper = new ClientCommandHelper(handler);
        commandHelper.registerCommands(new ExitCmd(commandHelper.getHolder()),
                new HelpCmd(commandHelper.getHolder()));
        commandHelper.registerAvailableCommands();

        while (commandHelper.executeNextCommand());
        printer.println("Программа завершена");
    }
}
