package edsh.command;

import java.util.TreeSet;

import edsh.helpers.CommandHelper;
import edsh.mainclasses.Ticket;
import edsh.network.AvailableCommand;

public class PrintUniquePriceCmd extends AbstractCommand implements ClientAvailable {
	
	public PrintUniquePriceCmd(CommandHelper.Holder h) {
		super(h, "print_unique_price", ": вывести уникальные значения поля price всех элементов в коллекции");
	}
	
	@Override
	public String execute(String[] args) {
		if(list.size() == 0)
			return "Коллекция пуста";
		
		TreeSet<Long> prices = new TreeSet<>();
		
		for(Ticket ticket : list) {
			prices.add(ticket.getPrice());
		}
		return "Надены билеты по следующим ценам:\n" + prices.toString();
	}

	@Override
	public AvailableCommand makeAvailable() {
		return new AvailableCommand(getName(), getDescription(), AvailableCommand.AttachedObject.NONE);
	}

}
