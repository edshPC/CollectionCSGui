package edsh.helpers;

import edsh.command.*;
import edsh.network.AvailableCommandsPacket;
import edsh.network.Request;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.channels.SelectionKey;
import java.util.HashMap;

public class DBServerCommandHelper extends ServerCommandHelper {
    private final HashMap<SelectionKey, String> users = new HashMap<>();
    @Getter
    private final DatabaseHelper db;

    public DBServerCommandHelper(MyScanner sc, DatabaseHelper db) {
        super(sc, new DatabaseStorage(db));
        this.db = db;
    }

    public boolean registerUser(SelectionKey key, String login, String pass) {
        if(users.containsKey(key) || db.containsUser(login)) return false;
        String passHash = DigestUtils.md5Hex(pass);
        if(!db.addUser(login, passHash)) return false;
        users.put(key, login);
        sendAvailableCommandsTo(key);
        return true;
    }

    public boolean loginUser(SelectionKey key, String login, String pass) {
        if(users.containsKey(key) || !db.containsUser(login)) return false;
        String passHash = DigestUtils.md5Hex(pass);
        if(!db.checkPassHash(login, passHash)) return false;
        users.put(key, login);
        sendAvailableCommandsTo(key);
        return true;
    }

    public boolean logoutUser(SelectionKey key) {
        if(!users.containsKey(key)) return false;
        users.remove(key);
        sendAvailableCommandsTo(key);
        return true;
    }

    public String getLogin(SelectionKey key) {
        return users.get(key);
    }

    @Override
    public void executeRequest(Request request, Printer printer) {
        if(getHolder().getCommands().get(request.getCommand())
                instanceof AuthoritativeCommand command) {
            command.setExecutor(request.getExecutor());
        }
        super.executeRequest(request, printer);
    }

    @Override
    public void registerAllCommands() {
        registerCommands(new HelpCmd(getHolder()), new InfoCmd(getHolder()), new ShowCmd(getHolder()), new DBAddCmd(this),
                new UpdateCmd(getHolder()), new RemoveByIdCmd(getHolder()), new ClearCmd(getHolder()), new SaveCmd(getHolder()),
                new ExecuteScriptCmd(this), new ExitCmd(getHolder()), new RemoveFirstCmd(getHolder()),
                new RemoveGreaterCmd(getHolder()), new RemoveLowerCmd(getHolder()), new RemoveAllByEventCmd(getHolder()),
                new FilterContainsCommentCmd(getHolder()), new PrintUniquePriceCmd(getHolder()), new SortCmd(getHolder()),
                new DBLoginCmd(this), new DBLogoutCmd(this), new DBRegisterCmd(this));
    }

    @Override
    public AvailableCommandsPacket getAvailableCommandsFor(SelectionKey key) {
        AvailableCommandsPacket pkt = new AvailableCommandsPacket();
        boolean isClientLogin = users.containsKey(key);
        for(Command command : getHolder().getCommands().values()) {
            boolean needAdd = isClientLogin;
            if(command instanceof AuthoritativeCommand authoritativeCommand
                && authoritativeCommand.isVisibilityReversed()) needAdd = !needAdd;
            if(command instanceof ClientAvailable availableCommand && needAdd) {
                pkt.addCommand(availableCommand.makeAvailable());
            }
        }
        return pkt;
    }

    @Override
    public void onDisconnect(SelectionKey key) {
        logoutUser(key);
    }

}
