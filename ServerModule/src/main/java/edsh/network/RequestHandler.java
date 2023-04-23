package edsh.network;

import edsh.helpers.RequestCommandHelper;
import edsh.helpers.ResponsePrinter;

import java.nio.channels.SelectionKey;

public class RequestHandler {
    private final RequestCommandHelper commandHelper;

    public RequestHandler(RequestCommandHelper commandHelper) {
        this.commandHelper = commandHelper;
    }

    public void handleRequestFrom(SelectionKey key) {
        ResponsePrinter responsePrinter = new ResponsePrinter();
        responsePrinter.setClient(key);

        Request request = (Request) key.attachment();
        commandHelper.executeRequest(request, responsePrinter);

    }

}
