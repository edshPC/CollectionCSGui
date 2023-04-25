package edsh.helpers;

import edsh.mainclasses.Ticket;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONArray;

import java.util.LinkedList;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ListHelper {

    @Getter
    private static LinkedList<Ticket> list;

    /**
     * Загружает элементы в коллекцию
     * @param fileHelper Из какого файла загружать
     * @return Количество загруженных файлов / -1 при ошибке
     */
    public static int load(FileHelper fileHelper) {
        if(fileHelper.readFile()) {
            JsonHelper jHelper = new JsonHelper(fileHelper.getRawJson());
            if(jHelper.parseRawJson()) {
                list = jHelper.toLinkedList();
                return list.size();
            }
        }
        list = new LinkedList<>();
        return -1;
    }

    /**
     * Сохраняет элементы в файл
     * @param fh Помошник с нужным файлом
     * @return Успешно ли сохранение
     */
    public static boolean save(FileHelper fh) {
        JSONArray arr = new JSONArray();
        list.forEach(ticket -> arr.put(ticket.toJsonObject()));
        JsonHelper jh = new JsonHelper(arr);
        jh.stringifyJsonArr();
        fh.setRawJson(jh.getRawJson());
        return fh.writeToFile();
    }

    /**
     * Сортирует коллекицию по имени, затем по дате создания
     */
    public static void sortList() {
        list.sort(Ticket::compareTo);
    }

    /**
     * Возвращает индекс элемента в коллекции по его id
     * @param id id
     * @return Индекс / -1 если объект не найден
     */
    public static int getIndexById(long id) {
        int index = 0;
        for(Ticket check : list) {
            if(check.getId() == id)
                return index;
            index++;
        }
        return -1;
    }

    public static Ticket getById(long id) {
        for(Ticket t : list) {
            if(t.getId() == id) return t;
        }
        return null;
    }

}
