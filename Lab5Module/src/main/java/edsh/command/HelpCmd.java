package edsh.command;

import edsh.helpers.CommandHelper.Holder;

import java.util.HashMap;

public class HelpCmd extends AbstractCommand {
	private final HashMap<String, Command> commands;
	
	public HelpCmd(Holder h) {
		super(h, "help", ": вывести справку по доступным командам");
		commands = h.getCommands();
	}
	
	@Override
	public String execute(String[] args) {
		String out = "";
		for(Command command : commands.values()) {
			out += '\n' + command.toString();
		}
		return out.substring(1);
	}

}
