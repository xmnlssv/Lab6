package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class InfoCommand extends NoArgsCommand {
    public InfoCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute() {
        getSender().send(InfoCommand.class);
    }
}
