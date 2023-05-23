package edsh.helpers;

import edsh.mainclasses.Ticket;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ListHelper {
    @Setter
    private static Printer printer;

    @Getter
    private static Deque<Ticket> list;

    /**
     * Загружает элементы в коллекцию
     * @param ds Хранитель данных
     * @return Количество загруженных файлов / -1 при ошибке
     */
    public static int load(DataStorage ds) {
        list = ds.readAll();
        if(list != null) return list.size();
        list = new ConcurrentLinkedDeque<>();
        //list = new LinkedList<>();
        return -1;
    }

    /**
     * Сохраняет элементы в файл
     * @param ds Хранитель данных
     * @return Успешно ли сохранение
     */
    public static boolean save(DataStorage ds) {
        return ds.saveAll(list);
    }

    /**
     * Сортирует коллекицию по имени, затем по дате создания
     */
    public static void sortList() {
        if(list instanceof List)
            ((List<Ticket>) list).sort(Ticket::compareTo);
        else {
            ArrayList<Ticket> temp = new ArrayList<>(list);
            temp.sort(Ticket::compareTo);
            list.clear();
            list.addAll(temp);
        }
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
