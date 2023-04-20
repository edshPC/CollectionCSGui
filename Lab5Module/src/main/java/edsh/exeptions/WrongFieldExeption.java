package edsh.exeptions;

import java.io.IOException;

public class WrongFieldExeption extends IOException {
	
	private static final long serialVersionUID = -942013368506337745L;

	public WrongFieldExeption(String msg) {
		super(msg);
	}
}
