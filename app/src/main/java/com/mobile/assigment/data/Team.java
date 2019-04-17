package com.mobile.assigment.data;

import java.util.ArrayList;

public class Team {
    private int id;
    String name;
    ArrayList<User> member = new ArrayList<User>();
    User owner;
    ArrayList<Board> boardlist = new ArrayList<Board>();
    String description;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<User> getmember() {
        return this.member;
    }
    public void setmember(ArrayList<User> member) {
        this.member = member;
    }
    public User getOwner() {
        return this.owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public ArrayList<Board> getBoardlist() {
        return this.boardlist;
    }
    public void setBoardlist(ArrayList<Board> boardlist) {
        this.boardlist = boardlist;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription() {
        this.description = description;
    }
}
