package util.exception;
        
public class LoanLimitHitException extends Exception{

    public LoanLimitHitException() {
    }

    public LoanLimitHitException(String message) {
        super(message);
    }
    
}

