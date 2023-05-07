package edsh.command;

import edsh.helpers.CommandHelper.Holder;

import java.util.HashMap;

public class HelpCmd extends AbstractCommand {

	public HelpCmd(Holder h) {
		super(h, "help", ": вывести справку по доступным командам");
	}

	@Override
	public String execute(String[] args) {
		StringBuilder out = new StringBuilder();
		for(Command command : holder.getCommands().values()) {
			out.append('\n').append(command.toString());
		}
		return out.substring(1);
	}

}
