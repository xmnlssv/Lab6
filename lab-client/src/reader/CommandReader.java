package reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import endpoint.RequestQueue;
import input.InputMode;
import input.InputReader;
import invoker.Invoker;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

import static serializer.ClientJsonSerializer.mapper;

public class CommandReader {
    private static final HashMap<String, Runnable> zeroArgMappings = new HashMap<>();
    private static final HashMap<String, Consumer<String>> oneArgMappings = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputReader inputReader = new InputReader(scanner, InputMode.MANUAL);
    private Invoker invoker;
    private String command = "";

    private String receiveLabWork() {
        try {
            return mapper.writeValueAsString(inputReader.receiveLabWork());
        } catch (JsonProcessingException exception) {
            return null;
        }
    }

    private String receiveAuthor() {
        try {
            return mapper.writeValueAsString(inputReader.receiveAuthor());
        } catch (JsonProcessingException exception) {
            return null;
        }
    }

    public CommandReader() {
        invoker = new Invoker();
        zeroArgMappings.put("help", () -> invoker.help());
        zeroArgMappings.put("info", () -> invoker.info());
        zeroArgMappings.put("show", () -> invoker.show());
        zeroArgMappings.put("sort", () -> invoker.sort());
        zeroArgMappings.put("clear", () -> invoker.clear());
        zeroArgMappings.put("exit", () -> System.exit(0));
        zeroArgMappings.put("min_by_author", () -> invoker.minByAuthor());
        zeroArgMappings.put("remove_last", () -> invoker.removeLast());
        zeroArgMappings.put("print_field_descending_minimal_point", () -> invoker.descendingMinimalPoint());
        zeroArgMappings.put("add", () -> invoker.add(receiveLabWork()));
        zeroArgMappings.put("remove_greater", () -> invoker.add(receiveAuthor()));
        zeroArgMappings.put("remove_any_by_author", () -> invoker.removeByAuthor(receiveAuthor()));

        oneArgMappings.put("update", (arg) -> invoker.update(arg, receiveLabWork()));
        oneArgMappings.put("remove_by_id", (arg) -> invoker.removeById(arg));
        oneArgMappings.put("execute_script", (arg) -> invoker.executeScript(arg));
    }

    public void run() {
        while (true) {
            System.out.println("Enter a command:");
            command = scanner.nextLine();
            String[] split = command.trim().toLowerCase().split(" ");
            if (split.length == 0) {
                continue;
            }
            if (zeroArgMappings.containsKey(split[0])) {
                zeroArgMappings.get(split[0]).run();
            } else if (oneArgMappings.containsKey(split[0])) {
                oneArgMappings.get(split[0]).accept(split[1]);
            } else {
                System.out.println("Unknown command. Please try again.");
            }
        }
    }
}
