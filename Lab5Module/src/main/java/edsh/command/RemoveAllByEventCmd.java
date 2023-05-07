package edsh.command;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.CommandHelper;
import edsh.mainclasses.Event;
import edsh.mainclasses.Ticket;
import edsh.network.AvailableCommand;

import java.util.Iterator;

public class RemoveAllByEventCmd extends AbstractCommand implements ClientAvailable, RequireAttachment<Event> {
	private Event attachment = null;

	public RemoveAllByEventCmd(CommandHelper.Holder h) {
		super(h, "remove_all_by_event", "{event} : удалить из коллекции все элементы, значение поля event которого эквивалентно заданному");
	}

	@Override
	public String execute(String[] args) {

		if(attachment == null)
			try {
				attachment = Event.getFactory().create(holder.getScanner());
			} catch (WrongFieldException e) {
				return "!Ошибка в создании события: " + e.getMessage();
			}

		int sizeBefore = list.size();
		list.removeIf(check -> check.getEvent().equals(attachment));

		attachment = null;
		int count = sizeBefore - list.size();
		if(count == 0)
			return "!Не найдено билетов с таким событием";
		
		return "Удалено " + count + " билетов";
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.EVENT);
	}

	@Override
	public void setAttachment(Event event) {
		attachment = event;
	}
}
