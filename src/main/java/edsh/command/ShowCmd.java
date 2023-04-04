package edsh.command;

import java.util.LinkedList;

import edsh.mainclasses.Ticket;

public class ShowCmd implements Command {
	private LinkedList<Ticket> list;
	
	public ShowCmd(CommandHelper ch) {
		this.list = ch.getList();
	}
	
	@Override
	public String execute(String[] args) {
		if(list.size() == 0)
			return "Коллекция пуста";
		String out = "";
		for(Ticket ticket : list) {
			out += ticket + "\n";
		}
		return out.substring(0, out.length()-1);
	}

	@Override
	public String getName() {
		return "show";
	}

}
