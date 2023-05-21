package edsh.command;

public interface RequireAttachment<T> {
    void setAttachment(T t);
    T getAttachment();
}
