package edsh.helpers;

import edsh.mainclasses.Event;
import edsh.mainclasses.Ticket;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseHelper {
    private final Printer printer = new LoggerPrinter("DatabaseHelper");
    @Getter
    private Connection connection;

    public boolean connect(String propertiesFile) {
        Properties properties = new Properties();
        try (FileReader fr = new FileReader(propertiesFile)) {
            properties.load(fr);
        } catch (IOException e) {
            printer.errPrintln("Ошибка в чтении конфига БД");
            return false;
        }
        String dbName = properties.getProperty("db");
        if(dbName == null) {
            printer.errPrintln("Название БД не указано в настройках. Укажите 'db=name' в файле");
            return false;
        }

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql:" + dbName, properties);
        } catch (ClassNotFoundException e) {
            printer.errPrintln("Драйвер БД не найден");
            return false;
        } catch (SQLException e) {
            printer.errPrintln("Не удалось подключиться к БД: " + e.getMessage());
            return false;
        }
        printer.println("Подключение к базе данных '" + dbName + "' успешно");
        return true;
    }

    public boolean createTables() {
        try {
            Statement st = connection.createStatement();
            st.execute(SQLRequests.tablesCreation);
        } catch (SQLException e) {
            printer.errPrintln("Ошибка создания необходимых таблиц: " + e.getMessage());
            return false;
        }
        return true;
    }

    public ResultSet executeQuery(String query) {
        try {
            return connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            printer.errPrintln("Ошибка в выполнении SQL-запроса: " + e.getMessage());
        }
        return null;
    }

    public boolean insertTicket(Ticket t, String owner) {
        try {
            Event ev = t.getEvent();
            PreparedStatement st = connection.prepareStatement(SQLRequests.addEvent);
            setEventFields(st, ev, 0);
            ResultSet evId = st.executeQuery();
            evId.next();
            ev.setId(evId.getLong(1));
            ev.updateLastId();

            st = connection.prepareStatement(SQLRequests.addTicket);
            setTicketFields(st, t, 0);
            st.setString(9, owner);
            ResultSet tId = st.executeQuery();
            tId.next();
            t.setId(tId.getLong(1));
            t.updateLastId();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            printer.errPrintln("Ошибка в выполнении SQL-запроса: " + e.getMessage());
        }
        return false;
    }

    public boolean updateTicket(Ticket t) {
        try {
            if(!containsTicket(t)) return false;
            Event ev = t.getEvent();
            PreparedStatement st = connection.prepareStatement(SQLRequests.updateEvent);
            st.setLong(1, ev.getId());
            setEventFields(st, ev, 1);
            st.execute();

            st = connection.prepareStatement(SQLRequests.updateTicket);
            st.setLong(1, t.getId());
            setTicketFields(st, t, 1);
            st.execute();
            return true;
        } catch (SQLException e) {
            printer.errPrintln("Ошибка в выполнении SQL-запроса: " + e.getMessage());
        }
        return false;
    }

    public boolean containsTicket(Ticket t) throws SQLException {
        PreparedStatement st = connection.prepareStatement(SQLRequests.hasTicket);
        st.setLong(1, t.getId());
        ResultSet set = st.executeQuery();
        set.next();
        return set.getBoolean(1);
    }

    public boolean containsUser(String login) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQLRequests.hasLogin);
            ps.setString(1, login);
            ResultSet set = ps.executeQuery();
            set.next();
            return set.getBoolean(1);
        } catch (SQLException ignored) {}
        return false;
    }
    public boolean addUser(String login, String passHash) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQLRequests.addUser);
            ps.setString(1, login);
            ps.setString(2, passHash);
            return ps.executeUpdate() == 1;
        } catch (SQLException ignored) {}
        return false;
    }
    public boolean checkPassHash(String login, String passHash) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQLRequests.getPassHash);
            ps.setString(1, login);
            ResultSet set = ps.executeQuery();
            set.next();
            return passHash.equals(set.getString(1));
        } catch (SQLException ignored) {}
        return false;
    }

    private void setEventFields(PreparedStatement st, Event ev, int offset) throws SQLException {
        st.setString(++offset, ev.getName());
        st.setDate(++offset, Date.valueOf(ev.getDate()));
        st.setLong(++offset, ev.getMinAge());
        st.setLong(++offset, ev.getTicketsCount());
        st.setString(++offset, ev.getEventType().name());
    }
    private void setTicketFields(PreparedStatement st, Ticket t, int offset) throws SQLException {
        st.setString(++offset, t.getName());
        st.setFloat(++offset, t.getCoordinates().getX());
        st.setInt(++offset, t.getCoordinates().getY());
        st.setTimestamp(++offset, Timestamp.valueOf(t.getCreationDate().toLocalDateTime()));
        st.setLong(++offset, t.getPrice());
        st.setString(++offset, t.getComment());
        st.setString(++offset, t.getType().name());
        st.setLong(++offset, t.getEvent().getId());
    }

}
