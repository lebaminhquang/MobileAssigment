package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.CheckListInterface;

public class CheckList {
    private String checklistID;
    private String description;
    private boolean check;
    private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Lists");


    public CheckList() {
    }

    public String getChecklistID() {
        return checklistID;
    }

    public void setChecklistID(String checklistID) {
        this.checklistID = checklistID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public static String createCheckList(CheckList checkList,String ListID,String CardID)
    {
        String key = reference.child(ListID).child("cardList").child(CardID).child("checkListList").push().getKey();
        reference.child(ListID).child("cardList").child(CardID).child("checkListList").child(key).setValue(checkList);
        return key;
    }

    public static void updateCheckList(CheckList checkList,String ListID,String CardID,String CheckListID)
    {
        reference.child(ListID).child("cardList").child(CardID)
                .child("checkListList").child(CheckListID).setValue(checkList);
    }

    public static void deleteCheckList(CheckList checkList,String ListID,String CardID,String CheckListID)
    {
        reference.child(ListID).child("cardList").child(CardID)
                .child("checkListList").child(CheckListID).removeValue();
    }

    public static void getCheckList(String ListID, String CardID, String CheckListID, final CheckListInterface checkListInterface)
    {
        reference.child(ListID).child("cardList").child(CardID)
                .child("checkListList").child(CheckListID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        CheckList checkList = dataSnapshot.getValue(CheckList.class);
                        checkListInterface.receivedCheckList(checkList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public String toString() {
        String checkString="";
        checkString+=description;
        return checkString;
    }
}
