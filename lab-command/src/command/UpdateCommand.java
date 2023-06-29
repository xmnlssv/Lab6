package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class UpdateCommand extends TwoArgsCommand {

    public UpdateCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute(String arg1, String arg2) {
        getSender().send(UpdateCommand.class, arg1, arg2);
    }
}
