package edsh.mainclasses;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.MyScanner;

import java.util.NoSuchElementException;

public interface MainclassFactory<T> {
    T create(MyScanner sc) throws WrongFieldException, NoSuchElementException;
}
