package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;
import exceptions.WrongArityException;

public abstract class NoArgsCommand extends AbstractCommand {
    public NoArgsCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute(String... args) {
        if (args.length != 0) {
            throw new WrongArityException(args.length, 0);
        }
        execute();
    }

    abstract void execute();
}
