package edsh.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edsh.helpers.FileHelper;
import edsh.helpers.MyScanner;
import edsh.mainclasses.Ticket;

public class CommandHelper {
	private LinkedList<Ticket> list;
	private FileHelper fileHelper;
	private MyScanner sc;
	private HashMap<String, Command> commands;
	
	/**
	 * Создает объект, работающий с командами из определенного потока в {@link Scanner}
	 * @param list Коллекция для обработки
	 * @param sc Сканер
	 * @param fh Файловый помошник с файлом коллекции
	 */
	public CommandHelper(LinkedList<Ticket> list, MyScanner sc, FileHelper fh) {
		this.list = list;
		this.fileHelper = fh;
		this.sc = sc;
		commands = new HashMap<>();
	}
	
	/**
	 * Регистрирует данные команды, сохраняя ее имя и объект в {@link HashMap}
	 * @param cmds Команды
	 */
	public void registerCommands(Command... cmds) {
		for(Command cmd : cmds) {
			this.commands.put(cmd.getName(), cmd);
		}
	}
	
	/**
	 * Регистрирует все возможные команды
	 */
	public void registerAllCommands() {
		registerCommands(new HelpCmd(this), new InfoCmd(this), new ShowCmd(this), new AddCmd(this),
				new UpdateCmd(this), new RemoveByIdCmd(this), new ClearCmd(this), new SaveCmd(this),
				new ExecuteScriptCmd(this), new ExitCmd(this), new RemoveFirstCmd(this),
				new RemoveGreaterCmd(this), new RemoveLowerCmd(this), new RemoveAllByEventCmd(this),
				new FilterContainsCommentCmd(this), new PrintUniquePriceCmd(this), new SortCmd(this));
	}
	
	
	
	/**
	 * Начинает ожидание ввода и исполнение команды пользователя, считывая через собственный {@link Scanner}
	 */
	public boolean executeNextCommand() {
		System.out.print("> ");
		try {
			String cmdStr = sc.nextLine();
			if(!sc.isConsole())
				System.out.println(cmdStr);
			String[] cmd = cmdStr.split(" +");
			
			if(cmd.length > 0 && commands.containsKey(cmd[0])) {
				Command command = commands.get(cmd[0]);
				System.out.println(command.execute(cmd));
			} else {
				if(cmd.length > 0 && !cmd[0].isEmpty())
					System.err.println("Данной команды не существует. Введите 'help' для просмотра списка команд");
			}
			
		} catch (NoSuchElementException e) {
			System.err.println("Ввод команд был прерван");
			return false;
		}
		return true;
		//executeNextCommand();
	}

	public LinkedList<Ticket> getList() {
		return list;
	}

	public FileHelper getFileHelper() {
		return fileHelper;
	}
	
	public MyScanner getScanner() {
		return sc;
	}
}
