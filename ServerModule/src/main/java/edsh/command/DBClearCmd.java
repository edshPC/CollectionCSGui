package edsh.command;

import edsh.helpers.DBServerCommandHelper;

import java.nio.channels.SelectionKey;

public class DBClearCmd extends ClearCmd implements AuthoritativeCommand {
    private final DBServerCommandHelper commandHelper;
    private SelectionKey executor;

    public DBClearCmd(DBServerCommandHelper helper) {
        super(helper.getHolder());
        commandHelper = helper;
    }

    @Override
    public String execute(String[] args) {
        int sizeBefore = list.size();
        list.removeIf(ticket -> commandHelper.checkTicketOwner(ticket.getId(), getExecutor()) &&
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
