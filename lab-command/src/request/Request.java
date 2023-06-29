package request;

import command.Command;

public record Request(
        Class<? extends Command> command,
        String[] args
) {

}
