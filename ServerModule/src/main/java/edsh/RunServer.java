package edsh;

import edsh.helpers.*;
import edsh.network.RequestHandler;
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
        int port = 19825; String dbProps = "db.properties";
        if(arguments.containsKey('p')) {
            try {
                port = Integer.parseInt(arguments.get('p'));
            } catch (NumberFormatException e) {
                printer.errPrintln("Ошибка в записи порта");
            }
        } // -port
        if(arguments.containsKey('d')) dbProps = arguments.get('d'); // -db
        boolean isFileMode = arguments.containsKey('f');


        ServerNetworkHandler handler = new ServerNetworkHandler(port);

        DataStorage ds;
        DatabaseHelper db = null;
        if(isFileMode) {
            String filename = arguments.get('f'); // -file
            FileHelper fh = new FileHelper(filename, new LoggerPrinter("FileHelper"));
            ds = new FileStorage(fh);
        } else {
            db = new DatabaseHelper();
            if(!db.connect(dbProps) || !db.createTables()) {
                printer.errPrintln("Некуда сохранять коллекцию! Проверьте соединение с БД или используйте -f {filename} в аргументах для работы с файлом");
                return;
            }
            ds = new DatabaseStorage(db);
        }
        int loaded = ListHelper.load(ds);
        if(loaded >= 0) {
            printer.println("Загружено " + loaded + " элементов в коллекцию");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             MyScanner sc = new MyScanner(new Scanner(br), true)) {

            ServerCommandHelper commandHelper = new ServerCommandHelper(sc, ds);
            commandHelper.registerAllCommands();
            if(isFileMode) {
                if (!handler.open(commandHelper)) return;
            } else {
                ServerCommandHelper openWith = new DBServerCommandHelper(sc, db);
                openWith.registerAllCommands();
                if (!handler.open(openWith)) return;
            }

            printer.println("Сервер запущен на порте " + port);


            while (!br.ready() || commandHelper.executeNextCommand()) {
                handler.run();
            }

        } catch (IOException e) {
            printer.errPrintln("Ошибка в селекторе: " + e.getMessage());
        }
        handler.close();
        if(ListHelper.getList().size() > 0 && ListHelper.save(ds))
            printer.println("Коллекция сохранена");

        printer.println("Сервер остановлен");
    }
}
