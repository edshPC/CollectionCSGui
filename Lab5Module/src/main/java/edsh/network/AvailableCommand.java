package edsh.network;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class AvailableCommand implements Serializable {
    public enum ArgType {
        STRING,
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE
    }
    public enum AttachedObject {
        NONE,
        TICKET,
        EVENT
    }

    private final String name;
    private final String description;
    private final ArgType[] args;
    private final AttachedObject attachment;

    public AvailableCommand(String name, String description, AttachedObject attachment, ArgType... args) {
        this.name = name;
        this.description = description;
        this.args = args;
        this.attachment = attachment;
    }

}
