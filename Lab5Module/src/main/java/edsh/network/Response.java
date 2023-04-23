package edsh.network;

import edsh.helpers.Printer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Response implements Serializable {
    public enum Status {
        OK,
        ERROR
    }

    private final Status status;
    private final String response;

    public void print(Printer printer) {
        switch (status) {
            case OK -> printer.print(response);
            case ERROR -> printer.errPrint(response);
        }
    }

}
