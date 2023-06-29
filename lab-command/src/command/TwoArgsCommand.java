package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;
import exceptions.WrongArityException;

public abstract class TwoArgsCommand extends AbstractCommand {
    public TwoArgsCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            throw new WrongArityException(args.length, 2);
        }
        execute(args[0], args[1]);
    }

    abstract void execute(String arg1, String arg2);
}
