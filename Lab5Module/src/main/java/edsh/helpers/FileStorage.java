package edsh.helpers;

import edsh.mainclasses.Ticket;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;

import java.nio.file.attribute.FileTime;
import java.util.LinkedList;

@RequiredArgsConstructor
public class FileStorage implements DataStorage {

    private final FileHelper fh;

    @Override
    public LinkedList<Ticket> readAll() {
        if(fh.readFile()) {
            JsonHelper jHelper = new JsonHelper(fh.getRawJson());
            if(jHelper.parseRawJson()) {
                return jHelper.toLinkedList();
            }
        }
        return null;
    }

    @Override
    public boolean saveAll(LinkedList<Ticket> list) {
        JSONArray arr = new JSONArray();
        list.forEach(ticket -> arr.put(ticket.toJsonObject()));
        JsonHelper jh = new JsonHelper(arr);
        jh.stringifyJsonArr();
        fh.setRawJson(jh.getRawJson());
        return fh.writeToFile();
    }

    @Override
    public String getCreationTime() {
        FileTime fileTime = fh.getCreationTime();
        if(fileTime != null) {
            String[] times = fileTime.toString().split("[T.]");
            return times[0] + " " + times[1];
        }
        return "[не сохранена]";
    }
}
