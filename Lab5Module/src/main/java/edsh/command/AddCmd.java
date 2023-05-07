package edsh.command;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.CommandHelper.Holder;
import edsh.mainclasses.Ticket;
import edsh.network.AvailableCommand;

public class AddCmd extends AbstractCommand implements ClientAvailable, RequireAttachment<Ticket> {
	private Ticket attachment = null;

	public AddCmd(Holder h) {
		super(h, "add", "{element} : добавить новый элемент в коллекцию");
	}
	
	@Override
	public String execute(String[] args) {

		if(attachment == null)
			try {
				attachment = Ticket.getFactory().create(holder.getScanner());
			} catch (WrongFieldException e) {
				return "!Ошибка в создании билета: " + e.getMessage();
			}

		list.add(attachment);
		attachment = null;
		return "Билет успешно добавлен!";
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.TICKET);
	}

	@Override
	public void setAttachment(Ticket ticket) {
		attachment = ticket;
		ticket.updateId();
	}
}
