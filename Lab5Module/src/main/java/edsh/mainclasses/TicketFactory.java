package edsh.mainclasses;

import edsh.enums.TicketType;
import edsh.exeptions.WrongFieldException;
import edsh.helpers.ConsolePrinter;
import edsh.helpers.MyScanner;
import edsh.helpers.Printer;

import java.util.NoSuchElementException;

public class TicketFactory implements MainclassFactory<Ticket> {

    /**
     * Начинает создание нового объекта билета, используя данный сканер
     * @param sc Сканер, который берет информацию
     * @return Новый объект класса {@link Ticket}
     * @throws WrongFieldException Если поля у созданного объекта неверные
     * @throws NoSuchElementException Если ввод полей отменен
     */
    @Override
    public Ticket create(MyScanner sc) throws WrongFieldException, NoSuchElementException {
        Printer printer = new ConsolePrinter();
        boolean needReask = sc.isConsole();
        String ticketName = "";
        do {
            printer.print("Введи имя билета:\n>> ");
            ticketName = sc.nextLine();
            if(!needReask)
                printer.println(ticketName);
            if(ticketName.isBlank()) {
                printer.errPrintln("Имя билета не должно быть пустым");
                continue;
            }
            break;
        } while(needReask);

        Coordinates coords = null;
        do {
            printer.print("Введи координаты места через запятую (x - float <= 542, y - int <= 203, пример: '10.2, 20'):\n>> ");
            String coordsStr = sc.nextLine();
            if(!needReask)
                printer.println(coordsStr);
            String[] coordsSplit = coordsStr.split(", ");
            try {
                float x = Float.parseFloat(coordsSplit[0]);
                int y = Integer.parseInt(coordsSplit[1]);
                coords = new Coordinates(x, y);
                break;
            } catch (NumberFormatException | IndexOutOfBoundsException | WrongFieldException e) {
                printer.errPrintln("Ошибка при вводе координат: " + e.getMessage());
            }
        } while(needReask);

        long price = 0;
        do {
            printer.print("Введи цену билета (число > 0):\n>> ");
            String priceStr = sc.nextLine();
            if(!needReask)
                printer.println(priceStr);
            try {
                price = Long.parseLong(priceStr);
                if(price <= 0) {
                    printer.errPrintln("Цена должна быть > 0");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                printer.errPrintln("Ошибка при вводе цены: " + e.getMessage());
            }
        } while(needReask);

        printer.print("Введи комментарий:\n>> ");
        String comment = sc.nextLine();
        if(!needReask)
            printer.println(comment);

        TicketType type = null;
        do {
            printer.println("Введи номер типа билета, доступные типы:");
            TicketType[] values = TicketType.values();
            for(int i=0; i<values.length; i++) {
                printer.println(" " + (i+1) + ". " + values[i]);
            }
            printer.print(">> ");
            String strId = sc.nextLine();
            if(!needReask)
                printer.println(strId);
            try {
                int id = Integer.parseInt(strId);
                type = values[id-1];
                break;
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                printer.errPrintln("Ошибка при вводе типа: " + e.getMessage());
            }
        } while(needReask);

        Event event = null;
        do {
            try {
                event = Event.getFactory().create(sc);
                break;
            } catch (Exception e) {
                printer.errPrintln("Ошибка при создании события: " + e.getMessage());
            }
        } while(needReask);

        return new Ticket(ticketName, coords, price, comment, type, event);
    }
}
