package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.BoardInterface;

import java.util.List;

public class Board {
    private String boardName;
    private String boardID;
    private String background;
    private String label;
    private List<ListCard> listCardList;
    private boolean visibility;
    private String deadline;
    private List<Activity> activity;

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

    public List<ListCard> getListCardList() {
        return listCardList;
    }

    public void setListCardList(List<ListCard> listCardList) {
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

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public static void createBoard(Board board,String OwnerID,BoardType type)
    {
        String key = reference.push().getKey();
        board.setBoardID(key);
        reference.child(key).setValue(board);
        if (type.equals(BoardType.PersonalBoard))
        {
            DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(OwnerID);
            UserReference.child("personalBoards").child(key).setValue("board");
        } else
        {
            DatabaseReference TeamReference = FirebaseDatabase.getInstance().getReference().child("Teams").child(OwnerID);
            TeamReference.child("boardList").child(key).setValue("board");
        }
    }
    public static void updateBoard(Board board)
    {
        reference.child(board.boardID).setValue(board);
    }
    public static void deleteBoard(String BID,String OwnerID, BoardType type)
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
                boardInterface.receivedBoard(board);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
