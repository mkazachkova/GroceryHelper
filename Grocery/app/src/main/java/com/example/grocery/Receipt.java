package com.example.grocery;

/**
 * Created by Kiki on 4/20/17.
 */

public class Receipt {

    public float amount;
    public long date;
    public String uri;

    public Receipt() {
        // Empty Constructor
    }



    public Receipt(float amount, long date, String uri) {
        this.amount = amount;
        this.date = date;
        this.uri = uri;
    }

    //public int getID() {return this.id;} //added

    public  String getUri() {return this.uri; }

    public float getAmount() {
        return this.amount;
    }

    public long getDate() {
        return this.date;
    }


}
