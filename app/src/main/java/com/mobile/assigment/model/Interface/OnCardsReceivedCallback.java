package com.mobile.assigment.model.Interface;

import com.mobile.assigment.model.Card;

import java.util.Map;

public interface OnCardsReceivedCallback {
    public void onCardReceived(int position, Map<String, String> cards);
}
