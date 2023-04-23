package edsh.command;

import edsh.helpers.CommandHelper;

public class RemoveFirstCmd extends AbstractCommand {
	
	public RemoveFirstCmd(CommandHelper.Holder h) {
		super(h, "remove_first", ": удалить первый элемент из коллекции");
	}
	
	
	@Override
	public String execute(String[] args) {
		if(list.size() == 0)
			return "!Нечего удалить";
		
		list.removeFirst();
		
		return "Билет успешно удален!";
	}

}
