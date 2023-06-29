package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestSender;

public class ExecuteScriptCommand extends OneArgCommand {
    public ExecuteScriptCommand(RequestSender sender) {
        super(sender);
    }

    @Override
    public void execute(String arg) {
        getSender().send(ExecuteScriptCommand.class, arg);
    }
}
