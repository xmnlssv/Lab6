package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class ClearCommand extends NoArgsCommand {
    public ClearCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute() {
        getSender().send(ClearCommand.class);
    }
}
