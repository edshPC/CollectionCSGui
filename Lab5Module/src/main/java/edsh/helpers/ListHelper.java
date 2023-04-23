package edsh.helpers;

import edsh.mainclasses.Ticket;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

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

}
