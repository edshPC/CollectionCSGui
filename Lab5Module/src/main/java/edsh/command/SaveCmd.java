package edsh.command;

import edsh.helpers.CommandHelper;
import edsh.helpers.ListHelper;
import org.json.JSONArray;

import edsh.helpers.JsonHelper;
import edsh.mainclasses.Ticket;

public class SaveCmd extends AbstractCommand {

	public SaveCmd(CommandHelper.Holder h) {
		super(h, "save", ": сохранить коллекцию в файл");
	}
	
	@Override
	public String execute(String[] args) {

		if(ListHelper.save(holder.getFileHelper()))
			return "Коллекция сохранена";
		else
			return "!Коллекция не сохранена";
	}

}
