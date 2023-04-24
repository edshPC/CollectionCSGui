package edsh.command;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.CommandHelper.Holder;
import edsh.mainclasses.Ticket;
import edsh.network.AvailableCommand;

public class AddCmd extends AbstractCommand implements ClientAvailable {

	public AddCmd(Holder h) {
		super(h, "add", "{element} : добавить новый элемент в коллекцию");
	}
	
	@Override
	public String execute(String[] args) {
		
		try {
			list.add(Ticket.getFactory().create(sc));
		} catch (WrongFieldException e) {
			return "!Ошибка в создании билета: " + e.getMessage();
		}
		
		return "Билет успешно добавлен!";
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.TICKET);
	}
}
