package com.mobile.assigment.model.Interface;

import com.mobile.assigment.model.Card;

import java.util.Map;

public interface OnCardsInListReceivedCallback {
    public void onCardsInListReceived(int position, Map<String, String> cards);
}
