package command;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.WrongArityException;

public interface Command {
    void execute(String... args) throws WrongArityException;
}
