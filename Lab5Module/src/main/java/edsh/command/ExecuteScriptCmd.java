package edsh.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

import edsh.helpers.CommandHelper;
import edsh.helpers.MyScanner;

public class ExecuteScriptCmd extends AbstractCommand {
	private final LinkedList<Path> invokes = new LinkedList<>();
	private final CommandHelper commandHelper;

	public ExecuteScriptCmd(CommandHelper commandHelper) {
		super(commandHelper.getHolder(), "execute_script", "{file_name} : считать и исполнить скрипт из указанного файла");
		this.commandHelper = commandHelper;
	}

	@Override
	public String execute(String[] args) {
		MyScanner sc;
		Path path;
		try {
			path = Paths.get(args[0]);
			sc = new MyScanner(new Scanner(path), false);
		} catch (Exception e) {
			return "!Файла с таким названием по этому пути не обнаружено. Создайте, например, файл script.txt в каталоге с программой и введите 'execute_script script.txt' или измените права";
		}
		if(invokes.contains(path)) {
			sc.close();
			return "!Обнаружена рекурсия. Провертье, не запускает ли скрипт сам себя";
		}
		MyScanner previous = commandHelper.getHolder().getScanner();
		commandHelper.getHolder().setScanner(sc);
		invokes.add(path);
		while (sc.hasNextLine() && commandHelper.executeNextCommand());
		invokes.removeLast();
		commandHelper.getHolder().setScanner(previous);
		sc.close();
		return "Скрипт завершен";
	}
}
