package worker;

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
import input.InputMode;
import input.InputReader;
import response.Response;
import response.Result;
import model.LabWork;
import model.Person;
import serializer.CollectionSerializer;
import serializer.ServerJsonSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents receiver.
 */
public class Worker {
    private static String filePath;
    private static ArrayList<LabWork> collection = new ArrayList<>();
    private static final Set<String> CALL_STACK = new LinkedHashSet<>();
    private static HashMap<String, String> commands = new HashMap<>();
    private static final HashMap<String, Supplier<Response>> zeroArgMappings = new HashMap<>();
    private static final HashMap<String, Function<String, Response>> oneArgMappings = new HashMap<>();
    private static Worker INSTANCE = null;

    private Worker() {

    }

    public static void initialize(String filePath) {
        collection = CollectionSerializer.unmarshal(filePath);
        PassportIdHelper.initialize(collection);
        IdHelper.initialize(collection);
    }

    static {
        commands.put("help", "get help on alternative commands");
        commands.put("info", "print all collection items into the string representation");
        commands.put("show", "output to the standard output stream all elements of the collection " +
                "in string representation");
        commands.put("add {element}", "add new item to collection");
        commands.put("update id {element}", "update the value of the collection element " +
                "whose Id matches the given one");
        commands.put("remove_by_id id", "remove an element from the collection by its Id");
        commands.put("clear", "remove all items from collection");
        commands.put("execute_script filename", "read and execute script from given file. " +
                "The same views are found in the script as in the interactive mode");
        commands.put("remove_last", "remove the last item from the collection");
        commands.put("remove_greater {element}", "remove all items from the collection that exceed the specified");
        commands.put("sort", "sort the collection in natural order");
        commands.put("remove_any_by_author author", "remove one item from the collection whose author field value" +
                " is equivalent to a given");
        commands.put("min_by_author", "output any object in the collection whose author field value is the minimum");
        commands.put("print_field_descending_minimal_point", "output the values of the field minimalPoint of all elements" +
                " in descending order");

        zeroArgMappings.put("help", () -> getInstance().helpAction());
        zeroArgMappings.put("info", () -> getInstance().infoAction());
        zeroArgMappings.put("show", () -> getInstance().showAction());
        zeroArgMappings.put("sort", () -> getInstance().sortAction());
        zeroArgMappings.put("clear", () -> getInstance().clearAction());
        zeroArgMappings.put("min_by_author", () -> getInstance().minByAuthorAction());
        zeroArgMappings.put("remove_last", () -> getInstance().removeLastAction());
        zeroArgMappings.put("print_field_descending_minimal_point", () -> getInstance().descendingMinimalPointAction());

        oneArgMappings.put("add", (arg) -> getInstance().addAction((LabWork) ServerJsonSerializer.deserialize(arg, LabWork.class)));
        oneArgMappings.put("remove_greater", (arg) -> getInstance().removeGreaterAction(Integer.parseInt(arg)));
        oneArgMappings.put("remove_any_by_author", (arg) -> getInstance().removeByAuthorAction((Person) ServerJsonSerializer.deserialize(arg, Person.class)));
        oneArgMappings.put("remove_by_id", (arg) -> getInstance().removeByIdAction(Integer.parseInt(arg)));
    }

