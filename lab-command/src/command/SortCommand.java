package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class SortCommand extends NoArgsCommand {
    public SortCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute() {
        getSender().send(SortCommand.class);
    }
}
