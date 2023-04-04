package edsh.mainclasses;

import org.json.JSONException;
import org.json.JSONObject;

import edsh.exeptions.WrongFieldExeption;

public class Coordinates {
    private float x; //Максимальное значение поля: 542
    private Integer y; //Максимальное значение поля: 203, Поле не может быть null
    
    public Coordinates(float x, int y) throws WrongFieldExeption {
    	if(x > 542 || y > 203)
    		throw new WrongFieldExeption("Недопустимое значение поля");
    	this.x = x;
    	this.y = y;
    }
    
    public Coordinates(JSONObject jObj) throws WrongFieldExeption {
    	
    	try {
    		this.x = jObj.getFloat("x");
        	this.y = jObj.getInt("y");
		} catch (JSONException e) {
			throw new WrongFieldExeption("Ошибка в получении поля");
		}
    }
    
    public JSONObject toJsonObject() {
    	JSONObject jObj = new JSONObject();
    	jObj.put("x", x).put("y", y);
    	return jObj;
    }
    
    @Override
    public String toString() {
    	String out = "x: " + x + ", y: " + y;
    	return out;
    }
}