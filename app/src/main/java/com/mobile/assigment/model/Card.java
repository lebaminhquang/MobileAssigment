package com.mobile.assigment.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Card {
    private String cardID;
    private String name;
    private String description;
    private String labels;
    private String createDate;
    private String dueDate;
    private String color;
    private List<Comment> commentList;
    private List<CheckList> checkListList;

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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<CheckList> getCheckListList() {
        return checkListList;
    }

    public void setCheckListList(List<CheckList> checkListList) {
        this.checkListList = checkListList;
    }

    public static void pushCard(Card card,String listID)
    {
        reference.child(listID).child("cardList").push().setValue(card);
    }

    @Override
    public String toString() {
        String cardString = "";
        cardString += "Des : " + this.description+"\n";
        if (!commentList.isEmpty())
        {
            for (Comment comment : commentList)
            {
                cardString += "Comment : " + comment.toString()+"\n";
            }
        }
        if (!checkListList.isEmpty())
        {
            for (CheckList checkList : checkListList)
            {
                cardString += "Check list : "+checkList.toString()+"\n";
            }
        }

        return cardString;
    }
}
