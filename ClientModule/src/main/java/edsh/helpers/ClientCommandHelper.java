package edsh.helpers;

import edsh.command.*;
import edsh.network.AvailableCommand;
import edsh.network.NetworkHandler;
import edsh.network.Request;
import edsh.network.Response;
import lombok.Getter;

import java.util.HashMap;
import java.util.Scanner;

@Getter
public class ClientCommandHelper extends CommandHelper {

    private final HashMap<String, RequestCommand> requestCommands = new HashMap<>();
    private final NetworkHandler handler;

    public ClientCommandHelper(NetworkHandler handler) {
        super(new MyScanner(new Scanner(System.in), true), null);
        this.handler = handler;
    }

    /**
     * Регистрирует данные команд с сервера, сохраняя ее имя и объект в {@link HashMap}
     */
    public boolean registerAvailableCommands() {
        Printer printer = new ConsolePrinter();
        Response response = handler.accept();
        requestCommands.clear();
        if(response.getStatus() != Response.Status.AVAILABLE_COMMANDS) {
            printer.errPrintln("Некорректный пакет доспупных команд");
            handler.disconnect();
            return false;
        }

        for(AvailableCommand availableCommand : response.getAvailableCommands().getCommands()) {
            RequestCommand cmd = new RequestCmd(availableCommand, getHolder());
            requestCommands.put(cmd.getName(), cmd);
        }
        return true;
    }

    public void registerAllClientCommands() {
        registerCommands(new ExitCmd(getHolder()),
                new ClientHelpCmd(this),
                new ConnectCmd(this),
                new DisconnectCmd(this),
                new ExecuteScriptCmd(this));
    }

    @Override
    public boolean executeCommand(String commandStr, String[] args, Printer printer) {
        if(!handler.isConnected() || !requestCommands.containsKey(commandStr)) {
            return super.executeCommand(commandStr, args, printer);
        }
        RequestCommand command = requestCommands.get(commandStr);
        Request request = command.createRequest(args);
        if(request == null) {
            printer.errPrintln("Ошибка в генерации запроса");
            return false;
        }

        handler.send(request);
        Response response = handler.accept();
        response.print(printer);

        return true;
    }

}
