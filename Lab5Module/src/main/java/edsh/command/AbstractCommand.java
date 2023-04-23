package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.FileHelper;
import edsh.helpers.ListHelper;
import edsh.helpers.MyScanner;
import edsh.mainclasses.Ticket;
import lombok.Getter;

import java.util.LinkedList;

public abstract class AbstractCommand implements Command {
    @Getter
    private final String name;
    private final String description;

    protected final LinkedList<Ticket> list;
    protected final MyScanner sc;
    protected final FileHelper fh;

    protected AbstractCommand(CommandHelper.Holder holder, String name, String description) {
        this.name = name;
        this.description = description;
        this.list = ListHelper.getList();
        this.sc = holder.getScanner();
        this.fh = holder.getFileHelper();
    }

    @Override
    public String toString() {
        return " - " + name + " " + description;
    }
}
