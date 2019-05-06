package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.OnCardLoadedCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Card {
    private String cardID;
    private String name;
    private String description;
    private String createDate;
    private String dueDate;
    private String color;
    private ArrayList<String> labelNames;
    private ArrayList<String> labelColors;
    private ArrayList<Boolean> labelChecked;
    private Map<String,Comment> commentList=new HashMap<>();
    private Map<String,CheckList> checkListList = new HashMap<>();

    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Cards");

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

    public void setLabelNames(ArrayList<String> names) {
        this.labelNames = names;
    }

    public void setLabelColors(ArrayList<String> colors) {
        this.labelColors = colors;
    }

    public void setLabelChecked(ArrayList<Boolean> checked) {
        this.labelChecked = checked;
    }

    public ArrayList<String> getLabelNames() {
        return labelNames;
    }

    public ArrayList<Boolean> getLabelChecked() {
        return labelChecked;
    }

    public ArrayList<String> getLabelColors() {
        return labelColors;
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
        String key = reference.push().getKey();
        card.setCardID(key);
        //add card
        reference.child(key).setValue(card);

        //save card to list
        DatabaseReference listReference = FirebaseDatabase.getInstance().getReference().child("Lists");
        listReference.child(listID).child("cardList").child(key).setValue(card.getName());
        return key;
    }
    public static void updateCard(Card card)
    {
        reference.child(card.cardID).setValue(card);
    }

    public static void getCard(String cardID, final OnCardLoadedCallback callback) {
        reference.child(cardID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Card card = dataSnapshot.getValue(Card.class);
                callback.onCardLoaded(card);
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
