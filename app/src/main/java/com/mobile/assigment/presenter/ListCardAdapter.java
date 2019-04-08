package com.mobile.assigment.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.assigment.R;

import java.util.ArrayList;

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ListCardViewHolder> {
    private ArrayList<String> mDataset;
    public class ListCardViewHolder extends RecyclerView.ViewHolder {
        public TextView mListNameTextView;

        public ListCardViewHolder(View v) {
            super(v);
            mListNameTextView = v.findViewById(R.id.card_list_name_txt);
        }
    }

    public ListCardAdapter(ArrayList<String> dataSet) {
        mDataset = dataSet;
    }

    @Override
    public ListCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_item, parent, false);
        ListCardViewHolder vh = new ListCardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListCardViewHolder holder, int position) {
        holder.mListNameTextView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
