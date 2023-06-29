package endpoint;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import utils.LogUtil;
import request.Request;

import java.time.Clock;

public class RequestSender {
    private static Clock clock = Clock.systemDefaultZone();
    private static Logger logger = LogUtil.getLogger("client");

    public static void send(Class<? extends Command> clazz, String... args) {
        try {
            Request request = new Request(clazz, args);
            String json = new ObjectMapper().writeValueAsString(request);
            RequestQueue.REQUEST_QUEUE.put(json, clock.instant());
        } catch (JsonProcessingException exception) {
            String message = exception.getLocalizedMessage();
            logger.error("Could not send command of type {}", clazz, exception);
        }
    }
}
