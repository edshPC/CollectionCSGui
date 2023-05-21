package edsh.network;

import edsh.helpers.ServerCommandHelper;
import edsh.helpers.ResponsePrinter;

import java.nio.channels.SelectionKey;

public class RequestHandler {
    private final ServerCommandHelper commandHelper;

    public RequestHandler(ServerCommandHelper commandHelper) {
        this.commandHelper = commandHelper;
    }

    public void handleRequestFrom(SelectionKey key) {
        ResponsePrinter responsePrinter = new ResponsePrinter();
        responsePrinter.setClient(key);
        Request request = (Request) key.attachment();
        request.setExecutor(key);
        commandHelper.executeRequest(request, responsePrinter);
    }

    public void handleDisconnect(SelectionKey key) {
        commandHelper.onDisconnect(key);
    }

    public void sendAvailableCommandsTo(SelectionKey key) {
        commandHelper.sendAvailableCommandsTo(key);
    }

}
