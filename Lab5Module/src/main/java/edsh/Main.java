package edsh;

import java.util.LinkedList;
import java.util.Scanner;

import edsh.command.*;
import edsh.helpers.*;
import edsh.mainclasses.Ticket;

//lombok - for auto getters/setters

public class Main {

	public static void main(String[] args) {
		String filename = "source.json";
		if(args.length > 0)
			filename = args[0];
		else
			System.out.println("Вы не ввели название файла в аргументах. Попытка загрузки из файла по умолчанию: 'source.json'");
		
		FileHelper fileHelper = new FileHelper(filename);
		LinkedList<Ticket> tickets = new LinkedList<>();
		
		if(fileHelper.readFile()) {
			JsonHelper jHelper = new JsonHelper(fileHelper.getRawJson());
			if(jHelper.parseRawJson()) {
				tickets = jHelper.toLinkedList();
				System.out.println("Загружено " + tickets.size() + " элементов в коллекцию");
			}
		}
		Ticket.setList(tickets);
		//Ticket.sortList();
		
		MyScanner sc = new MyScanner(new Scanner(System.in), true);
		CommandHelper commandHelper = new CommandHelper(tickets, sc, fileHelper);
		commandHelper.registerAllCommands();
		
		while(commandHelper.executeNextCommand());
		

	}
	
}
