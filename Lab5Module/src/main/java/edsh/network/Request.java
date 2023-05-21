package edsh.network;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.nio.channels.SelectionKey;

@Getter
@Builder
public class Request implements Serializable {
    private String command;
    private String[] args;
    private Serializable attachment;
    @Setter
    private SelectionKey executor;

    @SuppressWarnings("unchecked")
    public <T> T getAttachment() {
        try {
            return (T)attachment;
        } catch (ClassCastException e) {
            return null;
        }
    }

}
