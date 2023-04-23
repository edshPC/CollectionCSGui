package edsh.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

import edsh.helpers.CommandHelper;
import edsh.helpers.MyScanner;

public class ExecuteScriptCmd extends AbstractCommand {
	private static final LinkedList<Path> invokes = new LinkedList<>();
	
	public ExecuteScriptCmd(CommandHelper.Holder h) {
		super(h, "execute_script", "{file_name} : считать и исполнить скрипт из указанного файла");
	}
	
	@Override
	public String execute(String[] args) {
		MyScanner sc;
		Path path;
		try {
			path = Paths.get(args[1]);
			sc = new MyScanner(new Scanner(path), false);
		} catch (Exception e) {
			return "!Файла с таким названием по этому пути не обнаружено. Создайте, например, файл script.txt в каталоге с программой и введите 'execute_script script.txt' или измените права";
		}
		if(invokes.contains(path)) {
			//invokes.clear();
			sc.close();
			return "!Обнаружена рекурсия. Провертье, не запускает ли скрипт сам себя";
		}
		CommandHelper commandHelper = new CommandHelper(sc, fh);
		commandHelper.registerAllCommands();
		invokes.add(path);
		while (sc.hasNextLine()) {
			commandHelper.executeNextCommand();
		}
		invokes.removeLast();
		sc.close();
		return "Скрипт завершен";
	}
}
