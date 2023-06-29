package client;

import connection.Connection;
import endpoint.RequestQueue;
import endpoint.ResponseReceiver;
import utils.LogUtil;
import reader.CommandReader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import ch.qos.logback.classic.Logger;

public class Client {
    private static Clock clock = Clock.systemDefaultZone();
    private static Selector selector;
    private static SocketAddress socket;
    private static DatagramChannel channel;
    private static ResponseReceiver responseReceiver = new ResponseReceiver();
    private static Logger logger = LogUtil.getLogger("client");
    private static void listen() throws IOException {
        while (!selector.keys().isEmpty()) {
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            selector.select(200);
            for (Iterator<SelectionKey> iterator = selectedKeys.iterator(); iterator.hasNext(); ) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    read(key);
                } else if (key.isWritable()) {
                    write(key);
                }
                iterator.remove();
            }
        }
    }
    private static void initializeConnection() {
        try {
            selector = Selector.open();
            socket = new InetSocketAddress("127.0.0.1", Connection.PORT);
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.connect(socket);
            channel.register(
                    selector,
                    SelectionKey.OP_WRITE,
                    ByteBuffer.allocate(channel.socket().getReceiveBufferSize())
            );
            logger.info("Initialized connection to server on port {}", Connection.PORT);

            Thread thread = new Thread(() -> {
                try {
                    listen();
                } catch (IOException exception) {
                    logger.error("I/O exception while listening to selector", exception);
                }
            });
            thread.start();
        } catch (IOException exception) {
            logger.error("I/O exception while opening selector", exception);
        }
    }

    private static void read(SelectionKey key) {
        try {
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            channel.receive(buffer.clear());
            String response = StandardCharsets.UTF_8.decode(buffer.flip()).toString();
            logger.info("Receiving payload from server: {}", response);
            responseReceiver.receiveResponse(response);
        } catch (IOException exception) {
            logger.error("Error while reading selector key", exception);
        }
        key.interestOps(SelectionKey.OP_WRITE);
        selector.wakeup();
    }

    private static void write(SelectionKey key) {
        Instant now = clock.instant();
        Optional<String> request = RequestQueue.REQUEST_QUEUE.entrySet().stream().filter((r) -> {
            return (r.getValue().plus(500, ChronoUnit.MILLIS)).isBefore(now);
        }).findAny().map(Map.Entry::getKey);
        if (!request.isEmpty()) {
            try {
                RequestQueue.REQUEST_QUEUE.put(request.get(), clock.instant());
                logger.info("Sending payload to server: {}", request.get());
                channel.send(
                        ByteBuffer.wrap(request.get().getBytes(StandardCharsets.UTF_8)),
                        socket
                );
                key.interestOps(SelectionKey.OP_READ);
            } catch (IOException exception) {
                logger.error("I/O exception while writing selector key", exception);
            }
        }
    }

    public static void main(String[] args) {
        initializeConnection();
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                    Type any command.
                    Type [help] for receiving list of available commands and their descriptions.
                    Type [exit] or click [ctrl + D] at any moment to close the program.
                    """);
        CommandReader commandReader = new CommandReader();
        commandReader.run();
    }
}