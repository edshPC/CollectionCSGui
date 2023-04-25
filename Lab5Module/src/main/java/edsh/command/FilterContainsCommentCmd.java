package edsh.command;


import edsh.helpers.CommandHelper.Holder;
import edsh.mainclasses.Ticket;
import edsh.network.AvailableCommand;

public class FilterContainsCommentCmd extends AbstractCommand implements ClientAvailable {
	
	public FilterContainsCommentCmd(Holder h) {
		super(h, "filter_contains_comment", "[comment] : вывести элементы, значение поля comment которых содержит заданную подстроку");
	}
	
	
	@Override
	public String execute(String[] args) {
		if(args == null || args.length == 0) {
			return "!Слишком мало аргументов";
		}

		String comment = args[0];

		StringBuilder out = new StringBuilder();
		list.stream()
				.filter(ticket -> ticket.getComment().contains(comment))
				.forEach(ticket -> out.append("\n").append(ticket));

		if(out.length() == 0)
			return "Нечего вывести";
		
		return "Билеты с данной строкой в комментарии:\n" + out.substring(1);
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE, AvailableCommand.ArgType.STRING);
	}

}
