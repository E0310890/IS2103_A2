package model;

import java.io.Serializable;

public class Fine implements Serializable {
    private Integer amount;
    private Long lendID;

    public Fine() {
    }
    
    public Fine(Long lendID, Integer amount) {
        this.amount = amount;
        this.lendID = lendID;
    }

    
    
    public double getamount() {
        return amount;
    }
    
    
}
