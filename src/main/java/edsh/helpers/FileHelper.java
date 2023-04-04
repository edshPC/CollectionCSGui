package edsh.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Scanner;

public class FileHelper {
	private String filename;
	private String rawJson;
	private Path filePath;
	
	/**
	 * Создает объект помощника, работающего с определенным файлом
	 * @param filename Имя файла в одной папке с программой / путь до этого файла
	 */
	public FileHelper(String filename) {
		this.filename = filename;
		this.filePath = Paths.get(filename);
	}
	
	/**
	 * Создание файла по заданному названию
	 * @return Был ли создан файл
	 */
	public boolean createFile() {
		File file = new File(filename);
		try {
			Files.createDirectories(filePath.getParent());
			return file.createNewFile();
		} catch (Exception e) {
			System.err.println("Ошибка в создании файла");
		}
		return false;
	}
	
	/**
	 * Чтение данных из заданного файла
	 * @return Успешно ли чтение
	 */
	public boolean readFile() {
		String source = "";
		try (Scanner sc = new Scanner(filePath)) {
			while (sc.hasNextLine()) {
				source += sc.nextLine();
			}
		} catch (IOException e) {
			System.err.println("Файл не найден. Лист объектов пуст");
			return false;
		}
	
		this.rawJson = source;
		return true;
	}
	
	public String getRawJson() {
		return rawJson;
	}
	
	public void setRawJson(String rawJson) {
		this.rawJson = rawJson;
	}
	
	/**
	 * Запись в заданный файл json-строки
	 * @param rawJson json-строка для записи
	 * @return Успешна ли запись
	 */
	public boolean writeToFile(String rawJson) {
		try (FileWriter fw = new FileWriter(filename)) {
			fw.write(rawJson);
		} catch (FileNotFoundException e) {
			System.out.println("Файла не существует. Создание...");
			if(createFile())
				writeToFile(rawJson);
			else {
				System.err.println("Ошибка в создании файла. Попробуйте изменить права доступа");
				return false;
			}
		} catch (IOException e) {
			System.err.println("Ошибка в записи файла. Попробуйте изменить права доступа");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Запись в заданный файл заданной ранее json-строки
	 * @return Успешна ли запись
	 */
	public boolean writeToFile() {
		return writeToFile(rawJson);
	}
	
	/**
	 * Получает время создания файла, с которым работает в формате {@link FileTime}
	 * @return Время создания файла / the epoch time (1970-01-01), если файл не создан
	 */
	public FileTime getCreationTime() {
		try {
			return (FileTime) Files.getAttribute(Paths.get(filename), "creationTime");
		} catch (IOException e) {
			return FileTime.fromMillis(0);
		}
	}
	
}
