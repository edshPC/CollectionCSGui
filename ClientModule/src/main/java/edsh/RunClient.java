package edsh;

import edsh.helpers.ConsolePrinter;
import edsh.helpers.Printer;
import edsh.network.NetworkHandler;
import edsh.network.Request;
import edsh.network.Response;

import java.util.Scanner;

public class RunClient
{
    public static void main( String[] args ) {
        NetworkHandler handler;
        Printer console = new ConsolePrinter();
        try {
            String[] split = args[0].split(":");
            int port = Integer.parseInt(split[1]);
            handler = new NetworkHandler(split[0], port);
        } catch (Exception e) {
            console.errPrintln("Неверно введён адрес/порт. Пожалуйста, введите его в аргументы формате ардес:порт");
            return;
        }

        handler.connect();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            Request r = Request.builder().command(sc.nextLine()).build();
            handler.send(r);
            Response response = handler.accept();
            response.print(console);

        }

    }
}
