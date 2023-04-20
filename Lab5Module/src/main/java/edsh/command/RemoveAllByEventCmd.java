package edsh.command;

import java.util.LinkedList;

import edsh.exeptions.WrongFieldExeption;
import edsh.helpers.MyScanner;
import edsh.mainclasses.Event;
import edsh.mainclasses.Ticket;

public class RemoveAllByEventCmd implements Command {
	private LinkedList<Ticket> list;
	private MyScanner sc;
	
	public RemoveAllByEventCmd(CommandHelper ch) {
		this.list = ch.getList();
		this.sc = ch.getScanner();
	}
	
	
	@Override
	public String execute(String[] args) {
		
		System.out.println("Задай событие, по которому удалить билет:");
		Event ev = null;
		try {
			ev = Event.create(sc);
		} catch (WrongFieldExeption e) {
			System.err.println("Ошибка в создании события: " + e.getMessage());
			return "Событие не содано, ничего не удалено";
		}
		
		int count = 0;
		
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getEvent().equals(ev)) {
				list.remove(i);
				count++;
			}
		}
		
		if(count == 0)
			return "Не найдено билетов с таким событием";
		
		return "Удалено " + count + " билетов";
	}

	@Override
	public String getName() {
		return "remove_all_by_event";
	}

}
