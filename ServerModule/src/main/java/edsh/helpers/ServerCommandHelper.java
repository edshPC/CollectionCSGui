package edsh.helpers;

import edsh.command.ClientAvailable;
import edsh.command.Command;
import edsh.command.RequireAttachment;
import edsh.network.AvailableCommandsPacket;
import edsh.network.Request;

public class ServerCommandHelper extends CommandHelper {

    private final Printer logger = new LoggerPrinter("Execute");

    public ServerCommandHelper(MyScanner sc, FileHelper fh) {
        super(sc, fh);
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
