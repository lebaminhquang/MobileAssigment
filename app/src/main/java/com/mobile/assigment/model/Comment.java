package com.mobile.assigment.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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




    @Override
    public String toString() {
        String commentString = "";
        commentString += userID + "\n";
        commentString += contentComment + "\n";
        return commentString;
    }
}
