package edsh.mainclasses;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.json.JSONException;
import org.json.JSONObject;

import edsh.enums.EventType;
import edsh.exeptions.WrongFieldExeption;
import edsh.helpers.MyScanner;

public class Event implements Comparable<Event> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDate date; //Поле может быть null
    private long minAge;
    private long ticketsCount; //Значение поля должно быть больше 0
    private EventType eventType; //Поле не может быть null
    
    private static long lastId = 0;
    
    public Event(String name, LocalDate date, long minAge, long ticketsCount, EventType eventType) throws WrongFieldExeption {
    	id = ++lastId;
    	
    	if(name == null || name.isEmpty() || ticketsCount <= 0 || eventType == null)
			throw new WrongFieldExeption("Недопустимое значение поля");
    	
    	this.name = name;
    	this.date = date;
    	this.minAge = minAge;
    	this.ticketsCount = ticketsCount;
    	this.eventType = eventType;
    	
	}
    
    public Event(JSONObject jObj) throws WrongFieldExeption {
		try {
			this.id = jObj.getLong("id");
	    	this.name = jObj.getString("name");
			this.date = LocalDate.parse(jObj.getString("date"));
			this.minAge = jObj.getLong("minAge");
			this.ticketsCount = jObj.getLong("ticketsCount");
			this.eventType = EventType.valueOf(jObj.getString("eventType"));
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
    	jObj.put("id", id).put("name", name).put("date", date.toString()).put("minAge", minAge)
    		.put("ticketsCount", ticketsCount).put("eventType", eventType.name());
    	return jObj;
    }
    
    @Override
    public String toString() {
    	String out = "Событие #" + id + ":\n" +
    			"   - Имя: " + name + "\n" +
    			"   - Дата проведения: " + date + "\n" +
    			"   - Минимальный возраст: " + minAge + "\n" +
    			"   - Количество билетов: " + ticketsCount + "\n" +
    			"   - Тип: " + eventType;
    	return out;
    }
    
    @Override
	public int compareTo(Event ev) {
		return this.date.compareTo(ev.date);
	}
    
    @Override
    public boolean equals(Object o) {
    	if(o == this)
    		return true;
    	
    	if(!(o instanceof Event))
    		return false;
    	
    	Event ev = (Event)o;
		return name.equals(ev.name) && date.equals(ev.date) && minAge == ev.minAge &&
				ticketsCount == ev.ticketsCount && eventType.equals(ev.eventType);
	}
    
    /**
     * Начинает создание нового объекта объекта, используя данный сканер
     * @param sc Сканер, который берет информацию
     * @return Новый объект класса {@link Event}
     * @throws WrongFieldExeption Если поля у созданного объекта неверные
     * @throws NoSuchElementException Если ввод полей отменен
     */
    public static Event create(MyScanner sc) throws WrongFieldExeption, NoSuchElementException {
    	System.out.println("Введи данные события билета:");
		boolean needReask = sc.isConsole();
		String evName = "";
		do {
			System.out.print("Введи название:\n>>> ");;
			evName = sc.nextLine();
			if(!needReask)
				System.out.println(evName);
			if(evName.isBlank()) {
				System.err.println("Имя события не должно быть пустым");
				continue;
			}
			break;
		} while(needReask);
		
		LocalDate ld = null;
		do {
			System.out.print("Введи дату проведения в формате 'YYYY-MM-DD':\n>>> ");
			String ldStr = sc.nextLine();
			if(!needReask)
				System.out.println(ldStr);
			try {
				ld = LocalDate.parse(ldStr);
				break;
			} catch (Exception e) {
				System.err.println("Ошибка при вводе даты: " + e.getMessage());
			}
		} while(needReask);
		
		long minAge = 0;
		do {
			System.out.print("Введи минимальный возраст (число):\n>>> ");
			String minAgeStr = sc.nextLine();
			if(!needReask)
				System.out.println(minAgeStr);
			try {
				minAge = Long.parseLong(minAgeStr);
				break;
			} catch (Exception e) {
				System.err.println("Ошибка при вводе возраста: " + e.getMessage());
			}
		} while(needReask);
		
		long ticketsCount = 0;
		do {
			System.out.print("Введи количество билетов (число > 0):\n>>> ");
			String ticketsCountStr = sc.nextLine();
			if(!needReask)
				System.out.println(ticketsCountStr);
			try {
				ticketsCount = Long.parseLong(ticketsCountStr);
				if(ticketsCount <= 0)
					throw new WrongFieldExeption("Число должно быть > 0");
				break;
			} catch (Exception e) {
				System.err.println("Ошибка при вводе количества: " + e.getMessage());
			}
		} while(needReask);
		
		EventType type = null;
		do {
			System.out.println("Введи номер типа события, доступные типы:");
			EventType[] values = EventType.values();
			for(int i=0; i<values.length; i++) {
				System.out.println(" " + (i+1) + ". " + values[i]);
			}
			System.out.print(">>> ");
			String idStr = sc.nextLine();
			if(!needReask)
				System.out.println(idStr);
			try {
				int id = Integer.parseInt(idStr);
				type = values[id-1];
				break;
			} catch (Exception e) {
				System.err.println("Ошибка при вводе типа: " + e.getMessage());
			}
		} while(needReask);
		
		return new Event(evName, ld, minAge, ticketsCount, type);
    }
    
}