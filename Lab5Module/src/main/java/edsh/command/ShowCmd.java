package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.mainclasses.Ticket;
import edsh.network.AvailableCommand;

public class ShowCmd extends AbstractCommand implements ClientAvailable {
	
	public ShowCmd(CommandHelper.Holder h) {
		super(h, "show", ": вывести все элементы коллекции в строковом представлении");
	}
	
	@Override
	public String execute(String[] args) {
		if(list.size() == 0)
			return "Коллекция пуста";
		StringBuilder out = new StringBuilder();
		list.forEach(ticket -> out.append("\n").append(ticket));

		return out.substring(1);
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE);
	}
}
