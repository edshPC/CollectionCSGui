package edsh.command;


import edsh.exeptions.ExitExecutedException;
import edsh.helpers.CommandHelper;


public class ExitCmd extends AbstractCommand {

	public ExitCmd(CommandHelper.Holder h) {
		super(h, "exit", ": завершить программу");
	}
	
	@Override
	public String execute(String[] args) throws ExitExecutedException {
		holder.getScanner().close();
		throw new ExitExecutedException(0);
		//return "Программа завершена";
	}

}
