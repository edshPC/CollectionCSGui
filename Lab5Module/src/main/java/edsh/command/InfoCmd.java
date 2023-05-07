package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.network.AvailableCommand;

import java.nio.file.attribute.FileTime;

public class InfoCmd extends AbstractCommand implements ClientAvailable {
	
	public InfoCmd(CommandHelper.Holder h) {
		super(h, "info", ": вывести информацию о коллекции (тип, дата инициализации, количество элементов)");
	}
	
	@Override
	public String execute(String[] args) {
		FileTime fileTime = holder.getFileHelper().getCreationTime();
		String creationTime = "[не сохранена]";
		if(fileTime != null) {
			String[] times = fileTime.toString().split("[T.]");
			creationTime = times[0] + " " + times[1];
		}

		String out = "Тип коллекции: " + list.getClass().getSimpleName() + "\n" +
				"Дата инициализации: " + creationTime + "\n" +
				"Количество элементов: " + list.size();
		return out;
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE);
	}

}
