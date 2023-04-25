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
		String comment = "";
		if(args != null && args.length > 0) {
			for (int i = 0; i < args.length-1; i++) {
				comment += args[i] + " ";
			}
			comment += args[args.length-1];
		}
			
		
		String out = "";
		for(Ticket ticket : list) {
			if(ticket.getComment().contains(comment))
				out += ticket + "\n";
		}
		if(out.isEmpty())
			return "Нечего вывести";
		
		return "Билеты с данной строкой в комментарии:\n" + out.substring(0, out.length()-1);
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE, AvailableCommand.ArgType.STRING);
	}

}
