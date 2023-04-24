package edsh.network;

import edsh.helpers.Printer;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class Response implements Serializable {

    public enum Status {
        OK,
        ERROR,
        AVAILABLE_COMMANDS
    }

    private final Status status;
    private String response;
    private AvailableCommandsPacket availableCommands;

    public Response(Status status, String response) {
        this.status = status;
        this.response = response;
    }

    public Response(AvailableCommandsPacket pkt) {
        availableCommands = pkt;
        status = Status.AVAILABLE_COMMANDS;
    }

    public void print(Printer printer) {
        switch (status) {
            case OK -> printer.print(response);
            case ERROR -> printer.errPrint(response);
        }
    }

}
