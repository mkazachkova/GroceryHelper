package com.example.grocery;

/**
 * Created by Kiki on 4/20/17.
 */

public class Receipt {

    public float amount;
    public long date;


    public Receipt() {
        // Empty Constructor
    }

    public Receipt(float amount,long date) { //param for image?
        this.amount = amount;
        this.date =date;
    }

    //public int getID() {return this.id;} //added

    public float getAmount() {
        return this.amount;
    }

    public long getDate() {
        return this.date;
    }


}
