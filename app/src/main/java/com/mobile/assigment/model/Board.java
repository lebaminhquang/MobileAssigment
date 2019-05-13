package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.BoardInterface;
import com.mobile.assigment.model.Interface.OnListsInBoardReceived;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private String boardName;
    private String boardID;
    private String background;
    private String label;
    private Map<String,String> listCardList = new HashMap<>();
    private boolean visibility;
    private String deadline;
    private Map<String,Activity> activities = new HashMap<>();
    private ArrayList<String> memberList = new ArrayList<>();

    public enum BoardType {PersonalBoard,TeamBoard};

    static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Boards");

    public Board() {
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, String> getListCardList() {
        return listCardList;
    }

    public void setListCardList(Map<String, String> listCardList) {
        this.listCardList = listCardList;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Map<String, Activity> getActivities() {
        return activities;
    }

    public void setActivities(Map<String, Activity> activities) {
        this.activities = activities;
    }

    public ArrayList<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<String> memberList) {
        this.memberList = memberList;
    }

    public static void createBoard(Board board, String OwnerID, BoardType type)
    {
        String key = reference.push().getKey();
        board.setBoardID(key);
        reference.child(key).setValue(board);
        if (type.equals(BoardType.PersonalBoard))
        {
            DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(OwnerID);
            UserReference.child("personalBoards").child(key).setValue(key);
        } else
        {
            DatabaseReference TeamReference = FirebaseDatabase.getInstance().getReference().child("Teams").child(OwnerID);
            TeamReference.child("boardList").child(key).setValue(key);
        }
    }
    public static void updateBoard(Board board)
    {
        reference.child(board.boardID).setValue(board);
    }
    public static void deleteBoard(String BID, String OwnerID, BoardType type)
    {
        reference.child(BID).removeValue();
        if (type.equals(BoardType.PersonalBoard))
        {
            DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(OwnerID);
            UserReference.child("personalBoards").child(BID).removeValue();
        } else
        {
            DatabaseReference TeamReference = FirebaseDatabase.getInstance().getReference().child("Teams").child(OwnerID);
            TeamReference.child("boardList").child(BID).removeValue();
        }
    }
    public static void getBoard(String BID, final BoardInterface boardInterface)
    {
        reference.child(BID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Board board = dataSnapshot.getValue(Board.class);
                boardInterface.OnBoardReceived(board);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getListCardInBoard(String BID, final OnListsInBoardReceived receiveListInterface) {
        reference.child(BID).child("listCardList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> lists = new HashMap<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    lists.put(ds.getKey(), ds.getValue().toString());
                }
                receiveListInterface.onReceiveLists(lists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
