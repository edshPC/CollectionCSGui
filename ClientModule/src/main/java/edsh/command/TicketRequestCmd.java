package edsh.command;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.MyScanner;
import edsh.mainclasses.Ticket;
import edsh.network.Request;

import java.util.NoSuchElementException;

public class TicketRequestCmd extends AbstractCommandBase implements RequestCommand {
    private final MyScanner sc;
    protected TicketRequestCmd(String name, String description, MyScanner sc) {
        super(name, description);
        this.sc = sc;
    }

    @Override
    public Request createRequest(String[] args) throws NoSuchElementException {
        try {
            Ticket ticket = Ticket.getFactory().create(sc);
            return Request.builder()
                    .command(getName())
                    .args(args)
                    .attachment(ticket).build();

        } catch (WrongFieldException e) {
            return null;
        }
    }
}
