package edsh.mainclasses;

import edsh.enums.EventType;
import edsh.exeptions.WrongFieldException;
import edsh.helpers.ConsolePrinter;
import edsh.helpers.MyScanner;
import edsh.helpers.Printer;

import java.time.LocalDate;
import java.util.NoSuchElementException;

public class EventFactory implements MainclassFactory<Event> {

    /**
     * Начинает создание нового объекта объекта, используя данный сканер
     * @param sc Сканер, который берет информацию
     * @return Новый объект класса {@link Event}
     * @throws WrongFieldException Если поля у созданного объекта неверные
     * @throws NoSuchElementException Если ввод полей отменен
     */
    @Override
    public Event create(MyScanner sc) throws WrongFieldException, NoSuchElementException {
        Printer printer = new ConsolePrinter();
        printer.println("Введи данные события билета:");
        boolean needReask = sc.isConsole();
        String evName = "";
        do {
            printer.print("Введи название:\n>>> ");;
            evName = sc.nextLine();
            if(!needReask)
                printer.println(evName);
            if(evName.isBlank()) {
                printer.errPrintln("Имя события не должно быть пустым");
                continue;
            }
            break;
        } while(needReask);

        LocalDate ld = null;
        do {
            printer.print("Введи дату проведения в формате 'YYYY-MM-DD':\n>>> ");
            String ldStr = sc.nextLine();
            if(!needReask)
                printer.println(ldStr);
            try {
                ld = LocalDate.parse(ldStr);
                break;
            } catch (Exception e) {
                printer.errPrintln("Ошибка при вводе даты: " + e.getMessage());
            }
        } while(needReask);

        long minAge = 0;
        do {
            printer.print("Введи минимальный возраст (число):\n>>> ");
            String minAgeStr = sc.nextLine();
            if(!needReask)
                printer.println(minAgeStr);
            try {
                minAge = Long.parseLong(minAgeStr);
                break;
            } catch (Exception e) {
                printer.errPrintln("Ошибка при вводе возраста: " + e.getMessage());
            }
        } while(needReask);

        long ticketsCount = 0;
        do {
            printer.print("Введи количество билетов (число > 0):\n>>> ");
            String ticketsCountStr = sc.nextLine();
            if(!needReask)
                printer.println(ticketsCountStr);
            try {
                ticketsCount = Long.parseLong(ticketsCountStr);
                if(ticketsCount <= 0)
                    throw new WrongFieldException("Число должно быть > 0");
                break;
            } catch (Exception e) {
                printer.errPrintln("Ошибка при вводе количества: " + e.getMessage());
            }
        } while(needReask);

        EventType type = null;
        do {
            printer.println("Введи номер типа события, доступные типы:");
            EventType[] values = EventType.values();
            for(int i=0; i<values.length; i++) {
                printer.println(" " + (i+1) + ". " + values[i]);
            }
            printer.print(">>> ");
            String idStr = sc.nextLine();
            if(!needReask)
                printer.println(idStr);
            try {
                int id = Integer.parseInt(idStr);
                type = values[id-1];
                break;
            } catch (Exception e) {
                printer.errPrintln("Ошибка при вводе типа: " + e.getMessage());
            }
        } while(needReask);

        return new Event(evName, ld, minAge, ticketsCount, type);
    }
}
