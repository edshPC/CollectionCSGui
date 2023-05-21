package edsh.helpers;

import edsh.mainclasses.Ticket;

import java.util.LinkedList;

public interface DataStorage {
    LinkedList<Ticket> readAll();
    boolean saveAll(LinkedList<Ticket> list);
    String getCreationTime();
}
