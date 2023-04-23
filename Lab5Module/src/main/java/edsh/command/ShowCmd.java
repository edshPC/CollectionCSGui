package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.mainclasses.Ticket;

public class ShowCmd extends AbstractCommand {
	
	public ShowCmd(CommandHelper.Holder h) {
		super(h, "show", ": вывести все элементы коллекции в строковом представлении");
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

}
