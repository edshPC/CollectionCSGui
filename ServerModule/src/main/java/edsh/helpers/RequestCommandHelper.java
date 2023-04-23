package edsh.helpers;

import edsh.mainclasses.Ticket;
import edsh.network.Request;

import java.util.LinkedList;

public class RequestCommandHelper extends CommandHelper {

    private final Printer logger = new LoggerPrinter("Execute");

    public RequestCommandHelper(MyScanner sc, FileHelper fh) {
        super(sc, fh);
    }

    public void executeRequest(Request request, Printer printer) {
        String command = request.getCommand();
        String[] args = request.getArgs();
        if(executeCommand(command, args, printer)) {
            logger.println(command);
        }
    }
}
