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
		Ticket t = getAttachment();
		if(t == null) return "!Ошибка при создании билета";
		list.add(t);
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

	@Override
	public Ticket getAttachment() {
		if(attachment == null)
			try {
				return Ticket.getFactory().create(holder.getScanner());
			} catch (WrongFieldException e) {
				return null;
			}
		Ticket temp = attachment;
		attachment = null;
		return temp;
	}


}
