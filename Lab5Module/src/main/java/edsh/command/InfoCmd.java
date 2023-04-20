package edsh.command;

import java.util.LinkedList;

import edsh.helpers.FileHelper;
import edsh.mainclasses.Ticket;

public class InfoCmd implements Command {
	private LinkedList<Ticket> list;
	private FileHelper fh;
	
	public InfoCmd(CommandHelper ch) {
		this.list = ch.getList();
		this.fh = ch.getFileHelper();
	}
	
	@Override
	public String execute(String[] args) {
		String[] creationTime = fh.getCreationTime().toString().split("[T.]");
		String out = "Тип коллекции: LinkedList\n" +
				"Дата инициализации: " + creationTime[0] + " " + creationTime[1] + "\n" +
				"Количество элементов: " + list.size();
		return out;
	}

	@Override
	public String getName() {
		return "info";
	}

}
