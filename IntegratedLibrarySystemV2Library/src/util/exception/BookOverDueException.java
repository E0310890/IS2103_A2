package util.exception;

public class BookOverDueException extends Exception {

    public BookOverDueException() {
    }

    public BookOverDueException(String message) {
        super(message);
    }   
}