package edsh.command;

import edsh.mainclasses.Ticket;

public class SortCmd implements Command {
	
	public SortCmd(CommandHelper ch) {}
	
	@Override
	public String execute(String[] args) {
		
		Ticket.sortList();
		
		return "Коллекция отсортирована";
	}

	@Override
	public String getName() {
		return "sort";
	}

}
