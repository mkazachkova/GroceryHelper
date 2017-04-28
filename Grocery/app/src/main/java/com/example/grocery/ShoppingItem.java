package com.example.grocery;

/**
 * Created by mariyakazachkova on 4/17/17.
 */

public class ShoppingItem {
    public String name;
    public int quantity;
    public int reminderDays;

    public ShoppingItem(String name, int quantity, int reminderDays) {
        this.name = name;
        this.quantity = quantity;
        this.reminderDays = reminderDays;
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

}
