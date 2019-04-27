package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.TeamInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {
    private String teamID;
    private String teamName;
    private Map<String,String> usersIDs = new HashMap<>();
    private String ownerID;
    private Map<String,String> boardList;

    static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Teams");


    public Team() {
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Map<String, String> getUsersIDs() {
        return usersIDs;
    }

    public void setUsersIDs(Map<String, String> usersIDs) {
        this.usersIDs = usersIDs;
    }

    public Map<String, String> getBoardList() {
        return boardList;
    }

    public void setBoardList(Map<String, String> boardList) {
        this.boardList = boardList;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }


    public static String createTeam(Team team)
    {
        String key = reference.push().getKey();
        team.setTeamID(key);
        reference.child(key).setValue(team);
        return key;
    }

    public static void updateTeam(Team team)
    {
        reference.child(team.teamID).setValue(team);
    }
    public static void deleteTeam(String TID)
    {
        reference.child(TID).removeValue();
    }
    public static void getTeamInfo(String TID, final TeamInterface teamInterface)
    {
        reference.child(TID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Team team = dataSnapshot.getValue(Team.class) ;
                teamInterface.receivedTeam(team);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addMember(String userName)
    {
        final Team thisTeam = this;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.orderByChild("name").equalTo(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    User user = data.getValue(User.class);
                    usersIDs.put(user.getUserId(),"User");
                    User.addToTeam(user,teamID,teamName);
                    Team.updateTeam(thisTeam);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
