package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class DescendingMinimalPointCommand extends NoArgsCommand {
    public DescendingMinimalPointCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute() {
        getSender().send(DescendingMinimalPointCommand.class);
    }
}
