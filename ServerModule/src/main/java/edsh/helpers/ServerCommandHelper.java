package edsh.helpers;

import edsh.command.ClientAvailable;
import edsh.command.Command;
import edsh.network.AvailableCommandsPacket;
import edsh.network.Request;

public class ServerCommandHelper extends CommandHelper {

    private final Printer logger = new LoggerPrinter("Execute");

    public ServerCommandHelper(MyScanner sc, FileHelper fh) {
        super(sc, fh);
    }

    public void executeRequest(Request request, Printer printer) {
        String command = request.getCommand();
        String[] args = request.getArgs();
        if(executeCommand(command, args, printer)) {
            logger.println(command);
        }
    }

    /**
     * Проходится по всем командам и если они реализуют интерфейс {@link ClientAvailable},
     * добавляет ее в пакет
     * @return Пакет с доступными клиенту командами
     */
    public AvailableCommandsPacket getAvailableCommands() {
        AvailableCommandsPacket pkt = new AvailableCommandsPacket();
        for(Command command : getHolder().getCommands().values()) {
            if(command instanceof ClientAvailable availableCommand) {
                pkt.addCommand(availableCommand.makeAvailable());
            }
        }
        return pkt;
    }

}
