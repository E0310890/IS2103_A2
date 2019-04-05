package util.exception;

public class InvalidInputException extends Exception {

    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Invalid input occur.";
    }
    
    
    
}
