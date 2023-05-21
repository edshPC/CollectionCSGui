package edsh.mainclasses;
import java.io.Serializable;
import java.time.ZonedDateTime;

import edsh.helpers.ListHelper;
import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;

import edsh.enums.TicketType;
import edsh.exeptions.WrongFieldException;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ticket implements Comparable<Ticket>, Serializable {
	@Setter
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final long price; //Значение поля должно быть больше 0
    private final String comment; //Поле может быть null
    private final TicketType type; //Поле не может быть null
    private final Event event; //Поле не может быть null

    private static long lastId = 0;
	@Getter
	private static final MainclassFactory<Ticket> factory = new TicketFactory();
    
    public Ticket(String name, Coordinates coordinates, long price, String comment, TicketType type, Event event) throws WrongFieldException {
		updateId();
		creationDate = ZonedDateTime.now();
		
		if(name == null || name.isBlank() || coordinates == null || price <= 0 || type == null || event == null)
			throw new WrongFieldException("Недопустимое значение поля");
			//return;
		
		this.name = name;
		this.coordinates = coordinates;
		this.price = price;
		this.comment = comment;
		this.type = type;
		this.event = event;
		
	}
   
    public Ticket(JSONObject jObj) throws WrongFieldException {
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
			throw new WrongFieldException("Ошибка в получении поля");
		}
    	updateLastId();
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

	public void updateId() {
		id = ++lastId;
	}
	public void updateLastId() {
		lastId = Math.max(lastId, this.id);
	}
    
    @Override
    public String toString() {

		return "Информация о билете #" + id + ":\n" +
			   " - Имя: " + name + "\n" +
			   " - Координаты: " + coordinates + "\n" +
			   " - Дата создания: " + creationDate + "\n" +
			   " - Цена: " + price + "\n" +
			   " - Комментарий: " + comment + "\n" +
			   " - Тип: " + type + "\n" +
			   " - Событие: " + event;
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
		t.id = id;
		ListHelper.getList().set(index, t);
		t.updateLastId();
	}

    

       
}