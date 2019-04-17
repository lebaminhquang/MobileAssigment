package com.mobile.assigment.data;

public class Activity {
    private int id;
    User user;
    String doing;
    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getDoing() {
        return this.doing;
    }
    public void setDoing(String doing) {
        this.doing = doing;
    }
}
