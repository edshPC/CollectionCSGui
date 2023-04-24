package edsh.command;

import edsh.network.Request;

public interface RequestCommand {
    Request createRequest(String[] args);
    String getName();
}
