package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class AddCommand extends OneArgCommand {
    public AddCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute(String arg) {
        getSender().send(AddCommand.class, arg);
    }
}
