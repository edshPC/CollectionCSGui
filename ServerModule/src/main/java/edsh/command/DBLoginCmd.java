package edsh.command;

import edsh.helpers.DBServerCommandHelper;
import edsh.network.AvailableCommand;

import java.nio.channels.SelectionKey;
import java.util.NoSuchElementException;

public class DBLoginCmd extends AbstractCommand implements ClientAvailable, AuthoritativeCommand {
    private final DBServerCommandHelper commandHelper;
    private SelectionKey executor;

    public DBLoginCmd(DBServerCommandHelper helper) {
        super(helper.getHolder(), "login", "{login} {password} : войти в аккаунт");
        this.commandHelper = helper;
    }

    @Override
    public String execute(String[] args) throws NoSuchElementException {
        if(args.length < 2) return "!Неверные агрументы";
        if(!commandHelper.loginUser(getExecutor(), args[0], args[1]))
            return "!Неверный логин или пароль!";
        return "Добро пожаловать, " + args[0] + "!";
    }

    @Override
    public AvailableCommand makeAvailable() {
        return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE,
                AvailableCommand.ArgType.STRING, AvailableCommand.ArgType.STRING);
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
        return true;
    }
}
