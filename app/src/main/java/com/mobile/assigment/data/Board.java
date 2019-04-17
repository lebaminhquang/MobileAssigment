package com.mobile.assigment.data;

import java.util.ArrayList;

public class Board {
    private int id;
    String name;
    String deadline;
    String label;
    String background;
    boolean visibility;
    boolean commenting;
    ArrayList<Activity> activity = new ArrayList<Activity>();
    ArrayList<List> list = new ArrayList<List>();
    public String getName() {
        return this.name;
    }

    public String getDeadline() {
        return this.deadline;
    }

    public String getLabel() {
        return this.label;
    }

    public String getBackground() {
        return this.background;
    }

    public boolean getVisibility() {
        return this.visibility;
    }

    public boolean getCommenting() {
        return this.commenting;
    }

    public ArrayList<Activity> getActivity() {
        return this.activity;
    }

    public ArrayList<List> getList() {
        return this.list;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setCommenting(boolean commenting) {
        this.commenting = commenting;
    }

    public void setActivity(ArrayList<Activity> activity) {
        this.activity = activity;
    }

    public void setList(ArrayList<List> list) {
        this.list = list;
    }

}
