package edsh.command;

import edsh.helpers.DBServerCommandHelper;
import edsh.helpers.ListHelper;
import edsh.mainclasses.Ticket;

import java.nio.channels.SelectionKey;

public class DBRemoveGreaterCmd extends RemoveGreaterCmd implements AuthoritativeCommand {
    private final DBServerCommandHelper commandHelper;
    private SelectionKey executor;

    public DBRemoveGreaterCmd(DBServerCommandHelper helper) {
        super(helper.getHolder());
        commandHelper = helper;
    }

    @Override
    public String execute(String[] args) {
        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (Exception e) {
            return "!Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'remove_lower 5'";
        }

        Ticket check = ListHelper.getById(id);
        if(check == null) return "!Не найден билет с данным id";

        int sizeBefore = list.size();
        list.removeIf(ticket -> ticket.compareTo(check) > 0 &&
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
