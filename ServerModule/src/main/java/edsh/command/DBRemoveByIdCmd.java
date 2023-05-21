package edsh.command;

import edsh.helpers.DBServerCommandHelper;
import edsh.helpers.ListHelper;
import edsh.mainclasses.Ticket;

import java.nio.channels.SelectionKey;

public class DBRemoveByIdCmd extends RemoveByIdCmd implements AuthoritativeCommand {
    private final DBServerCommandHelper commandHelper;
    private SelectionKey executor;

    public DBRemoveByIdCmd(DBServerCommandHelper helper) {
        super(helper.getHolder());
        commandHelper = helper;
    }

    @Override
    public String execute(String[] args) {
        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (Exception e) {
            return "!Не указан или указан неверно id элемента. Пожалуйста укажите id как число";
        }

        Ticket ticket = ListHelper.getById(id);
        if(ticket == null) return "!Не найден билет с данным id";
        if(commandHelper.checkTicketOwner(ticket.getId(), getExecutor()) &&
           commandHelper.getDb().removeTicket(ticket.getId())) {
            list.remove(ticket);
            return "Билет удален";
        }

        return "!Этот билет не является вашим";
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
