package edsh.mainclasses;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

import edsh.helpers.ConsolePrinter;
import edsh.helpers.ListHelper;
import edsh.helpers.Printer;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

import edsh.enums.TicketType;
import edsh.exeptions.WrongFieldExeption;
import edsh.helpers.MyScanner;

@Getter
public class Ticket implements Comparable<Ticket> {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final long price; //Значение поля должно быть больше 0
    private final String comment; //Поле может быть null
    private final TicketType type; //Поле не может быть null
    private final Event event; //Поле не может быть null

    private static long lastId = 0;
    
    public Ticket(String name, Coordinates coordinates, long price, String comment, TicketType type, Event event) throws WrongFieldExeption {
		id = ++lastId;
		creationDate = ZonedDateTime.now();
		
		if(name == null || name.isBlank() || coordinates == null || price <= 0 || type == null || event == null)
			throw new WrongFieldExeption("Недопустимое значение поля");
			//return;
		
		this.name = name;
		this.coordinates = coordinates;
		this.price = price;
		this.comment = comment;
		this.type = type;
		this.event = event;
		
	}
   
    public Ticket(JSONObject jObj) throws WrongFieldExeption {
    	try {
    		this.id = jObj.getLong("id");
        	this.name = jObj.getString("name");
    		this.coordinates = new Coordinates(jObj.getJSONObject("coordinates"));
    		this.creationDate = ZonedDateTime.parse(jObj.getString("creationDate"));
    		this.price = jObj.getLong("price");
    		this.comment = jObj.getString("comment");
    		this.type = TicketType.valueOf(jObj.getString("type"));
    		this.event = new Event(jObj.getJSONObject("event"));
		} catch (JSONException e) {
			throw new WrongFieldExeption("Ошибка в получении поля");
		}
    	lastId = Math.max(lastId, this.id);
    }
    
    /**
     * Метод собирает {@link JSONObject} по своему объекту
     * @return Все поля объекта в виде {@link JSONObject}
     */
    public JSONObject toJsonObject() {
    	JSONObject jObj = new JSONObject();
    	jObj.put("id", id).put("name", name).put("coordinates", coordinates.toJsonObject()).put("creationDate", creationDate.toString())
    		.put("price", price).put("comment", comment).put("type", type.name()).put("event", event.toJsonObject());
    	return jObj;
    }
    
    @Override
    public String toString() {
    	String out = "Информация о билете #" + id + ":\n" +
    			" - Имя: " + name + "\n" +
    			" - Координаты: " + coordinates + "\n" +
    			" - Дата создания: " + creationDate + "\n" +
    			" - Цена: " + price + "\n" +
    			" - Комментарий: " + comment + "\n" +
    			" - Тип: " + type + "\n" +
    			" - Событие: " + event;
    	
    	return out;
    }
    
    @Override
	public int compareTo(Ticket t) {
    	int diff = this.event.compareTo(t.event);
    	if(diff != 0)
    		return diff;
    	
    	diff = this.name.compareTo(t.name);
    	if(diff != 0)
    		return diff;
    	diff = this.creationDate.compareTo(t.creationDate);
		
		return diff;
	}
    
    /**
     * Метод заменяет заданный объект в коллекции, по заданному индексу, устанавливая ему заданный id
     * @param index Индекс, куда поместить
     * @param id id, которое нужно присвоить
     * @param t Билет, который нужно встроить
     */
    public static void putWithId(int index, long id, Ticket t) {
		if (lastId == t.id)
			lastId--;
		t.id = id;
		ListHelper.getList().set(index, t);
		lastId = Math.max(lastId, id);
	}

    
    /**
     * Начинает создание нового объекта билета, используя данный сканер
     * @param sc Сканер, который берет информацию
     * @return Новый объект класса {@link Ticket}
     * @throws WrongFieldExeption Если поля у созданного объекта неверные
     * @throws NoSuchElementException Если ввод полей отменен
     */
    public static Ticket create(MyScanner sc) throws WrongFieldExeption, NoSuchElementException {
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
			} catch (NumberFormatException | IndexOutOfBoundsException | WrongFieldExeption e) {
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
				event = Event.create(sc);
				break;
			} catch (Exception e) {
				printer.errPrintln("Ошибка при создании события: " + e.getMessage());
			}
		} while(needReask);
		
    	return new Ticket(ticketName, coords, price, comment, type, event);
    }
       
}