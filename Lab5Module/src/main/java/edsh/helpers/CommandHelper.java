package edsh.helpers;

import java.util.*;

import edsh.command.*;
import edsh.exeptions.ExitExecutedException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CommandHelper {

	@Getter
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Holder {
		@Setter
		private MyScanner scanner;
		private final DataStorage dataStorage;
		private final HashMap<String, Command> commands = new HashMap<>();
	}

	@Getter
	private final Holder holder;

	/**
	 * Создает объект, работающий с командами из определенного потока в {@link Scanner}
	 * @param sc Сканер
	 * @param ds Помошник с файлом коллекции
	 */
	public CommandHelper(MyScanner sc, DataStorage ds) {
		this.holder = new Holder(sc, ds);
	}

	/**
	 * Регистрирует данные команды, сохраняя ее имя и объект в {@link HashMap}
	 * @param cmds Команды
	 */
	public void registerCommands(Command... cmds) {
		for(Command cmd : cmds) {
			holder.commands.put(cmd.getName(), cmd);
		}
	}

	/**
	 * Регистрирует все возможные команды
	 */
	public void registerAllCommands() {
		registerCommands(new HelpCmd(holder), new InfoCmd(holder), new ShowCmd(holder), new AddCmd(holder),
				new UpdateCmd(holder), new RemoveByIdCmd(holder), new ClearCmd(holder), new SaveCmd(holder),
				new ExecuteScriptCmd(this), new ExitCmd(holder), new RemoveFirstCmd(holder),
				new RemoveGreaterCmd(holder), new RemoveLowerCmd(holder), new RemoveAllByEventCmd(holder),
				new FilterContainsCommentCmd(holder), new PrintUniquePriceCmd(holder), new SortCmd(holder));
	}

	public boolean executeCommand(String commandStr, String[] args, Printer printer) throws NoSuchElementException, ExitExecutedException {
		if(!holder.commands.containsKey(commandStr)) {
			printer.errPrintln("Данной команды не существует. Введите 'help' для просмотра списка команд");
			return false;
		}
		Command command = holder.commands.get(commandStr);
		String out = command.execute(args);
		if(out.startsWith("!")) {
			printer.errPrintln(out.substring(1));
		} else {
			printer.println(out);
		}
		return true;
	}

	/**
	 * Начинает ожидание ввода и исполнение команды пользователя, считывая через собственный {@link Scanner}
	 */
	public boolean executeNextCommand() {
		Printer console = new ConsolePrinter();
		MyScanner sc = holder.getScanner();
		try {
			String cmdStr = sc.nextLine().trim();
			if(!sc.isConsole())
				console.println(cmdStr);
			String[] cmd = cmdStr.split(" +(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); //Разделяем по пробелу не в кавычках
			String[] args = ArgsHelper.fromSplitedCommand(cmd).getArgs();

			if(cmd.length > 0 && !cmd[0].isEmpty()) {
				executeCommand(cmd[0], args, console);
			}
		} catch (NoSuchElementException e) {
			console.errPrintln("Ввод команд был прерван");
			return false;
		} catch (ExitExecutedException e) {
			return false;
		}
		return true;
	}

}
