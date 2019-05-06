package com.mobile.assigment.presenter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.assigment.R;
import com.mobile.assigment.model.Card;
import com.mobile.assigment.model.Interface.OnCardLoadedCallback;

import java.util.ArrayList;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {
    private ArrayList<String> mCardNameList;
    private ArrayList<String> mCardIDList;
    private Activity mParentActivity;
    OnCardClickedCallback mCardClickedCallback;
    OnCardLoadedCallback mCardLoadedCallback;

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView mCardName;
        public CardViewHolder(View v) {
            super(v);
            mCardName = v.findViewById(R.id.item_card_name_txt);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mCardClickedCallback.displayCardFragment(mCardNameList.get(position), mCardIDList.get(position));
                    Card.getCard(mCardIDList.get(position), mCardLoadedCallback);
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.mCardName.setText(mCardNameList.get(position));
    }

    public CardsAdapter(OnCardClickedCallback cardClickedCallback, OnCardLoadedCallback cardLoadedCallback) {
        mCardNameList = new ArrayList<>();
        mCardIDList = new ArrayList<>();
        mCardClickedCallback = cardClickedCallback;
        mCardLoadedCallback = cardLoadedCallback;
    }

    public void addCard(String cardName, String cardID) {
        mCardNameList.add(cardName);
        mCardIDList.add(cardID);
        notifyItemChanged(mCardNameList.size() - 1);
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        CardViewHolder vh = new CardViewHolder(v);
        return vh;
    }

    public void setParentActivity(Activity activity) {
        this.mParentActivity = activity;
    }

    @Override
    public int getItemCount() {
        return mCardNameList.size();
    }
}
