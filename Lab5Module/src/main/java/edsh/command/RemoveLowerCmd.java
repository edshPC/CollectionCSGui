package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;
import edsh.network.AvailableCommand;

public class RemoveLowerCmd extends AbstractCommand implements ClientAvailable {
	
	public RemoveLowerCmd(CommandHelper.Holder h) {
		super(h, "remove_lower", "{id} : удалить из коллекции все элементы, меньшие, чем заданный");
	}

	@Override
	public String execute(String[] args) {
		long id;
		try {
			id = Long.parseLong(args[1]);
		} catch (Exception e) {
			return "!Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'remove_lower 5'";
		}

		ListHelper.sortList();
		int index = ListHelper.getIndexById(id);
		if(index < 0)
			return "!Не найден билет с данным id";
		else if(index == list.size()-1)
			return "!Не найдено билетов ниже данного";
		
		for (int i = index; i < list.size()-1; i++) {
			list.removeLast();
		}
		
		return "Было удалено " + index + " билетов";
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE, AvailableCommand.ArgType.LONG);
	}

}
