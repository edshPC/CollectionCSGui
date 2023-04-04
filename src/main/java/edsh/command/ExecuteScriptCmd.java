package edsh.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import edsh.helpers.FileHelper;
import edsh.helpers.MyScanner;
import edsh.mainclasses.Ticket;

public class ExecuteScriptCmd implements Command {
	private LinkedList<Ticket> list;
	private FileHelper fh;
	private static ArrayList<Path> invokes = new ArrayList<>();
	
	public ExecuteScriptCmd(CommandHelper ch) {
		this.list = ch.getList();
		this.fh = ch.getFileHelper();
	}
	
	@Override
	public String execute(String[] args) {
		MyScanner sc;
		Path path;
		try {
			path = Paths.get(args[1]);
			sc = new MyScanner(new Scanner(path), false);
		} catch (Exception e) {
			return "Файла с таким названием по этому пути не обнаружено. Создайте, например, файл script.txt в каталоге с программой и введите 'execute_script script.txt'";
		}
		CommandHelper commandHelper = new CommandHelper(list, sc, fh);
		commandHelper.registerAllCommands();
		if(invokes.contains(path)) {
			invokes.clear();
			sc.close();
			return "Обнаружена рекурсия. Провертье, не запускает ли скрипт сам себя";
		}
		invokes.add(path);
		while (sc.hasNextLine()) {
			commandHelper.executeNextCommand();
		}
		invokes.clear();
		sc.close();
		return "Скрипт завершен";
	}

	@Override
	public String getName() {
		return "execute_script";
	}

}
