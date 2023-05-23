package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;
import edsh.mainclasses.Ticket;

import java.util.Deque;

public abstract class AbstractCommand extends AbstractCommandBase implements Command {

    protected final Deque<Ticket> list;
    protected final CommandHelper.Holder holder;

    protected AbstractCommand(CommandHelper.Holder holder, String name, String description) {
        super(name, description);
        this.holder = holder;
        this.list = ListHelper.getList();
    }

}
