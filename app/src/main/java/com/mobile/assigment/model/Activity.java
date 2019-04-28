package com.mobile.assigment.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.model.Interface.ActivityInterface;

public class Activity {
    private String activityID;
    private String contentActivity;
    private String activityTime;

    static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Boards");


    public Activity() {
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getContentActivity() {
        return contentActivity;
    }

    public void setContentActivity(String contentActivity) {
        this.contentActivity = contentActivity;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public static String createActivity(Activity activity,String boardID)
    {
        DatabaseReference activityReference = reference.child(boardID).child("activities");
        String key = activityReference.push().getKey();
        activity.setActivityID(key);
        activityReference.child(key).setValue(activity);
        return key;
    }
    public static void getActivity(String activityID, String boardID, final ActivityInterface activityInterface)
    {
        DatabaseReference activityReference = reference.child(boardID).child("activities");
        activityReference.child(activityID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Activity activity = dataSnapshot.getValue(Activity.class);
                activityInterface.receivedActivity(activity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void updateActivity(Activity activity,String boardID)
    {
        DatabaseReference activityReference = reference.child(boardID).child("activities");
        activityReference.child(activity.activityID).setValue(activity);
    }
    public static void deleteActivity(Activity activity,String boardID)
    {
        DatabaseReference activityReference = reference.child(boardID).child("activities");
        activityReference.child(activity.activityID).removeValue();
    }

}
