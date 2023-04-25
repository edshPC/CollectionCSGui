package edsh.command;

import edsh.helpers.CommandHelper.Holder;
import edsh.network.AvailableCommand;

public class ClearCmd extends AbstractCommand implements ClientAvailable {
	
	public ClearCmd(Holder h) {
		super(h, "clear", ": очистить коллекцию");
	}
	
	@Override
	public String execute(String[] args) {
		list.clear();
		return "Коллекция очищена";
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE);
	}
}
