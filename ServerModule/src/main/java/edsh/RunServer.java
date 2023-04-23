package edsh;

import edsh.command.ExitCmd;
import edsh.command.HelpCmd;
import edsh.helpers.*;
import edsh.mainclasses.Ticket;
import edsh.network.ServerNetworkHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class RunServer {
    public static void main(String[] args) {
        Printer printer = new LoggerPrinter("Main");
        ServerNetworkHandler handler = new ServerNetworkHandler(19825);
        FileHelper fh = new FileHelper("sources.json", printer);

        int loaded = ListHelper.load(fh);
        if(loaded >= 0) {
            printer.println("Загружено " + loaded + " элементов в коллекцию");
        }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             MyScanner sc = new MyScanner(new Scanner(br), true)) {

            RequestCommandHelper commandHelper = new RequestCommandHelper(sc, fh);
            commandHelper.registerAllCommands();
            if (!handler.open(commandHelper)) return;
            printer.println("Сервер запущен");


            while (!br.ready() || commandHelper.executeNextCommand()) {
                handler.run();
            }

        } catch (IOException e) {
            printer.errPrintln("Ошибка в селекторе: " + e.getMessage());
        }
        handler.close();
    }
}
