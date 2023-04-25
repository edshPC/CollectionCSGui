package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;
import edsh.network.AvailableCommand;

public class SortCmd extends AbstractCommand implements ClientAvailable {
	
	public SortCmd(CommandHelper.Holder h) {
		super(h, "sort", ": отсортировать коллекцию");
	}
	
	@Override
	public String execute(String[] args) {

		ListHelper.sortList();
		
		return "Коллекция отсортирована";
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE);
	}

}
