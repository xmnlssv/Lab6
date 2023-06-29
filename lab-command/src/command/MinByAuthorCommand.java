package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class MinByAuthorCommand extends NoArgsCommand {
    public MinByAuthorCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute() {
        getSender().send(MinByAuthorCommand.class);
    }
}
