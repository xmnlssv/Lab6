package exceptions;

public class InvalidFileDataException extends Exception {
    public InvalidFileDataException(String path, String message) {
        super("Invalid file data in " + path + ": " + message + "");
    }
}
