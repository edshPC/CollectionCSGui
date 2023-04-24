package edsh.command;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.MyScanner;
import edsh.mainclasses.Event;
import edsh.network.Request;

import java.util.NoSuchElementException;

public class EventRequestCmd extends AbstractCommandBase implements RequestCommand {
    private final MyScanner sc;
    protected EventRequestCmd(String name, String description, MyScanner sc) {
        super(name, description);
        this.sc = sc;
    }

    @Override
    public Request createRequest(String[] args) throws NoSuchElementException {
        try {
            Event ev = Event.getFactory().create(sc);
            return Request.builder()
                    .command(getName())
                    .args(args)
                    .attachment(ev).build();

        } catch (WrongFieldException e) {
            return null;
        }
    }
}
