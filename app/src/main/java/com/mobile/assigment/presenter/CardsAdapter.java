package com.mobile.assigment.presenter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.assigment.R;

import java.util.ArrayList;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {
    private ArrayList<String> mCardNameList = new ArrayList<>();
    private Activity mParentActivity;
    OnCardClickedCallback mCallback;
    public class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView mCardName;
        public CardViewHolder(View v) {
            super(v);
            mCardName = v.findViewById(R.id.item_card_name_txt);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mCallback.displayCardFragment(mCardNameList.get(position));
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.mCardName.setText(mCardNameList.get(position));
    }

    public CardsAdapter(ArrayList<String> cardNameList, OnCardClickedCallback callback) {
        mCardNameList = cardNameList;
        mCallback = callback;
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
