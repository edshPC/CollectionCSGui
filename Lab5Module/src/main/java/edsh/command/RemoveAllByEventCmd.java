package edsh.command;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.CommandHelper;
import edsh.mainclasses.Event;
import edsh.mainclasses.Ticket;

import java.util.Iterator;

public class RemoveAllByEventCmd extends AbstractCommand {

	public RemoveAllByEventCmd(CommandHelper.Holder h) {
		super(h, "remove_all_by_event", "{event} : удалить из коллекции все элементы, значение поля event которого эквивалентно заданному");
	}
	
	
	@Override
	public String execute(String[] args) {
		
		System.out.println("Задай событие, по которому удалить билет:");
		Event ev = null;
		try {
			ev = Event.getFactory().create(sc);
		} catch (WrongFieldException e) {
			System.err.println("Ошибка в создании события: " + e.getMessage());
			return "Событие не содано, ничего не удалено";
		}
		
		int count = 0;
		
		for(Iterator<Ticket> it = list.iterator(); it.hasNext();) {
			Ticket ticket = it.next();
			if(ticket.getEvent().equals(ev)){
				it.remove();
				count++;
			}
		}
		
		if(count == 0)
			return "!Не найдено билетов с таким событием";
		
		return "Удалено " + count + " билетов";
	}
}
