package edsh.helpers;

import edsh.network.AvailableCommandsPacket;
import edsh.network.Response;
import lombok.Setter;

import java.nio.channels.SelectionKey;

public class ResponsePrinter implements Printer {
    private Response response;
    @Setter
    private SelectionKey client;

    @Override
    public void print(String str) {
        response = new Response(Response.Status.OK, str);
        sendTo(client);
    }

    @Override
    public void println(String str) {
        response = new Response(Response.Status.OK, str + '\n');
        sendTo(client);
    }

    @Override
    public void errPrint(String str) {
        response = new Response(Response.Status.ERROR, str);
        sendTo(client);
    }

    @Override
    public void errPrintln(String str) {
        response = new Response(Response.Status.ERROR, str + '\n');
        sendTo(client);
    }

    public void sendAvailableCommands(AvailableCommandsPacket pkt) {
        response = new Response(pkt);
        sendTo(client);
    }

    public void sendTo(SelectionKey client) {
        if(client == null || response == null) return;
        if(client.interestOps() == SelectionKey.OP_WRITE &&
            client.attachment() instanceof Response resp) {
            resp.setResponse(response.getResponse());
            return;
        }
        client.attach(response);
        client.interestOps(SelectionKey.OP_WRITE); //нужно отправить ответ
    }
}
