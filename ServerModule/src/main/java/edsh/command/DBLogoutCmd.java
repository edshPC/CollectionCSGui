package edsh.command;

import edsh.helpers.DBServerCommandHelper;
import edsh.network.AvailableCommand;

import java.nio.channels.SelectionKey;
import java.util.NoSuchElementException;

public class DBLogoutCmd extends AbstractCommand implements ClientAvailable, AuthoritativeCommand {
    private final DBServerCommandHelper commandHelper;
    private SelectionKey executor;

    public DBLogoutCmd(DBServerCommandHelper helper) {
        super(helper.getHolder(), "logout", ": выйти из аккаунта");
        this.commandHelper = helper;
    }

    @Override
    public String execute(String[] args) throws NoSuchElementException {
        if(!commandHelper.logoutUser(getExecutor()))
            return "!Вы не вошли в аккаунт!";
        return "Выход из аккаунта";
    }

    @Override
    public AvailableCommand makeAvailable() {
        return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE);
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
