package util.exception;

public class BookAlreadyLendedException extends Exception{

    public BookAlreadyLendedException() {
    }

    public BookAlreadyLendedException(String message) {
        super(message);
    }
    
}
