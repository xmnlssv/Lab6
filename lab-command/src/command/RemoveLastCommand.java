package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class RemoveLastCommand extends NoArgsCommand {
    public RemoveLastCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute() {
        getSender().send(RemoveLastCommand.class);
    }
}
