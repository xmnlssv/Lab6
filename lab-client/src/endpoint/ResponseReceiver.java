package endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.AddCommand;
import command.ClearCommand;
import command.Command;
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
import response.Response;
import response.Result;

import java.util.HashMap;

public class ResponseReceiver {
    private static HashMap<Class<? extends Command>, String> readableNames = new HashMap<>();

    static {
        readableNames.put(AddCommand.class, "add");
        readableNames.put(ClearCommand.class, "clear");
        readableNames.put(DescendingMinimalPointCommand.class, "descending minimal point");
        readableNames.put(ExecuteScriptCommand.class, "execute script");
        readableNames.put(HelpCommand.class, "help");
        readableNames.put(InfoCommand.class, "info");
        readableNames.put(MinByAuthorCommand.class, "min by author");
        readableNames.put(RemoveByAuthorCommand.class, "remove by author");
        readableNames.put(RemoveByIdCommand.class, "remove by id");
        readableNames.put(RemoveGreaterCommand.class, "remove greater");
        readableNames.put(RemoveLastCommand.class, "remove last");
        readableNames.put(ShowCommand.class, "show");
        readableNames.put(SortCommand.class, "sort");
        readableNames.put(UpdateCommand.class, "update");
    }

    public void receiveResponse(String json) throws JsonProcessingException {
        try {
            Response response = new ObjectMapper().readValue(json, Response.class);
            handle(response, 0);
        } catch (Exception exception) {
            Response[] responses = new ObjectMapper().readValue(json, Response[].class);
            for (int i = 0; i < responses.length; i++) {
                handle(responses[i], i);
            }
        }
    }

    private void handle(Response response, int index) throws JsonProcessingException {
        String request = new ObjectMapper().writeValueAsString(response.request());
        if (RequestQueue.REQUEST_QUEUE.containsKey(request) && index == 0) {
            RequestQueue.REQUEST_QUEUE.remove(request);
        }
        String readableName = readableNames.getOrDefault(response.request().command(), "unknown");
        if (response.result() == Result.SUCCESS) {
            System.out.println("Command [" + readableName + "] executed successfully!");
            if (response.response() != null) {
                System.out.println("Response:");
                System.out.println(response.response());
            }
        } else {
            System.out.println("Command [" + readableName + "] executed unsuccessfully.");
        }
    }
}
