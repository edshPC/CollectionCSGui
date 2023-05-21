package edsh.helpers;

import edsh.enums.EventType;
import edsh.enums.TicketType;
import edsh.exeptions.WrongFieldException;
import edsh.mainclasses.Coordinates;
import edsh.mainclasses.Event;
import edsh.mainclasses.Ticket;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;

@RequiredArgsConstructor
public class DatabaseStorage implements DataStorage {

    private final Printer printer = new LoggerPrinter("DatabaseStorage");
    private final DatabaseHelper db;

    @Override
    public LinkedList<Ticket> readAll() {
        try (ResultSet set = db.executeQuery(SQLRequests.getAllTickets)) {
            if(set == null) return null;
            LinkedList<Ticket> list = new LinkedList<>();
            while (set.next()) {
                Ticket ticket = Ticket.builder()
                        .id(set.getLong(1))
                        .name(set.getString(2))
                        .coordinates(new Coordinates(
                                set.getFloat("x"),
                                set.getInt("y")
                        ))
                        .creationDate(ZonedDateTime.ofInstant(set.getTimestamp("creationDate").toInstant(), ZoneId.systemDefault()))
                        .price(set.getLong("price"))
                        .comment(set.getString("comment"))
                        .type(TicketType.valueOf(set.getString("type")))
                        .event(Event.builder()
                                .id(set.getLong("event"))
                                .name(set.getString(12))
                                .date(set.getDate("date").toLocalDate())
                                .minAge(set.getLong("minage"))
                                .ticketsCount(set.getLong("ticketscount"))
                                .eventType(EventType.valueOf(set.getString("eventtype")))
                                .build())
                        .build();
                ticket.updateLastId();
                list.add(ticket);
            }
            return list;
        } catch (SQLException | WrongFieldException e) {
            printer.errPrintln("Ошибка в загрузке из базы данных: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean saveAll(LinkedList<Ticket> list) {
        for(Ticket ticket : list) {
            if(!db.updateTicket(ticket) && !db.insertTicket(ticket, null)) return false;
        }
        return true;
    }

    @Override
    public String getCreationTime() {
        try {
            ResultSet has = db.executeQuery(SQLRequests.hasTickets);
            has.next();
            if(has.getBoolean(1)) {
                ResultSet time = db.executeQuery(SQLRequests.getOldestTicketCreationTime);
                time.next();
                Timestamp timestamp = time.getTimestamp(1);
                return timestamp.toString().split("\\.")[0];
            }
        } catch (SQLException e) {
            printer.errPrintln("Ошибка в получении даты создания: " + e.getMessage());
        } catch (NullPointerException ignored) {}
        return "[не сохранена]";
    }
}
