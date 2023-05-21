package edsh.command;

import edsh.helpers.DBServerCommandHelper;

import java.nio.channels.SelectionKey;

public class DBRemoveFirstCmd extends RemoveFirstCmd implements AuthoritativeCommand {
    private final DBServerCommandHelper commandHelper;
    private SelectionKey executor;

    public DBRemoveFirstCmd(DBServerCommandHelper helper) {
        super(helper.getHolder());
        commandHelper = helper;
    }

    @Override
    public String execute(String[] args) {
        if(list.size() == 0)
            return "!Нечего удалить";
        long firstId = list.getFirst().getId();
        if(commandHelper.checkTicketOwner(firstId, getExecutor()) &&
           commandHelper.getDb().removeTicket(firstId)) {
            list.removeFirst();
            return "Первый билет удален";
        }

        return "!Первый билет не является вашим";
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
