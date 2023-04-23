package edsh.command;

import edsh.helpers.CommandHelper;
import org.json.JSONArray;

import edsh.helpers.JsonHelper;
import edsh.mainclasses.Ticket;

public class SaveCmd extends AbstractCommand {

	public SaveCmd(CommandHelper.Holder h) {
		super(h, "save", ": сохранить коллекцию в файл");
	}
	
	@Override
	public String execute(String[] args) {
		JSONArray arr = new JSONArray();		
		for(Ticket ticket : list) {
			arr.put(ticket.toJsonObject());
		}
		JsonHelper jh = new JsonHelper(arr);
		jh.stringifyJsonArr();
		fh.setRawJson(jh.getRawJson());
		if(fh.writeToFile())
			return "Коллекция сохранена";
		else
			return "!Коллекция не сохранена";
	}

}
