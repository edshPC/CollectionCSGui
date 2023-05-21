package edsh;

import java.util.Scanner;

import edsh.helpers.*;

public class Main {

	public static void main(String[] args) {
		Printer printer = new ConsolePrinter();
		String filename = "source.json";
		if(args.length > 0)
			filename = args[0];
		else
			printer.println("Вы не ввели название файла в аргументах. Попытка загрузки из файла по умолчанию: 'source.json'");

		FileHelper fileHelper = new FileHelper(filename, printer);
		DataStorage dataStorage = new FileStorage(fileHelper);
		int loaded = ListHelper.load(dataStorage);

		if(loaded >= 0) {
			printer.println("Загружено " + loaded + " элементов в коллекцию");
		}


		MyScanner sc = new MyScanner(new Scanner(System.in), true);
		CommandHelper commandHelper = new CommandHelper(sc, dataStorage);
		commandHelper.registerAllCommands();

		do {
			printer.print("> ");
		} while(commandHelper.executeNextCommand());
		printer.println("Программа завершена");

	}

}
