package com.mobile.assigment.data;

import java.util.ArrayList;

public class Card {
    private int id;
    String name;
    String description;
    String label;
    String create_date;
    String due_date;
    String color;
    ArrayList<Item> checklist = new ArrayList<Item>();
    ArrayList<Comment> commentlist = new ArrayList<Comment>();

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLabel() {
        return this.label;
    }

    public String getCreate_date() {
        return this.create_date;
    }

    public String getDue_date() {
        return this.due_date;
    }

    public String  getColor() {
        return this.color;
    }

    public ArrayList<Item> getChecklist() {
        return this.checklist;
    }

    public ArrayList<Comment> getCommentlist() {
        return this.commentlist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void getDescription(String description) {
        this.description = description;
    }

    public void getLabel(String label) {
        this.label = label;
    }

    public void getCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void getDue_date(String due_date) {
        this.due_date = due_date;
    }

    public void getColor(String color) {
        this.color = color;
    }

    public void getChecklist(ArrayList<Item> checklist) {
        this.checklist = checklist;
    }

    public void getCommentlist(ArrayList<Comment> commentlist) {
        this.commentlist = commentlist;
    }
}
