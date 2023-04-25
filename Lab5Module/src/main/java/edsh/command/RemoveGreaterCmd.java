package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;
import edsh.network.AvailableCommand;

public class RemoveGreaterCmd extends AbstractCommand implements ClientAvailable {
	
	public RemoveGreaterCmd(CommandHelper.Holder h) {
		super(h, "remove_greater", "{id} : удалить из коллекции все элементы, превышающие заданный");
	}
	
	
	@Override
	public String execute(String[] args) {
		long id;
		try {
			id = Long.parseLong(args[1]);
		} catch (Exception e) {
			return "!Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'remove_greater 5'";
		}

		ListHelper.sortList();
		int index = ListHelper.getIndexById(id);
		if(index < 0)
			return "!Не найден билет с данным id";
		else if(index == 0)
			return "!Не найдено билетов выше данного";
		
		for (int i = 0; i < index; i++) {
			list.removeFirst();
		}
		
		return "Было удалено " + index + " билетов";
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE, AvailableCommand.ArgType.LONG);
	}

}
