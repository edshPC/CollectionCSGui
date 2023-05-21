package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.network.AvailableCommand;

public class InfoCmd extends AbstractCommand implements ClientAvailable {
	
	public InfoCmd(CommandHelper.Holder h) {
		super(h, "info", ": вывести информацию о коллекции (тип, дата инициализации, количество элементов)");
	}
	
	@Override
	public String execute(String[] args) {

		String creationTime = holder.getDataStorage().getCreationTime();

		return "Тип коллекции: " + list.getClass().getSimpleName() + "\n" +
				"Дата инициализации: " + creationTime + "\n" +
				"Количество элементов: " + list.size();
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE);
	}

}
