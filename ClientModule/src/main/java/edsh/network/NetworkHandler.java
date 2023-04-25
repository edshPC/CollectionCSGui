package edsh.network;

import edsh.helpers.ConsolePrinter;
import edsh.helpers.Printer;
import lombok.Getter;

import java.io.*;
import java.net.Socket;

public class NetworkHandler {
    private final Printer printer = new ConsolePrinter();
    private final String address;
    private final int port;
    @Getter
    private boolean isConnected = false;
    private Socket socket;

    public NetworkHandler(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public boolean connect() {
        if(address == null || port < 0) {
            printer.errPrintln("Некорректно задан адрес/порт в аргументах");
            return false;
        }
        return connect(address, port);
    }

    public boolean connect(String address, int port) {
        try {
            socket = new Socket(address, port);
            printer.println("Подключение к серверу успешно");

            isConnected = socket.isConnected();
            return true;
        } catch (IOException e) {
            printer.errPrintln("Ошибка подключения: " + e.getMessage());
        }
        return false;
    }

    public void send(Request o) {
        if(isConnected)
            try {
                ObjectOutputStream output =
                        new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                output.writeObject(o);
                output.flush();
            } catch (IOException e) {
                printer.errPrintln("Ошибка отправки запроса: " + e.getMessage());
                disconnect();
            }
        else printer.errPrintln("Вы не подключены к серверу");
    }

    public Response accept() {
        if(isConnected)
            try {
                ObjectInputStream input =
                        new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                return (Response) input.readObject();
            } catch (Exception e) {
                printer.errPrintln("Ошибка получения ответа: " + e.getMessage());
                disconnect();
            }
        return new Response(Response.Status.ERROR, "");
    }

    public void disconnect() {
        try {
            if(!socket.isClosed()) socket.close();
        } catch (IOException ignored) {}
        isConnected = false;
        printer.println("Отключено от сервера");
    }

}
