package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class RemoveGreaterCommand extends OneArgCommand {
    public RemoveGreaterCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute(String arg) {
        getSender().send(RemoveGreaterCommand.class, arg);
    }
}
