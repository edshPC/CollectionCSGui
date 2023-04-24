package edsh;

import edsh.helpers.*;
import edsh.network.ServerNetworkHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class RunServer {
    public static void main(String[] args) {
        Printer printer = new LoggerPrinter("Main");
        HashMap<Character, String> arguments = new ArgsHelper(args).parseKeyArguments();
        int port = 19825; String filename = "source.json";
        if(arguments.containsKey('p')) {
            try {
                port = Integer.parseInt(arguments.get('p'));
            } catch (NumberFormatException e) {
                printer.errPrintln("Ошибка в записи порта");
            }
        }
        if(arguments.containsKey('f')) filename = arguments.get('f');

        ServerNetworkHandler handler = new ServerNetworkHandler(port);
        FileHelper fh = new FileHelper(filename, new LoggerPrinter("FileHelper"));

        int loaded = ListHelper.load(fh);
        if(loaded >= 0) {
            printer.println("Загружено " + loaded + " элементов в коллекцию");
        }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             MyScanner sc = new MyScanner(new Scanner(br), true)) {

            ServerCommandHelper commandHelper = new ServerCommandHelper(sc, fh);
            commandHelper.registerAllCommands();
            if (!handler.open(commandHelper)) return;
            printer.println("Сервер запущен на порте " + port);


            while (!br.ready() || commandHelper.executeNextCommand()) {
                handler.run();
            }

        } catch (IOException e) {
            printer.errPrintln("Ошибка в селекторе: " + e.getMessage());
        }
        handler.close();
        printer.println("Сервер остановлен");
    }
}
