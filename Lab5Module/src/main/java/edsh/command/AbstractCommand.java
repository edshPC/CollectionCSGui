package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.FileHelper;
import edsh.helpers.ListHelper;
import edsh.helpers.MyScanner;
import edsh.mainclasses.Ticket;

import java.util.LinkedList;

public abstract class AbstractCommand extends AbstractCommandBase implements Command {

    protected final LinkedList<Ticket> list;
    protected final MyScanner sc;
    protected final FileHelper fh;

    protected AbstractCommand(CommandHelper.Holder holder, String name, String description) {
        super(name, description);
        if(holder == null) {
            this.list = null;
            this.sc = null;
            this.fh = null;
            return;
        }
        this.list = ListHelper.getList();
        this.sc = holder.getScanner();
        this.fh = holder.getFileHelper();
    }

}
