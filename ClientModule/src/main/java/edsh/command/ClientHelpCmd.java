package edsh.command;

import edsh.helpers.ClientCommandHelper;

public class ClientHelpCmd extends HelpCmd {

    private final ClientCommandHelper commandHelper;

    public ClientHelpCmd(ClientCommandHelper commandHelper) {
        super(commandHelper.getHolder());
        this.commandHelper = commandHelper;
    }

    @Override
    public String execute(String[] args) {
        StringBuilder out = new StringBuilder("Команды сервера:\n");

        if(commandHelper.getHandler().isConnected()) {
            for(RequestCommand command : commandHelper.getRequestCommands().values()) {
                out.append(command.toString()).append('\n');
            }
        } else {
            out.append(" - подключитесь к серверу чтобы получить доступные команды!\n");
        }

        out.append("Команды клиента:\n").append(super.execute(args));
        return out.toString();
    }
}
