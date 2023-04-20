package edsh.command;

import java.util.LinkedList;

import edsh.mainclasses.Ticket;

public class RemoveFirstCmd implements Command {
	private LinkedList<Ticket> list;
	
	public RemoveFirstCmd(CommandHelper ch) {
		this.list = ch.getList();
	}
	
	
	@Override
	public String execute(String[] args) {
		if(list.size() == 0)
			return "Нечего удалить";
		
		list.removeFirst();
		
		return "Билет успешно удален!";
	}

	@Override
	public String getName() {
		return "remove_first";
	}

}
