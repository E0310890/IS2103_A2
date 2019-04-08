package model;

import java.io.Serializable;

public class Fine implements Serializable {
    private Long lendID;
    private double fineAmount;

    public Fine() {
    }
    
    public Fine(Long lendID, double fineAmount) {
        this.lendID = lendID;
        this.fineAmount = fineAmount;
    }

    public Long getLendID() {
        return lendID;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    @Override
    public String toString() {
        return "Fine{" + "lendID=" + lendID + ", fineAmount=" + fineAmount + '}' + "\n";
    }
    
    
    
    
}
