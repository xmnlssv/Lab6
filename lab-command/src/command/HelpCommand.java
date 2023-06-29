package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class HelpCommand extends NoArgsCommand {
    public HelpCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute() {
        getSender().send(HelpCommand.class);
    }
}