    public static Worker getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        return new Worker();
    }

    public ArrayList<LabWork> getCollection() {
        return collection;
    }

    public Response addAction(LabWork labWork) {
        labWork.setId(IdHelper.generateId());
        collection.add(labWork);
        IdHelper.saveId(labWork.getId());
        return new Response(
                AddCommand.class,
                new String[]{ServerJsonSerializer.serialize(labWork)},
                null,
                Result.SUCCESS
        );
    }

    public Response clearAction() {
        collection.clear();
        IdHelper.removeAll();
        return new Response(
                ClearCommand.class,
                new String[]{},
                null,
                Result.SUCCESS
        );
    }

    public Response descendingMinimalPointAction() {
        StringBuilder stringBuilder = new StringBuilder();
        collection.stream().sorted(Comparator.comparing(LabWork::getMinimalPoint).reversed()).forEach((point) -> {
            stringBuilder.append(point).append("\n");
        });

        return new Response(
                DescendingMinimalPointCommand.class,
                new String[]{},
                stringBuilder.toString(),
                Result.SUCCESS
        );
    }

    public Response[] executeScriptAction(String scriptPath) {
        if (!CALL_STACK.contains(scriptPath)) {
            CALL_STACK.add(scriptPath);
            InputReader inputReader;
            try {
                Scanner scanner = new Scanner(new FileInputStream(scriptPath));
                inputReader = new InputReader(scanner, InputMode.AUTO);
                List<Response> responses = new ArrayList<>();
                responses.add(new Response(
                        ExecuteScriptCommand.class,
                        new String[]{scriptPath},
                        null,
                        Result.SUCCESS
                ));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] split = line.split(" ");
                    if (split.length == 0) {
                        continue;
                    }
                    if (zeroArgMappings.containsKey(split[0])) {
                        responses.add(zeroArgMappings.get(split[0]).get());
                    } else if (oneArgMappings.containsKey(split[0])) {
                        responses.add(oneArgMappings.get(split[0]).apply(split[1]));
                    }
                }
                Response[] responseArray = new Response[responses.size()];
                responses.toArray(responseArray);
                CALL_STACK.remove(scriptPath);
                return responseArray;
            } catch (FileNotFoundException exception) {
                return new Response[]{new Response(
                        ExecuteScriptCommand.class,
                        new String[]{scriptPath},
                        null,
                        Result.FAILURE
                )};
            }
        } else {
            return new Response[]{new Response(
                    ExecuteScriptCommand.class,
                    new String[]{scriptPath},
                    null,
                    Result.FAILURE
            )};
        }
    }

    public Response helpAction() {
        StringBuilder tutorial = new StringBuilder();
        for (Map.Entry<String, String> entry : commands.entrySet()) {
            String key = String.format("%-43s", entry.getKey());
            String value = String.format(" : %1$s", entry.getValue());
            tutorial.append(key).append(value).append("\n");
        }
        return new Response(
                HelpCommand.class,
                new String[]{},
                tutorial.toString(),
                Result.SUCCESS
        );
    }

    public Response infoAction() {
        StringBuilder info = new StringBuilder();
        info
                .append("Type of collection: ")
                .append(collection.getClass())
                .append("\n")
                .append("Date of initialization: ")
                .append(ZonedDateTime.now())
                .append("\n")
                .append("Amount of elements: ")
                .append(collection.size())
                .append("\n");
        return new Response(
                InfoCommand.class,
                new String[]{},
                info.toString(),
                Result.SUCCESS
        );
    }

    public Response minByAuthorAction() {
        if (collection.isEmpty()) {
            return new Response(
                    MinByAuthorCommand.class,
                    new String[]{},
                    null,
                    Result.FAILURE
            );
        }
        LabWork minLabWork = collection.stream().sorted().iterator().next();
        return new Response(
                MinByAuthorCommand.class,
                new String[]{},
                ServerJsonSerializer.serialize(minLabWork),
                Result.SUCCESS
        );
    }

    public Response removeByAuthorAction(Person author) {
        Optional<LabWork> forDeletion = collection.stream().filter(labWork -> labWork.getAuthor().equals(author)).findAny();
        if (forDeletion.isEmpty()) {
            return new Response(
                    RemoveByAuthorCommand.class,
                    new String[]{ServerJsonSerializer.serialize(forDeletion.get())},
                    null,
                    Result.FAILURE
            );
        } else {
            return new Response(
                    RemoveByAuthorCommand.class,
                    new String[]{ServerJsonSerializer.serialize(author)},
                    ServerJsonSerializer.serialize(forDeletion.get()),
                    Result.SUCCESS
            );
        }
    }

    public Response removeByIdAction(long id) {
        Optional<LabWork> forDeletion = collection.stream().filter(labWork -> labWork.getId() == id).findAny();
        if (forDeletion.isEmpty()) {
            return new Response(
                    RemoveByIdCommand.class,
                    new String[]{id + ""},
                    null,
                    Result.FAILURE
            );
        } else {
            return new Response(
                    RemoveByIdCommand.class,
                    new String[]{id + ""},
                    ServerJsonSerializer.serialize(forDeletion.get()),
                    Result.SUCCESS
            );
        }
    }

    public Response removeGreaterAction(long id) {
        List<LabWork> forDeletion = collection.stream().filter(labWork -> labWork.getId() > id).toList();
        collection.removeAll(forDeletion);
        return new Response(
                RemoveGreaterCommand.class,
                new String[]{id + ""},
                null,
                Result.SUCCESS
        );
    }

    public Response removeLastAction() {
        Integer id = (Integer) collection.get(collection.size() - 1).getId();
        IdHelper.removeId(id);
        collection.remove(collection.size() - 1);
        return new Response(
                RemoveLastCommand.class,
                new String[]{},
                null,
                Result.SUCCESS
        );
    }

    public Response showAction() {
        StringBuilder info = new StringBuilder();
        collection.stream().forEach((labWork) -> {
            info.append(labWork.toString()).append("\n");
        });
        return new Response(
                ShowCommand.class,
                new String[]{},
                info.toString(),
                Result.SUCCESS
        );
    }

    public Response sortAction() {
        Collections.sort(collection);
        return new Response(
                SortCommand.class,
                new String[]{},
                null,
                Result.SUCCESS
        );
    }

    public Response updateAction(long id, LabWork updatedLabWork) {
        Optional<LabWork> forUpdate = collection.stream().filter(labWork -> labWork.getId() == id).findAny();
        if (forUpdate.isEmpty()) {
            return new Response(
                    UpdateCommand.class,
                    new String[]{id + "", ServerJsonSerializer.serialize(updatedLabWork)},
                    null,
                    Result.FAILURE
            );
        }
        collection.remove(forUpdate.get());
        collection.add(updatedLabWork);
        return new Response(
                UpdateCommand.class,
                new String[]{id + "", ServerJsonSerializer.serialize(updatedLabWork)},
                null,
                Result.SUCCESS
        );
    }

}
