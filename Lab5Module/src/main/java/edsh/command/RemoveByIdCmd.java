package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;

public class RemoveByIdCmd extends AbstractCommand {
	
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
		int index = ListHelper.getIndexById(id);
		if(index < 0)
			return "!Не найден билет с данным id";
		
		list.remove(index);
		
		return "Билет успешно удален!";
	}

}
