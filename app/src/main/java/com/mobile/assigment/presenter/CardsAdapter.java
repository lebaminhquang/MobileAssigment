package com.mobile.assigment.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.assigment.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {
    private ArrayList<String> mCardNameList = new ArrayList<>();

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView mCardName;
        public CardViewHolder(View v) {
            super(v);
            mCardName = v.findViewById(R.id.card_name_txt);
        }
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.mCardName.setText(mCardNameList.get(position));
    }

    public CardsAdapter(ArrayList<String> cardNameList) {
        mCardNameList = cardNameList;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        CardViewHolder vh = new CardViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return mCardNameList.size();
    }
}
