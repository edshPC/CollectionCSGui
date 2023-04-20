package edsh.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerPrinter implements Printer {
    private final Logger logger;
    public LoggerPrinter(String name) {
        logger = LoggerFactory.getLogger(name);
    }


    @Override
    public void print(String str) {
        System.out.print(str);
    }

    @Override
    public void println(String str) {
        logger.info(str);
    }

    @Override
    public void errPrint(String str) {
        System.err.print(str);
    }

    @Override
    public void errPrintln(String str) {
        logger.error(str);
    }
}
