package edsh.command;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.CommandHelper;
import edsh.mainclasses.Event;
import edsh.network.AvailableCommand;

public class RemoveAllByEventCmd extends AbstractCommand implements ClientAvailable, RequireAttachment<Event> {
	private Event attachment = null;

	public RemoveAllByEventCmd(CommandHelper.Holder h) {
		super(h, "remove_all_by_event", "{event} : удалить из коллекции все элементы, значение поля event которого эквивалентно заданному");
	}

	@Override
	public String execute(String[] args) {

		Event ev = getAttachment();
		if(ev == null) return "!Ошибка в создании события";

		int sizeBefore = list.size();
		list.removeIf(check -> check.getEvent().equals(ev));

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

	@Override
	public Event getAttachment() {
		if(attachment == null)
			try {
				return Event.getFactory().create(holder.getScanner());
			} catch (WrongFieldException e) {
				return null;
			}
		Event temp = attachment;
		attachment = null;
		return temp;
	}
}
