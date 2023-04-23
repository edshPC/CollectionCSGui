package edsh.helpers;

import lombok.Getter;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Getter
public class MyScanner implements AutoCloseable {
	private final Scanner sc;
	private final boolean isConsole;
	private boolean isOpen = true;
	
	public MyScanner(Scanner sc, boolean isConsole) {
		this.sc = sc;
		this.isConsole = isConsole;
	}
	
	public boolean hasNextLine() throws IllegalStateException {
		return isOpen && sc.hasNextLine();
	}
	
	public String nextLine() throws IllegalStateException, NoSuchElementException {
		return sc.nextLine();
	}

	@Override
	public void close() {
		sc.close();
		isOpen = false;
	}
}
