package com.mobile.assigment.presenter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.assigment.R;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ListCardViewHolder> {
    private ArrayList<String> mDataset;
    private Activity mParentActivity;
    private ArrayList<ArrayList<String>> mCardsData;

    public class ListCardViewHolder extends RecyclerView.ViewHolder {
        public TextView mListNameTextView;
        public RecyclerView mCardsRecyclerView;
        public CircleButton mAddCardButton;
        public CardsAdapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;

        public ListCardViewHolder(View v) {
            super(v);
            mListNameTextView = v.findViewById(R.id.card_list_name_txt);
            mAddCardButton = v.findViewById(R.id.add_card_btn);
            mCardsRecyclerView = v.findViewById(R.id.cards_recycler_view);
            mLayoutManager = new LinearLayoutManager(mParentActivity, LinearLayoutManager.VERTICAL, false);
            mCardsRecyclerView.setLayoutManager(mLayoutManager);
        }

        public void setUpAdapter(ArrayList<String> dataset) {
            mAdapter = new CardsAdapter(dataset);
            mCardsRecyclerView.setAdapter(mAdapter);
        }
    }

    public ListCardAdapter(ArrayList<String> dataSet) {
        mDataset = dataSet;
        mCardsData = new ArrayList<>();
        //TODO: for now, we initialize an empty list of card names, in the future, load from database
        for (int i = 0; i < mDataset.size(); i++) {
            mCardsData.add(new ArrayList<String>());
        }
    }

    public void setParentActivity(Activity activity) {
        this.mParentActivity = activity;
    }

    @Override
    public ListCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_item, parent, false);
        ListCardViewHolder vh = new ListCardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ListCardViewHolder holder, int position) {
        holder.mListNameTextView.setText(mDataset.get(position));

        holder.setUpAdapter(mCardsData.get(position));
        final int pos = position;

        //create data to add
        holder.mAddCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Adding new card");
                System.out.println(pos);
                mCardsData.get(pos).add("New card");
                holder.mAdapter.notifyItemChanged(mCardsData.get(pos).size() - 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
