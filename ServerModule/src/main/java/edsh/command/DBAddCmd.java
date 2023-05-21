package edsh.command;

import edsh.helpers.DBServerCommandHelper;
import edsh.mainclasses.Ticket;

import java.nio.channels.SelectionKey;

public class DBAddCmd extends AddCmd implements AuthoritativeCommand {
    private final DBServerCommandHelper commandHelper;
    private SelectionKey executor;

    public DBAddCmd(DBServerCommandHelper helper) {
        super(helper.getHolder());
        commandHelper = helper;
    }

    @Override
    public String execute(String[] args) {
        Ticket t = getAttachment();
        if(t == null) return "!Ошибка в создании билета";

        if(!commandHelper.getDb().insertTicket(t, commandHelper.getLogin(getExecutor())))
            return "!Ошибка записи билета в базу данных";
        list.add(t);
        return "Билет успешно добавлен!";
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
