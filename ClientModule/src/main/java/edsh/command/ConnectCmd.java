package edsh.command;

import edsh.helpers.ClientCommandHelper;

public class ConnectCmd extends AbstractCommand {
    private final ClientCommandHelper commandHelper;

    public ConnectCmd(ClientCommandHelper commandHelper) {
        super(commandHelper.getHolder(), "connect",
                "[address:port] : подключается к серверу по данному адресу (если не задан - по адресу из агрументов запуска программы)");
        this.commandHelper = commandHelper;
    }

    @Override
    public String execute(String[] args) {
        if(commandHelper.getHandler().isConnected()) {
            return "!Вы уже подключены к серверу!";
        }

        boolean success;
        if(args.length > 0) {
            String[] split = args[0].split(":", 2);
            try {
                success = commandHelper.getHandler().connect(split[0], Integer.parseInt(split[1]));
            } catch (Exception e) {
                return "!Ошибка в парсинге адреса. Пожалуйста, введите его в формате 'address:port'";
            }
        } else {
            success = commandHelper.getHandler().connect();
        }
        if(!success) {
            return "!Не удалось подключиться к серверу";
        }
        commandHelper.registerAvailableCommands();
        return "Серверные команды доступны";
    }
}
