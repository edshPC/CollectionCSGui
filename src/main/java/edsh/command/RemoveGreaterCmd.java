package edsh.command;

import java.util.LinkedList;

import edsh.mainclasses.Ticket;

public class RemoveGreaterCmd implements Command {
	private LinkedList<Ticket> list;
	
	public RemoveGreaterCmd(CommandHelper ch) {
		this.list = ch.getList();
	}
	
	
	@Override
	public String execute(String[] args) {
		long id;
		try {
			id = Long.valueOf(args[1]);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			return "Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'remove_greater 5'";
		}
		
		Ticket.sortList();
		int index = Ticket.getIndexById(id);
		if(index < 0)
			return "Не найден билет с данным id";
		else if(index == 0)
			return "Не найдено билетов выше данного";
		
		for (int i = 0; i < index; i++) {
			list.removeFirst();
		}
		
		return "Было удалено " + index + " билетов";
	}

	@Override
	public String getName() {
		return "remove_greater";
	}

}
