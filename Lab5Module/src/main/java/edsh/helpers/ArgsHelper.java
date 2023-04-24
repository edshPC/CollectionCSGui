package edsh.helpers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public class ArgsHelper {
    private final String[] args;

    public HashMap<Character, String> parseKeyArguments() {
        HashMap<Character, String> arguments = new HashMap<>();
        for(int i = 0; i < args.length; i++) {
            if(args[i].startsWith("-") && args[i].length() > 1) {
                char arg = args[i].charAt(1);
                String value = "";
                if(i < args.length-1 && !args[i+1].startsWith("-")) {
                    value = args[i+1];
                    i++;
                }
                arguments.put(arg, value);
            }
        }

        return arguments;
    }

    public static ArgsHelper fromSplitedCommand(String[] cmd) {
        if(cmd.length > 1) {
            return new ArgsHelper(Arrays.copyOfRange(cmd, 1, cmd.length));
        }
        return new ArgsHelper(new String[0]);
    }
}
