package edsh.command;

import edsh.helpers.DBServerCommandHelper;
import edsh.network.AvailableCommand;

import java.nio.channels.SelectionKey;
import java.util.NoSuchElementException;

public class DBRegisterCmd extends AbstractCommand implements ClientAvailable, AuthoritativeCommand {
    private final DBServerCommandHelper commandHelper;
    private SelectionKey executor;

    public DBRegisterCmd(DBServerCommandHelper helper) {
        super(helper.getHolder(), "register", "{login} {password} : создать и войти в аккаунт");
        this.commandHelper = helper;
    }

    @Override
    public String execute(String[] args) throws NoSuchElementException {
        if(args.length < 2) return "!Неверные агрументы";
        if(args[0].length() > 16) return "!Логин должен быть не более 16 символов!";
        if(!commandHelper.registerUser(getExecutor(), args[0], args[1]))
            return "!Пользователь с данным логином уже зарегестрирован!";
        return "Регестрация успешна. Добро пожаловать, " + args[0] + "!";
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
