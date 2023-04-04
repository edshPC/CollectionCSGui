package edsh.command;

import java.util.LinkedList;

import edsh.mainclasses.Ticket;

public class RemoveByIdCmd implements Command {
	private LinkedList<Ticket> list;
	
	public RemoveByIdCmd(CommandHelper ch) {
		this.list = ch.getList();
	}
	
	
	@Override
	public String execute(String[] args) {
		long id;
		try {
			id = Long.valueOf(args[1]);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			return "Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'update 5'";
		}
		int index = Ticket.getIndexById(id);
		if(index < 0)
			return "Не найден билет с данным id";
		
		list.remove(index);
		
		return "Билет успешно удален!";
	}

	@Override
	public String getName() {
		return "remove_by_id";
	}

}
