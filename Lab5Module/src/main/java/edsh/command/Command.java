package edsh.command;

import java.util.NoSuchElementException;

public interface Command {
	String execute(String[] args) throws NoSuchElementException;
	String getName();
}
