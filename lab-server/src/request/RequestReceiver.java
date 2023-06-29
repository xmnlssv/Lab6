package request;

import ch.qos.logback.classic.Logger;
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
import connection.ConnectionManager;
import response.AddressedResponse;
import utils.LogUtil;
import worker.Worker;
import model.LabWork;
import model.Person;
import response.Response;
import serializer.ServerJsonSerializer;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class RequestReceiver {
    private static final Map<Class<? extends Command>, Supplier<Response>> zeroArgMappings = new HashMap<>();
    private static final Map<Class<? extends Command>, Function<String, Response>> oneArgMappings = new HashMap<>();
    private static Worker worker = Worker.getInstance();
    private static Logger logger = LogUtil.getLogger("server");

    static {
        zeroArgMappings.put(ClearCommand.class, () -> worker.clearAction());
        zeroArgMappings.put(HelpCommand.class, () -> worker.helpAction());
        zeroArgMappings.put(ShowCommand.class, () -> worker.showAction());
        zeroArgMappings.put(SortCommand.class, () -> worker.sortAction());
        zeroArgMappings.put(InfoCommand.class, () -> worker.infoAction());
        zeroArgMappings.put(MinByAuthorCommand.class, () -> worker.minByAuthorAction());
        zeroArgMappings.put(DescendingMinimalPointCommand.class, () -> worker.descendingMinimalPointAction());
        zeroArgMappings.put(RemoveLastCommand.class, () -> worker.removeLastAction());

        oneArgMappings.put(AddCommand.class, (arg) -> worker.addAction((LabWork) ServerJsonSerializer.deserialize(arg, LabWork.class)));
        oneArgMappings.put(RemoveByIdCommand.class, (arg) -> worker.removeByIdAction(Integer.parseInt(arg)));
        oneArgMappings.put(RemoveByAuthorCommand.class, (arg) -> worker.removeByAuthorAction((Person) ServerJsonSerializer.deserialize(arg, Person.class)));
        oneArgMappings.put(RemoveGreaterCommand.class, (arg) -> worker.removeGreaterAction(Integer.parseInt(arg)));
    }

    public static void read(
            ByteBuffer buffer,
            DatagramChannel channel,
            Selector selector,
            SelectionKey key
    ) {
        try {
            SocketAddress address = channel.receive(buffer);
            buffer.flip();
            String payload = StandardCharsets.UTF_8.decode(buffer).toString();
            logger.info("Receiving payload from client: {}", payload);
            String response = generateResponse(payload);
            ConnectionManager.responseQueue.add(new AddressedResponse(response, address));
            key.interestOps(SelectionKey.OP_WRITE);
            selector.wakeup();
        } catch (IOException exception) {
            logger.error("I/O exception while reading selection key", exception);
        }
    }

    public static String generateResponse(String payload) {
        Request request = null;
        try {
            request = new ObjectMapper().readValue(payload, Request.class);
        } catch (JsonProcessingException exception) {
            String message = exception.getLocalizedMessage();
            logger.error("Could not parse payload {}", payload, exception);
        }
        if (request.command() == ExecuteScriptCommand.class) {
            List<Response> responses = new ArrayList<>();
            for (Response response : worker.executeScriptAction(request.args()[0])) {
                responses.add(response);
            }
            Response[] responseArray = new Response[responses.size()];
            responses.toArray(responseArray);
            return ServerJsonSerializer.serialize(responseArray);
        } else if (request.command() == UpdateCommand.class) {
            Response response = worker.updateAction(
                    Integer.parseInt(request.args()[0]),
                    (LabWork) ServerJsonSerializer.deserialize(request.args()[1], LabWork.class)
            );
            return ServerJsonSerializer.serialize(response);
        } else if (zeroArgMappings.containsKey(request.command())) {
            Response response = zeroArgMappings.get(request.command()).get();
            return ServerJsonSerializer.serialize(response);
        } else if (oneArgMappings.containsKey(request.command())) {
            Response response = oneArgMappings.get(request.command()).apply(request.args()[0]);
            return ServerJsonSerializer.serialize(response);
        } else {
            logger.error("Could not parse request command {}", request.command());
            return "";
        }
    }
}
