package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.UserInterface;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String userId;
    private String name;
    private String email;
    private Map<String,String> teamIDs = new HashMap<>();
    private Map<String,String> personalBoards;

    static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email  = email;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getTeamIDs() {
        return teamIDs;
    }

    public void setTeamIDs(Map<String, String> teamIDs) {
        this.teamIDs = teamIDs;
    }

    public Map<String, String> getPersonalBoards() {
        return personalBoards;
    }

    public void setPersonalBoards(Map<String, String> personalBoards) {
        this.personalBoards = personalBoards;
    }

    public static void createUser(User user, String UID)
    {
        user.setUserId(UID);
        reference.child(UID).setValue(user) ;
    }

    public static void getUser(String UID, final UserInterface userInterface)
    {
        reference.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class) ;
                userInterface.OnUserReceived(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void deleteUser(String UID)
    {
        reference.child(UID).removeValue();
    }
    public static void updateUserInfo(User user)
    {
        reference.child(user.userId).setValue(user);
    }

    public static void addToTeam(User user,String TeamID,String TeamName)
    {
        user.teamIDs.put(TeamID,TeamName);
        updateUserInfo(user);
    }
}
