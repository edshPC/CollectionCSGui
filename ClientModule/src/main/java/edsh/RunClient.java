package edsh;

import edsh.network.NetworkHandler;
import edsh.network.Request;

public class RunClient
{
    public static void main( String[] args ) {
        NetworkHandler handler;
        try {
            String[] split = args[0].split(":");
            int port = Integer.parseInt(split[1]);
            handler = new NetworkHandler(split[0], port);
        } catch (Exception e) {
            System.err.println("Неверно введён адрес/порт. Пожалуйста, введите его в аргументы формате ардес:порт");
            return;
        }

        handler.connect();
        handler.send(new Request());
    }
}
