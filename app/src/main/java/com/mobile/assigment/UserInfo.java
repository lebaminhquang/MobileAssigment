package com.mobile.assigment;

import com.mobile.assigment.model.Board;
import com.mobile.assigment.model.Card;
import com.mobile.assigment.presenter.CardsAdapter;

public class UserInfo {
    private String mName;
    private String mEmail;
    private String mId;
    private Board mCurrentBoard;
    private static UserInfo mInstance;
    private String mCurrentListID;
    private Card mCurrentCard;
    private CardsAdapter mCurrentCardsAdapter;
    private int mCurrentCardPosition;
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

    public void setCurrentBoard(Board board) {
        mCurrentBoard = board;
    }

    public Board getCurrentBoard() {
        return mCurrentBoard;
    }

    public void setCurrentCard(Card card) {
        mCurrentCard = card;
    }

    public Card getCurrentCard() {
        return mCurrentCard;
    }

    public void setCurrentListID(String id) {
        mCurrentListID = id;
    }

    public String getCurrentListID() {
        return mCurrentListID;
    }

    public CardsAdapter getCurrentCardsAdapter() {
        return mCurrentCardsAdapter;
    }

    public void setCurrentCardsAdapter(CardsAdapter currentCardsAdapter) {
        this.mCurrentCardsAdapter = currentCardsAdapter;
    }

    public void setCurrentCardPosition(int currentCardPosition) {
        this.mCurrentCardPosition = currentCardPosition;
    }

    public int getCurrentCardPosition() {
        return mCurrentCardPosition;
    }
}
