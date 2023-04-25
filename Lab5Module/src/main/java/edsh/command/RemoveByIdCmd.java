package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;
import edsh.network.AvailableCommand;

public class RemoveByIdCmd extends AbstractCommand implements ClientAvailable {

	public RemoveByIdCmd(CommandHelper.Holder h) {
		super(h, "remove_by_id", "{id} : удалить элемент из коллекции по его id");
	}


	@Override
	public String execute(String[] args) {
		long id;
		try {
			id = Long.parseLong(args[0]);
		} catch (Exception e) {
			return "!Не указан или указан неверно id элемента. Пожалуйста укажите id как число";
		}

		int sizeBefore = list.size();
		list.removeIf(check -> check.getId() == id);
		if(sizeBefore - list.size() == 0)
			return "!Не найден билет с данным id";

		return "Билет успешно удален!";
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE, AvailableCommand.ArgType.LONG);
	}

}
