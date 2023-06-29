package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class RemoveByAuthorCommand extends OneArgCommand {
    public RemoveByAuthorCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute(String arg) {
        getSender().send(RemoveByAuthorCommand.class, arg);
    }
}
