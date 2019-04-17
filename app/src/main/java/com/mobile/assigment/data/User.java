package com.mobile.assigment.data;

import java.util.ArrayList;

public class User {
    private int id;
    String name;
    String username;
    String password;
    String email;
    ArrayList<Board> personal_board = new ArrayList<Board>();
    public User(int id, String name, String username, String password, String email, ArrayList<Board> personal_board) {
        super();
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.personal_board = personal_board;
    }
    public User(String name, String username, String password, String email) {
        super();
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
