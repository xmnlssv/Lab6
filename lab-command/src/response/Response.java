package response;

import command.Command;
import request.Request;

public record Response(
        Request request,
        Object response,
        Result result
) {
    public Response(
            Class<? extends Command> command,
            String[] args,
            Object response,
            Result result
    ) {
        this(new Request(command, args), response, result);
    }
}
