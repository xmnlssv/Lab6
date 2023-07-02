package server;

import ch.qos.logback.classic.Logger;
import connection.ConnectionManager;
import serializer.CollectionSerializer;
import utils.LogUtil;
import worker.Worker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Scanner;

public class Server {
    public static Worker worker = Worker.getInstance();
    private static Logger logger = LogUtil.getLogger("server");

    private static void save(String collectionFile) {
        logger.info("Saving collection to file...");
        String asString = CollectionSerializer.marshal(worker.getCollection());
        try {
            PrintWriter printWriter = new PrintWriter(collectionFile);
            printWriter.println(asString);
            printWriter.flush();
            printWriter.close();
            logger.info("Successfully saved collection to file!");
            System.out.println("Successfully saved collection to file!");
        } catch (FileNotFoundException exception) {
            System.err.println(String.format("Error saving collection: file %s not found", collectionFile));
            logger.error("Error saving collection: file {} not found.", collectionFile);
        }
    }

    public static void main(String[] args) {
        String collectionFile = System.getenv("PATH999");
        ConnectionManager.initialize();
        Worker.initialize(collectionFile);
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a server command:");
            String command = in.nextLine();
            if (command.equals("save")) {
                save(collectionFile);
            } else if (command.equals("exit")) {
                save(collectionFile);
                logger.info("Server exiting...");
                System.exit(0);
            } else {
                System.out.println("Unknown command. Please type [save] or [exit].");
            }
        }
    }
}
