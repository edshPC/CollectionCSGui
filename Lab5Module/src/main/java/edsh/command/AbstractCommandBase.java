package edsh.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractCommandBase {

    private final String name;
    private final String description;

    @Override
    public String toString() {
        return " - " + name + " " + description;
    }
}
