package edsh.command;

import edsh.helpers.MyScanner;

public class ExitCmd implements Command {
	
	private MyScanner sc;
		
	public ExitCmd(CommandHelper ch) {
		this.sc = ch.getScanner();
	}
	
	@Override
	public String execute(String[] args) {
		System.out.println("Выход из программы");
		sc.close();
		System.exit(0);
		return "Программа завершена";
	}

	@Override
	public String getName() {
		return "exit";
	}

}
