package edsh.mainclasses;

import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

import edsh.exeptions.WrongFieldException;

import java.io.Serializable;

@Getter
public class Coordinates implements Serializable {
    private final float x; //Максимальное значение поля: 542
    private final Integer y; //Максимальное значение поля: 203, Поле не может быть null
    
    public Coordinates(float x, int y) throws WrongFieldException {
    	if(x > 542 || y > 203)
    		throw new WrongFieldException("Недопустимое значение поля");
    	this.x = x;
    	this.y = y;
    }
    
    public Coordinates(JSONObject jObj) throws WrongFieldException {
    	
    	try {
    		this.x = jObj.getFloat("x");
        	this.y = jObj.getInt("y");
		} catch (JSONException e) {
			throw new WrongFieldException("Ошибка в получении поля");
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