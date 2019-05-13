package com.mobile.assigment;

import com.mobile.assigment.model.User;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> allUsers;
    private static UserList Instance;

    private UserList() {
    }

    public static UserList getInstance() {
        if (Instance == null) {
            Instance = new UserList();
        }
        return Instance;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }
}
