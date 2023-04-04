package edsh.command;

import java.util.LinkedList;

import edsh.mainclasses.Ticket;

public class RemoveLowerCmd implements Command {
	private LinkedList<Ticket> list;
	
	public RemoveLowerCmd(CommandHelper ch) {
		this.list = ch.getList();
	}
	
	
	@Override
	public String execute(String[] args) {
		long id;
		try {
			id = Long.valueOf(args[1]);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			return "Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'remove_lower 5'";
		}
		
		Ticket.sortList();
		int index = Ticket.getIndexById(id);
		if(index < 0)
			return "Не найден билет с данным id";
		else if(index == list.size()-1)
			return "Не найдено билетов ниже данного";
		
		for (int i = index; i < list.size()-1; i++) {
			list.removeLast();
		}
		
		return "Было удалено " + index + " билетов";
	}

	@Override
	public String getName() {
		return "remove_lower";
	}

}
