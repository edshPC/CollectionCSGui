package edsh.command;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;
import edsh.mainclasses.Ticket;
import edsh.network.AvailableCommand;

public class UpdateCmd extends AbstractCommand implements ClientAvailable, RequireAttachment<Ticket> {
	private Ticket attachment = null;

	public UpdateCmd(CommandHelper.Holder h) {
		super(h, "update", "{id} {element} : обновить значение элемента коллекции, id которого равен заданному");
	}

	@Override
	public String execute(String[] args) {
		long id;
		try {
			id = Long.parseLong(args[0]);
		} catch (Exception e) {
			return "!Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'update 5'";
		}
		int index = ListHelper.getIndexById(id);
		if(index < 0)
			return "!Не найден билет с данным id. Используйте add чтобы добавить новый";

		Ticket t = getAttachment();
		if(t == null) return "!Ошибка при создании билета";
		Ticket.putWithId(index, id, t);

		return "Билет успешно обновлен!";
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.TICKET, AvailableCommand.ArgType.LONG);
	}

	@Override
	public void setAttachment(Ticket ticket) {
		attachment = ticket;
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
