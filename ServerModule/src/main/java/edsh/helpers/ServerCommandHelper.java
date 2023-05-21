package edsh.helpers;

import edsh.command.*;
import edsh.network.AvailableCommandsPacket;
import edsh.network.Request;

import java.nio.channels.SelectionKey;

public class ServerCommandHelper extends CommandHelper {

    private final Printer logger = new LoggerPrinter("Execute");

    public ServerCommandHelper(MyScanner sc, DataStorage ds) {
        super(sc, ds);
    }

    public void executeRequest(Request request, Printer printer) {
        String cmd = request.getCommand();
        String[] args = request.getArgs();
        if(getHolder().getCommands().get(cmd) instanceof RequireAttachment<?> command) {
            command.setAttachment(request.getAttachment());
        }
        if(executeCommand(cmd, args, printer)) {
            logger.println(cmd);
        }
    }

    /**
     * Проходится по всем командам и если они реализуют интерфейс {@link ClientAvailable},
     * добавляет ее в пакет
     * @return Пакет с доступными клиенту командами
     */
    public AvailableCommandsPacket getAvailableCommandsFor(SelectionKey key) {
        AvailableCommandsPacket pkt = new AvailableCommandsPacket();
        for(Command command : getHolder().getCommands().values()) {
            if(command instanceof ClientAvailable availableCommand) {
                pkt.addCommand(availableCommand.makeAvailable());
            }
        }
        return pkt;
    }

    public void sendAvailableCommandsTo(SelectionKey key) {
        ResponsePrinter responsePrinter = new ResponsePrinter();
        responsePrinter.setClient(key);

        AvailableCommandsPacket availableCommands = getAvailableCommandsFor(key);
        responsePrinter.sendAvailableCommands(availableCommands);
    }
    public void onDisconnect(SelectionKey key) {}

}
