package edsh.helpers;

import edsh.mainclasses.Ticket;

import java.util.Deque;

public interface DataStorage {
    Deque<Ticket> readAll();
    boolean saveAll(Deque<Ticket> list);
    String getCreationTime();
}
