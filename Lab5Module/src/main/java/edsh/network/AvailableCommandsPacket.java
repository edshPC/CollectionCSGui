package edsh.network;

import lombok.Getter;

import java.io.Serializable;
import java.util.LinkedList;

@Getter
public class AvailableCommandsPacket implements Serializable {
    private final LinkedList<AvailableCommand> commands = new LinkedList<>();

    public void addCommand(AvailableCommand command) {
        commands.add(command);
    }

}
