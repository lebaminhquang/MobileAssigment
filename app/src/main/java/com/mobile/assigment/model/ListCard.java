package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.ListCardInterface;

import java.util.List;

public class ListCard {
    private String listCardID;
    private String listCardName;
    private List<Card> cardList;

    static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Lists");

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

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }



    public static String createListCard(ListCard listCard)
    {
        String key = reference.push().getKey();
        reference.child(key).setValue(listCard);
        return key;
    }

    public static void updateListCard(ListCard listCard,String listCardID)
    {
        reference.child(listCardID).setValue(listCard);
    }

    public static void getData(String listCardID, final ListCardInterface callback)
    {

        reference.child(listCardID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListCard listCard = dataSnapshot.getValue(ListCard.class);
                callback.receivedListCast(listCard);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void deleteData(String listCardID)
    {
        reference.child(listCardID).removeValue();
    }

    @Override
    public String toString() {
        String cardValue="";
        cardValue += this.listCardName+"\n";
        if (!cardList.isEmpty())
        {
            for (Card card : this.cardList)
            {
                cardValue+=card.toString()+"\n";
            }
        }
        return cardValue;
    }
}