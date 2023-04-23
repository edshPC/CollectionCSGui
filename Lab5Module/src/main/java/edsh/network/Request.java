package edsh.network;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class Request implements Serializable {
    private String command;
    private String[] args;
    private Object attachment;

    @SuppressWarnings("unchecked")
    public <T> T getAttachment() {
        try {
            return (T)attachment;
        } catch (ClassCastException e) {
            return null;
        }
    }

}
