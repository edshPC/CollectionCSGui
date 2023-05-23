package edsh.helpers;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.json.JSONArray;
import org.json.JSONException;

import edsh.exeptions.WrongFieldException;
import edsh.mainclasses.Ticket;

public class JsonHelper {
	private String rawJson;
	private JSONArray jsonArr;
	
	public JsonHelper(String rawJson) {
		this.rawJson = rawJson;
	}
	
	public JsonHelper(JSONArray jsonArr) {
		this.jsonArr = jsonArr;
	}	
	
	/**
	 * Парсинг ранее заданной строки в {@link JSONArray}
	 * @return Успешен ли парсинг
	 */
	public boolean parseRawJson() {
		try {
			jsonArr = new JSONArray(rawJson);
		} catch (JSONException e) {
			System.err.println("Ошибка в парсинге. Лист объектов пуст");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Перевод заданного {@link JSONArray} в красивую строку
	 */
	public void stringifyJsonArr() {
		rawJson = jsonArr.toString(4);
	}
	
	public JSONArray getJsonArr() {
		return jsonArr;
	}
	
	/**
	 * Сбор коллекции объектов из заданного {@link JSONArray}
	 * @return Собранная коллекция
	 */
	public Deque<Ticket> toList() {
		//Deque<Ticket> temp = new LinkedList<>();
		Deque<Ticket> temp = new ConcurrentLinkedDeque<>();
		
		for(int i=0; i<jsonArr.length(); i++) {
			
			try {
				temp.add(new Ticket(jsonArr.getJSONObject(i)));
			} catch (WrongFieldException | JSONException e) {
				System.err.println("Пропущен поврежденный билет: " + e.getMessage());
			}
		}
		
		return temp;
	}
	
	public String getRawJson() {
		return rawJson;
	}
}
