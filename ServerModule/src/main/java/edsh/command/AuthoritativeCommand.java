package edsh.command;

import java.nio.channels.SelectionKey;

public interface AuthoritativeCommand {
    void setExecutor(SelectionKey key);
    SelectionKey getExecutor();
    boolean isVisibilityReversed();
}
