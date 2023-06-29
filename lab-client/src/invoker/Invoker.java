package invoker;

import com.fasterxml.jackson.core.JsonProcessingException;
import command.AbstractCommand;
import command.AddCommand;
import command.ClearCommand;
import command.DescendingMinimalPointCommand;
import command.ExecuteScriptCommand;
import command.HelpCommand;
import command.InfoCommand;
import command.MinByAuthorCommand;
import command.RemoveByAuthorCommand;
import command.RemoveByIdCommand;
import command.RemoveGreaterCommand;
import command.RemoveLastCommand;
import command.ShowCommand;
import command.SortCommand;
import command.UpdateCommand;
import endpoint.RequestSender;

/**
 * Represents invoker.
 */
public class Invoker {
    private RequestSender sender = new RequestSender();

    public void add(String arg) {
        handle(new AddCommand(sender), arg);
    }

    public void clear() {
        handle(new ClearCommand(sender));
    }

    public void descendingMinimalPoint() {
        handle(new DescendingMinimalPointCommand(sender));
    }

    public void executeScript(String arg) {
        handle(new ExecuteScriptCommand(sender), arg);
    }

    public void help() {
        handle(new HelpCommand(sender));
    }

    public void info() {
        handle(new InfoCommand(sender));
    }

    public void minByAuthor() {
        handle(new MinByAuthorCommand(sender));
    }

    public void removeByAuthor(String arg) {
        handle(new RemoveByAuthorCommand(sender), arg);
    }

    public void removeById(String arg) {
        handle(new RemoveByIdCommand(sender), arg);
    }

    public void removeGreater(String arg) {
        handle(new RemoveGreaterCommand(sender), arg);
    }

    public void removeLast() {
        handle(new RemoveLastCommand(sender));
    }

    public void show() {
        handle(new ShowCommand(sender));
    }

    public void sort() {
        handle(new SortCommand(sender));
    }

    public void update(String arg1, String arg2) {
        handle(new UpdateCommand(sender), arg1, arg2);
    }

    private void handle(AbstractCommand command, String... args) {
        command.execute(args);
    }
}
