package edsh.command;

import java.util.LinkedList;

import edsh.mainclasses.Ticket;

public class ClearCmd implements Command {
	private LinkedList<Ticket> list;
	
	public ClearCmd(CommandHelper ch) {
		this.list = ch.getList();
	}
	
	@Override
	public String execute(String[] args) {
		list.clear();
		return "Коллекция очищена";
	}

	@Override
	public String getName() {
		return "clear";
	}

}
