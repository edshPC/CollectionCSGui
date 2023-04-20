package edsh.helpers;

public class ConsolePrinter implements Printer {

    @Override
    public void print(String str) {
        System.out.print(str);
    }

    @Override
    public void println(String str) {
        System.out.println(str);
    }

    @Override
    public void errPrint(String str) {
        System.err.print(str);
    }

    @Override
    public void errPrintln(String str) {
        System.err.println(str);
    }
}
