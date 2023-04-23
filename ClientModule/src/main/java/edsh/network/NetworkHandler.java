package edsh.network;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class NetworkHandler {
    private final String address;
    private final int port;
    private Socket socket;

    public NetworkHandler(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public boolean connect() {
        try {
            socket = new Socket(address, port);
            System.out.println("Сокет создан");
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        }
        return false;
    }

    public void send(Request o) {
        try {
            BufferedOutputStream buf = new BufferedOutputStream(socket.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(buf);
            oos.writeObject(o);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response accept() {
        try {
            BufferedInputStream buf = new BufferedInputStream(socket.getInputStream());
            ObjectInputStream ois = new ObjectInputStream(buf);
            return (Response) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
