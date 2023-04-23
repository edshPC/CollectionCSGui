package edsh.network;

import edsh.helpers.LoggerPrinter;
import edsh.helpers.Printer;
import edsh.helpers.RequestCommandHelper;
import lombok.SneakyThrows;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class ServerNetworkHandler {
    private final Printer printer = new LoggerPrinter(getClass().getSimpleName());
    private final int port;
    private RequestHandler requestHandler;
    private ServerSocketChannel servSocket;
    private Selector selector;

    public ServerNetworkHandler(int port) {
        this.port = port;
    }

    public boolean open(RequestCommandHelper commandHelper) {
        try {
            selector = Selector.open();
            requestHandler = new RequestHandler(commandHelper);

            servSocket = ServerSocketChannel.open();
            servSocket.bind(new InetSocketAddress(port));
            servSocket.configureBlocking(false);
            servSocket.register(selector, SelectionKey.OP_ACCEPT);
            return true;
        } catch (IOException e) {
            printer.errPrintln("Ошибка в открытии порта: " + e.getMessage());
            return false;
        }
    }

    public void run() throws IOException {
        if(selector.select(200) == 0) return;

        for(Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext();) {
            SelectionKey key = it.next();
            it.remove();

            if(key.isAcceptable())
                handleConnection();
            else if(key.isReadable()) {
                if(attachRequest(key)) requestHandler.handleRequestFrom(key);
                else disconnectClient(key);
            }
            else if (key.isWritable()) {
                if(!handleResponse(key)) disconnectClient(key);
            }
        }
    }

    @SneakyThrows
    public void close() {
        selector.close();
        servSocket.close();
    }

    private void handleConnection() {
        try {
            SocketChannel client = servSocket.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            printer.println("Подключен клиент " + client.getRemoteAddress().toString());
        } catch (Exception e) {
            printer.errPrintln("Ошибка в создании подключения: " + e.getMessage());
        }
    }

    private boolean attachRequest(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        try {
            ByteBuffer bb = ByteBuffer.allocate(1 << 20); //Mb
            if(client.read(bb) <= 0) return false;
            ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(bb.array()));
            Object request = ois.readObject();
            ois.close();

            if(!(request instanceof Request)) {
                printer.errPrintln("Получен некорректный запрос");
                return false;
            }
            key.attach(request);
            printer.println("Получен запрос от клиента " + client.getRemoteAddress().toString());
            return true;
        } catch (SocketException ignored) {}
        catch (IOException | ClassNotFoundException e) {
            printer.errPrintln("Ошибка в получении запроса: " + e.getMessage());
        }
        return false;
    }

    private boolean handleResponse(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        try {
            Object response = key.attachment();
            if(!(response instanceof Response)) {
                printer.errPrintln("Некорректный ответ");
                return false;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream(1 << 20); //Mb
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            oos.close();

            ByteBuffer bb = ByteBuffer.wrap(baos.toByteArray());
            if(client.write(bb) <= 0) return false;
            key.interestOps(SelectionKey.OP_READ); //После записи разрешаем только читать

            printer.println("Ответ отправлен клиенту " + client.getRemoteAddress().toString());
            return true;
        } catch (Exception e) {
            printer.errPrintln("Ошибка в отправке ответа: " + e.getMessage());
        }
        return false;
    }

    private void disconnectClient(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        key.cancel();
        try {
            printer.println("Отключен клиент " + client.getRemoteAddress().toString());
            if(client.isOpen()) client.close();
        } catch (IOException e) {
            printer.errPrintln("Клиент отключен");
        }
    }

}
