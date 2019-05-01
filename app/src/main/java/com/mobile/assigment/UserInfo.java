package com.mobile.assigment;

public class UserInfo {
    private String mName;
    private String mEmail;
    private String mId;
    private static UserInfo mInstance;
    private UserInfo() {

    }

    public static UserInfo getInstance() {
        if (mInstance == null) {
            mInstance = new UserInfo();
        }
        return mInstance;
    }

    public void setUserName(String userName) {
        mName = userName;
    }

    public void setUserEmail(String userEmail) {
        mEmail = userEmail;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }
}
