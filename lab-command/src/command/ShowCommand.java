package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class ShowCommand extends NoArgsCommand {
    public ShowCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute() {
        getSender().send(ShowCommand.class);
    }
}
