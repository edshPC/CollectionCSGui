package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;
import edsh.mainclasses.Ticket;
import edsh.network.AvailableCommand;

public class RemoveLowerCmd extends AbstractCommand implements ClientAvailable {
	
	public RemoveLowerCmd(CommandHelper.Holder h) {
		super(h, "remove_lower", "{id} : удалить из коллекции все элементы, меньшие, чем заданный");
	}

	@Override
	public String execute(String[] args) {
		long id;
		try {
			id = Long.parseLong(args[0]);
		} catch (Exception e) {
			return "!Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'remove_lower 5'";
		}

		Ticket ticket = ListHelper.getById(id);
		if(ticket == null) return "!Не найден билет с данным id";

		int sizeBefore = list.size();
		list.removeIf(check -> check.compareTo(ticket) < 0);
		
		return "Было удалено " + (sizeBefore - list.size()) + " билетов";
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE, AvailableCommand.ArgType.LONG);
	}

}
