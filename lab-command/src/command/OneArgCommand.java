package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;
import exceptions.WrongArityException;

public abstract class OneArgCommand extends AbstractCommand {
    public OneArgCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            throw new WrongArityException(args.length, 1);
        }
        execute(args[0]);
    }

    abstract void execute(String arg);
}
