package edsh.command;


import edsh.helpers.CommandHelper;

public class ExitCmd extends AbstractCommand {

	public ExitCmd(CommandHelper.Holder h) {
		super(h, "exit", ": завершить программу (без сохранения в файл)");
	}
	
	@Override
	public String execute(String[] args) {
		System.out.println("Выход из программы");
		sc.close();
		System.exit(0);
		return "Программа завершена";
	}

}
