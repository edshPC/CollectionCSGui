package edsh.exeptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExitExecutedException extends RuntimeException {

    private final int exitCode;

}
