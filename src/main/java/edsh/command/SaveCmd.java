package edsh.command;

import java.util.LinkedList;

import org.json.JSONArray;

import edsh.helpers.FileHelper;
import edsh.helpers.JsonHelper;
import edsh.mainclasses.Ticket;

public class SaveCmd implements Command {
	private LinkedList<Ticket> list;
	private FileHelper fh;
	
	public SaveCmd(CommandHelper ch) {
		this.list = ch.getList();
		this.fh = ch.getFileHelper();
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
			return "Коллекция не сохранена";
	}

	@Override
	public String getName() {
		return "save";
	}

}
