package edsh;

import edsh.helpers.LoggerPrinter;
import edsh.helpers.Printer;
import edsh.network.ServerNetworkHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunServer
{
    public static void main( String[] args ) {
        Printer printer = new LoggerPrinter("Main");
        ServerNetworkHandler handler = new ServerNetworkHandler(19825);
        if(handler.open()) printer.println("Сервер запущен");
        else return;


        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while(true) {
                handler.run();
                if(br.ready()) {
                    System.out.println(br.readLine());
                }
            }
        } catch (IOException e) {
            printer.errPrintln("Ошибка в селекторе: " + e.getMessage());
        }
    }
}
