package response;

import ch.qos.logback.classic.Logger;
import command.AddCommand;
import connection.ConnectionManager;
import model.LabWork;
import serializer.ServerJsonSerializer;
import utils.LogUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.charset.StandardCharsets;

public class ResponseSender {
    private static Logger logger = LogUtil.getLogger("server");
    public static void write(DatagramChannel channel, SelectionKey key) {
        if (!ConnectionManager.responseQueue.isEmpty()) {
            AddressedResponse response = ConnectionManager.responseQueue.poll();
            ByteBuffer buffer = ByteBuffer.wrap(response.serializedResponse().getBytes(StandardCharsets.UTF_8));
            try {
                logger.info("Sending payload to client: {}", response.serializedResponse());
                channel.send(buffer, response.socketAddress());
            } catch (IOException exception) {
                logger.error("I/O exception while writing selection key", exception);
            }
        }
        key.interestOps(SelectionKey.OP_READ);
    }
    private static Response normalize(Response response) {
        if (response.request().command() == AddCommand.class) {
            LabWork labWork = (LabWork) ServerJsonSerializer.deserialize(response.request().args()[0], LabWork.class);
            return new Response(
                    response.request().command(),
                    new String[]{ServerJsonSerializer.serialize(labWork.withId(null))},
                    response.response(),
                    response.result()
            );
        } else {
            return response;
        }
    }
}
