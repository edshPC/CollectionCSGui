package edsh.command;

import edsh.helpers.DBServerCommandHelper;
import edsh.helpers.ListHelper;
import edsh.mainclasses.Event;
import edsh.mainclasses.Ticket;

import java.nio.channels.SelectionKey;

public class DBRemoveAllByEventCmd extends RemoveAllByEventCmd implements AuthoritativeCommand {
    private final DBServerCommandHelper commandHelper;
    private SelectionKey executor;

    public DBRemoveAllByEventCmd(DBServerCommandHelper helper) {
        super(helper.getHolder());
        commandHelper = helper;
    }

    @Override
    public String execute(String[] args) {
        Event ev = getAttachment();
        if(ev == null) return "!Ошибка в создании события";

        int sizeBefore = list.size();
        list.removeIf(ticket -> ticket.getEvent().equals(ev) &&
                    commandHelper.checkTicketOwner(ticket.getId(), getExecutor()) &&
                    commandHelper.getDb().removeTicket(ticket.getId()));

        return "Удалено " + (sizeBefore - list.size()) + "/" + sizeBefore + " билетов";
    }

    @Override
    public void setExecutor(SelectionKey key) {
        executor = key;
    }

    @Override
    public SelectionKey getExecutor() {
        return executor;
    }

    @Override
    public boolean isVisibilityReversed() {
        return false;
    }
}
