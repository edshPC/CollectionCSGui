package edsh.command;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;
import edsh.mainclasses.Ticket;

public class UpdateCmd extends AbstractCommand {

	public UpdateCmd(CommandHelper.Holder h) {
		super(h, "update", "{id} {element} : обновить значение элемента коллекции, id которого равен заданному");
	}

	@Override
	public String execute(String[] args) {
		long id;
		try {
			id = Long.parseLong(args[1]);
		} catch (Exception e) {
			return "!Не указан или указан неверно id элемента. Пожалуйста укажите id как число, пример: 'update 5'";
		}
		int index = ListHelper.getIndexById(id);
		if(index < 0)
			return "!Не найден билет с данным id. Используйте add чтобы добавить новый";

		try {
			Ticket.putWithId(index, id, Ticket.getFactory().create(sc));
		} catch (WrongFieldException e) {
			System.err.println("Ошибка в создании билета: " + e.getMessage());
			return "!Билет не обновлен";
		}

		return "Билет успешно обновлен!";
	}

}
