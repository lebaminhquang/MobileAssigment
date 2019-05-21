package com.mobile.assigment.model.Interface;

import com.mobile.assigment.model.Card;
import com.mobile.assigment.presenter.ListCardAdapter;

public interface OnCardLoadedForListCallback {
    void onCardLoadedForList(ListCardAdapter.ListCardViewHolder holder, Card card);
}
