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
		int loaded = ListHelper.load(fileHelper);

		if(loaded >= 0) {
			printer.println("Загружено " + loaded + " элементов в коллекцию");
		}


		MyScanner sc = new MyScanner(new Scanner(System.in), true);
		CommandHelper commandHelper = new CommandHelper(sc, fileHelper);
		commandHelper.registerAllCommands();

		while(commandHelper.executeNextCommand());
		printer.println("Программа завершена");

	}

}
