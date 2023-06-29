package command;

import endpoint.RequestSender;

public abstract class AbstractCommand implements Command {
    private RequestSender sender;
    public AbstractCommand(RequestSender sender) {
        this.sender = sender;
    }

    public RequestSender getSender() {
        return sender;
    }
}
