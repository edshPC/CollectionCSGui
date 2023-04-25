package edsh.command;

import edsh.helpers.ClientCommandHelper;

public class DisconnectCmd extends AbstractCommand {
    private final ClientCommandHelper commandHelper;

    public DisconnectCmd(ClientCommandHelper commandHelper) {
        super(commandHelper.getHolder(), "disconnect", ": отключиться от сервера");
        this.commandHelper = commandHelper;
    }

    @Override
    public String execute(String[] args) {
        if(!commandHelper.getHandler().isConnected()) {
            return "!Вы не подключены к серверу!";
        }

        commandHelper.getHandler().disconnect();
        return "Программа работает в оффлайн-режиме";
    }
}
