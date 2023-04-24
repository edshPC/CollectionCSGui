package edsh.exeptions;

import java.io.IOException;

public class WrongFieldException extends IOException {

	public WrongFieldException(String msg) {
		super(msg);
	}
}
