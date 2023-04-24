package edsh.command;


import edsh.exeptions.ExitExecutedException;
import edsh.helpers.CommandHelper;


public class ExitCmd extends AbstractCommand {

	public ExitCmd(CommandHelper.Holder h) {
		super(h, "exit", ": завершить программу (без сохранения в файл)");
	}
	
	@Override
	public String execute(String[] args) throws ExitExecutedException {
		sc.close();
		throw new ExitExecutedException(0);
		//return "Программа завершена";
	}

}
