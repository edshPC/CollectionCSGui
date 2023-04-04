package edsh.command;

import java.util.LinkedList;

import edsh.mainclasses.Ticket;

public class FilterContainsCommentCmd implements Command {
	private LinkedList<Ticket> list;
	
	public FilterContainsCommentCmd(CommandHelper ch) {
		this.list = ch.getList();
	}
	
	
	@Override
	public String execute(String[] args) {
		String comment = "";
		if(args.length > 1) {
			for (int i = 1; i < args.length-1; i++) {
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
	public String getName() {
		return "filter_contains_comment";
	}

}
