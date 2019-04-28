package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.CardInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Card {
    private String cardID;
    private String name;
    private String description;
    private String labels;
    private String createDate;
    private String dueDate;
    private String color;
    private Map<String,Comment> commentList=new HashMap<>();
    private Map<String,CheckList> checkListList = new HashMap<>();

    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Lists");

    public Card() {
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Map<String, Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(Map<String, Comment> commentList) {
        this.commentList = commentList;
    }

    public Map<String, CheckList> getCheckListList() {
        return checkListList;
    }

    public void setCheckListList(Map<String, CheckList> checkListList) {
        this.checkListList = checkListList;
    }

    public static String createCard(Card card, String listID)
    {
        DatabaseReference cardReference = reference.child(listID).child("cardList");
        String key = cardReference.push().getKey();
        card.setCardID(key);
        cardReference.child(key).setValue(card);
        return key;
    }
    public static void updateCard(Card card,String listID)
    {
        DatabaseReference cardReference = reference.child(listID).child("cardList");
        cardReference.child(card.cardID).setValue(card);
    }
    public static void getCard(String cardID, String listID, final CardInterface cardInterface)
    {
        DatabaseReference cardReference = reference.child(listID).child("cardList");
        reference.child(cardID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Card card = new Card();
                cardInterface.receivedCard(card);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void deleteCard(String cardID,String listID)
    {
        DatabaseReference cardReference = reference.child(listID).child("cardList");
        cardReference.child(cardID).removeValue();
    }
}
