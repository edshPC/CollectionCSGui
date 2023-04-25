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
			id = Long.parseLong(args[1]);
		} catch (Exception e) {
			return "!Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'update 5'";
		}
		int index = ListHelper.getIndexById(id);
		if(index < 0)
			return "!Не найден билет с данным id. Используйте add чтобы добавить новый";

		if(attachment == null)
			try {
				attachment = Ticket.getFactory().create(sc);
			} catch (WrongFieldException e) {
				return "!Билет не обновлен";
			}
		Ticket.putWithId(index, id, attachment);
		attachment = null;

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
}
