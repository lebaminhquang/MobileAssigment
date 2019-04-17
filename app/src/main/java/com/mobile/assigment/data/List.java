package com.mobile.assigment.data;

import java.util.ArrayList;

public class List {
    private int id;
    String name;
    ArrayList<Card> cardlist = new ArrayList<Card>();
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Card> getCardList() {
        return this.cardlist;
    }
    public void setCardlist(ArrayList<Card> cardlist) {
        this.cardlist = cardlist;
    }
}
