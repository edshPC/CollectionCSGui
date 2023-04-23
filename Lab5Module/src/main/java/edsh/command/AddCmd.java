package edsh.command;

import edsh.exeptions.WrongFieldExeption;
import edsh.helpers.CommandHelper.Holder;
import edsh.mainclasses.Ticket;

public class AddCmd extends AbstractCommand {

	public AddCmd(Holder h) {
		super(h, "add", "{element} : добавить новый элемент в коллекцию");
	}
	
	@Override
	public String execute(String[] args) {
		
		try {
			list.add(Ticket.create(sc));
		} catch (WrongFieldExeption e) {
			return "!Ошибка в создании билета: " + e.getMessage();
		}
		
		return "Билет успешно добавлен!";
	}

}
