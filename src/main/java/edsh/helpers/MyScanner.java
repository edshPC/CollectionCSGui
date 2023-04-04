package edsh.helpers;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MyScanner {
	private final Scanner sc;
	private final boolean isConsole;
	private boolean isOpen = true;
	
	public MyScanner(Scanner sc, boolean isConsole) {
		this.sc = sc;
		this.isConsole = isConsole;
	}

	public Scanner getScanner() {
		return sc;
	}

	public boolean isConsole() {
		return isConsole;
	}
	
	public boolean hasNextLine() throws IllegalStateException {
		return isOpen && sc.hasNextLine();
	}
	
	public String nextLine() throws IllegalStateException, NoSuchElementException {
		return sc.nextLine();
	}
	
	public void close() {
		sc.close();
		isOpen = false;
	}

	public boolean isOpen() {
		return isOpen;
	}
}
