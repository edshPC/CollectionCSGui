package edsh.command;

import edsh.helpers.CommandHelper.Holder;

public class ClearCmd extends AbstractCommand {
	
	public ClearCmd(Holder h) {
		super(h, "clear", ": очистить коллекцию");
	}
	
	@Override
	public String execute(String[] args) {
		list.clear();
		return "Коллекция очищена";
	}
}
