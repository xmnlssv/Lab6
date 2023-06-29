package exceptions;

public class WrongArityException extends RuntimeException {
    public WrongArityException(int actual, int expected) {
        super("Wrong arity: expected args: " + expected + ", actual: " + actual);
    }
}
