package com.kreativadezign.grocerylist.mygrocerylist.Model;

/**
 * Created by cyberdog on 4/15/2018.
 */

public class Grocery {
    private String name;
    private String quantity;
    private String dateItemAdded;
    private int cena;
    private int id;

    public Grocery(int id, String name, String quantity, String dateItemAdded, int cena) {
        this.name = name;
        this.quantity = quantity;
        this.dateItemAdded = dateItemAdded;
        this.cena = cena;
        this.id = id;
    }

    public Grocery() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
