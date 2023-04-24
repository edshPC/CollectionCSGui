package edsh.command;

import edsh.network.AvailableCommand;
import edsh.network.Request;

public class SimpleRequestCmd extends AbstractCommandBase implements RequestCommand {
    private final AvailableCommand.AttachedObject attachment;
    private final AvailableCommand.ArgType[] argChecks;

    public SimpleRequestCmd(AvailableCommand availableCommand) {
        super(availableCommand.getName(), availableCommand.getDescription());
        this.attachment = availableCommand.getAttachment();
        this.argChecks = availableCommand.getArgs();
    }

    @Override
    public Request createRequest(String[] args) {
        Request.RequestBuilder requestBuilder = Request.builder()
                .command(getName());

        return requestBuilder.build();
    }
}
