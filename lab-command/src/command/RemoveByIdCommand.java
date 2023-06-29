package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class RemoveByIdCommand extends OneArgCommand {
    public RemoveByIdCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute(String arg) {
        getSender().send(RemoveByIdCommand.class, arg);
    }
}
