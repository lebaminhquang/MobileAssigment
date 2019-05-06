package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.OnCardsInListReceivedCallback;

import java.util.HashMap;
import java.util.Map;

public class ListCard {
    private String listCardID;
    private String listCardName;
    private Map<String,Card> cardList = new HashMap<>();

    static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Lists");
    static DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        public ListCard() {
    }

    public String getListCardID() {
        return listCardID;
    }

    public void setListCardID(String listCardID) {
        this.listCardID = listCardID;
    }

    public String getListCardName() {
        return listCardName;
    }

    public void setListCardName(String listCardName) {
        this.listCardName = listCardName;
    }

    public Map<String, Card> getCardList() {
        return cardList;
    }

    public void setCardList(Map<String, Card> cardList) {
        this.cardList = cardList;
    }

    public static String createListCard(ListCard listCard, String boardID)
    {
        String key = reference.push().getKey();
        listCard.setListCardID(key);
        reference.child(key).setValue(listCard);

        root.child("Boards").child(boardID).child("listCardList").child(key).setValue(listCard.listCardName);
        return key;
    }

    public static void updateListCard(ListCard listCard)
    {
        reference.child(listCard.listCardID).setValue(listCard);
    }

//    public static void getListCard(String listCardID, final OnListsInBoardReceived callback)
//    {
//
//        reference.child(listCardID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ListCard listCard = dataSnapshot.getValue(ListCard.class);
//                callback.onReceiveLists(listCard);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public static void deleteListCard(String listCardID)
    {
        reference.child(listCardID).removeValue();
    }

    public static void getAllCardsInList(String listID, final int position, final OnCardsInListReceivedCallback callback) {
        reference.child(listID).child("cardList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> cards = new HashMap<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    cards.put(ds.getKey(), ds.getValue().toString());
                }
                callback.onCardsInListReceived(position, cards);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}