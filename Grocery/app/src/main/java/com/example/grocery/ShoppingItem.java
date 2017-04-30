package com.example.grocery;

/**
 * Created by mariyakazachkova on 4/17/17.
 */

public class ShoppingItem implements Comparable<ShoppingItem>{
    public String name;
    public int quantity;
    public int reminderDays;
    public String ID;
    public boolean inList;

    public ShoppingItem(String name, int quantity, int reminderDays , String ID, boolean inList){
        this.name = name;
        this.quantity = quantity;
        this.reminderDays = reminderDays;
        this.ID = ID;
        this.inList = inList;
    }

    public String getName() {
        return  this.name;
    }

    public int getDays() {
        return this.reminderDays;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getID() {
        return this.ID;
    }

    public boolean inList() {
        System.out.println(this.inList);
        return this.inList;
    }

    public int compareTo(ShoppingItem t) {
        String name1 = this.getName().toLowerCase();
        String name2 = t.getName().toLowerCase();
        return name1.compareTo(name2);
    }

}
