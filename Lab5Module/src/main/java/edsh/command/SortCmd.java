package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;

public class SortCmd extends AbstractCommand {
	
	public SortCmd(CommandHelper.Holder h) {
		super(h, "sort", ": отсортировать коллекцию");
	}
	
	@Override
	public String execute(String[] args) {

		ListHelper.sortList();
		
		return "Коллекция отсортирована";
	}

}
