package edsh.mainclasses;

import edsh.exeptions.WrongFieldException;
import edsh.helpers.MyScanner;

import java.io.Serializable;
import java.util.NoSuchElementException;

public interface MainclassFactory<T extends Serializable> {
    T create(MyScanner sc) throws WrongFieldException, NoSuchElementException;
}
