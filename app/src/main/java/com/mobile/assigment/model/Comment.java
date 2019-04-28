package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.CommentInterface;

public class Comment {
    private String commentID;
    private String userID;
    private String contentComment;
    private String commentTime;

    static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Lists");
    public Comment() {
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
//    public static void pushComment(Comment comment,)

    public static String createComment(Comment comment,String listID,String cardID)
    {
        DatabaseReference cardReference = reference
                .child(listID).child("cardList")
                .child(cardID).child("commentList");
        String key = cardReference.push().getKey();
        comment.setCommentID(key);
        cardReference.child(key).setValue(comment);
        return key;
    }
    public static void updateComment(Comment comment,String listID,String cardID)
    {
        DatabaseReference cardReference = reference
                .child(listID).child("cardList")
                .child(cardID).child("commentList");
        cardReference.child(comment.commentID).setValue(comment);
    }
    public static void getComment(String commentID, String listID, String cardID, final CommentInterface commentInterface)
    {
        DatabaseReference cardReference = reference
                .child(listID).child("cardList")
                .child(cardID).child("commentList");
        cardReference.child(commentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                commentInterface.receivedComment(comment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void deleteComment(String commentID,String listID,String cardID)
    {
        DatabaseReference cardReference = reference
                .child(listID).child("cardList")
                .child(cardID).child("commentList");
        cardReference.child(commentID).removeValue();
    }
}
