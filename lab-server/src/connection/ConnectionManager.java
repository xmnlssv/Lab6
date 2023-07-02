package connection;

import ch.qos.logback.classic.Logger;
import request.RequestReceiver;
import response.AddressedResponse;
import response.ResponseSender;
import utils.LogUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static connection.Connection.PORT;

public class ConnectionManager {
    private static Selector selector;
    private static DatagramChannel channel;
    private static int bufferSize;
    private static RequestReceiver requestReceiver;
    public static Queue<AddressedResponse> responseQueue = new ConcurrentLinkedQueue<>();
    private static Logger logger = LogUtil.getLogger("server");
    public static void initialize() {
        try {
            selector = Selector.open();
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            bufferSize = channel.socket().getReceiveBufferSize();
            channel.register(selector, SelectionKey.OP_READ);
            channel.bind(new InetSocketAddress(Connection.PORT));
            Thread thread = new Thread(ConnectionManager::listen);
            thread.start();
        } catch (IOException exception) {
            logger.error("IO exception on port " + PORT);
        }
    }

    private static void listen() {
        while (!Thread.interrupted() && !channel.socket().isClosed()) {
            try {
                selector.select(200);
                for (Iterator<SelectionKey> iterator = selector.selectedKeys().iterator(); iterator.hasNext(); ) {
                    SelectionKey key = iterator.next();
                    try {
                        if (key.isValid()) {
                            if (key.isReadable()) {
                                RequestReceiver.read(
                                        ByteBuffer.allocate(bufferSize),
                                        channel,
                                        selector,
                                        key
                                );
                            } else if (key.isWritable()) {
                                ResponseSender.write(channel, key);
                            }
                        }
                    } finally {
                        iterator.remove();
                    }
                }
            } catch (IOException exception) {
                logger.error("I/O exception while listening to selector: " + exception);
            }
        }
    }

    public static void close() {
        try {
            if (channel != null) channel.close();
            if (selector != null) selector.close();
        } catch (IOException exception) {
            logger.error("Selector I/O Exception: " + exception);
        } catch (ClosedSelectorException exception) {
            logger.error("Selector already closed: " + exception);
        }
    }
}
