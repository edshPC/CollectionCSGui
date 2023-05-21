package edsh.command;

import edsh.helpers.DBServerCommandHelper;
import edsh.helpers.ListHelper;
import edsh.mainclasses.Ticket;

import java.nio.channels.SelectionKey;

public class DBUpdateCmd extends UpdateCmd implements AuthoritativeCommand {
    private final DBServerCommandHelper commandHelper;
    private SelectionKey executor;

    public DBUpdateCmd(DBServerCommandHelper helper) {
        super(helper.getHolder());
        commandHelper = helper;
    }

    @Override
    public String execute(String[] args) {
        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (Exception e) {
            return "!Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'update 5'";
        }
        int index = ListHelper.getIndexById(id);
        if(index < 0)
            return "!Не найден билет с данным id. Используйте add чтобы добавить новый";

        if(!commandHelper.checkTicketOwner(id, getExecutor()))
            return "!У вас нет доступа к этому билету";

        Ticket t = getAttachment();
        if(t == null) return "!Ошибка при создании билета";

        t.setId(id);
        if(!commandHelper.getDb().updateTicket(t))
            return "!Ошибка обновления билета в базе данных";
        Ticket.putWithId(index, id, t);

        return "Билет успешно обновлен!";
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
