package util.exception;

public class BookNotLendException extends Exception{

    public BookNotLendException() {
    }

    public BookNotLendException(String message) {
        super(message);
    }    
}