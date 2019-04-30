package com.mobile.assigment.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.assigment.R;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ListCardViewHolder> {
    private ArrayList<String> mDataset;
    private Activity mParentActivity;
    private ArrayList<ArrayList<String>> mCardsData;
    private OnCardClickedCallback mCallback;
    private AlertDialog mAddCardDialog;
    private EditText mNewCardNameTxt;

    //current list is the list that is clicked on
    private ListCardViewHolder mCurrentList;

    public class ListCardViewHolder extends RecyclerView.ViewHolder {
        public TextView mListNameTextView;
        public RecyclerView mCardsRecyclerView;
        public CircleButton mAddCardButton;
        public CircleButton mDeleteListButton;
        public CardsAdapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;

        public ListCardViewHolder(View v) {
            super(v);
            mListNameTextView = v.findViewById(R.id.card_list_name_txt);
            mAddCardButton = v.findViewById(R.id.add_card_btn);
            mDeleteListButton = v.findViewById(R.id.delete_list_btn);
            mCardsRecyclerView = v.findViewById(R.id.cards_recycler_view);
            mLayoutManager = new LinearLayoutManager(mParentActivity, LinearLayoutManager.VERTICAL, false);
            mCardsRecyclerView.setLayoutManager(mLayoutManager);
        }

        public void setUpAdapter(ArrayList<String> dataset, OnCardClickedCallback callback) {
            mAdapter = new CardsAdapter(dataset, callback);
            mCardsRecyclerView.setAdapter(mAdapter);
            mAdapter.setParentActivity(mParentActivity);
        }
    }

    public ListCardAdapter(ArrayList<String> dataSet, OnCardClickedCallback callback) {
        mDataset = dataSet;
        mCardsData = new ArrayList<>();
        //TODO: for now, we initialize an empty list of card names, in the future, load from database
        for (int i = 0; i < mDataset.size(); i++) {
            mCardsData.add(new ArrayList<String>());
        }

         mCallback = callback;

    }

    public void setParentActivity(Activity activity) {
        this.mParentActivity = activity;
    }

    public void setUpAddCardDialogBox() {
        //create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mParentActivity);
        LayoutInflater inflater = mParentActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_card_dialog_view, null);
        mNewCardNameTxt = dialogView.findViewById(R.id.new_card_name_txt);

        builder.setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int pos = mCurrentList.getAdapterPosition();
                        mCardsData.get(pos).add(mNewCardNameTxt.getText().toString());
                        mCurrentList.mAdapter.notifyItemChanged(mCardsData.get(pos).size() - 1);
                        mNewCardNameTxt.setText("");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setTitle("New card name");

        mAddCardDialog = builder.create();
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

        holder.setUpAdapter(mCardsData.get(position), mCallback);
        final int pos = position;

        //create data to add
        holder.mAddCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentList = holder;
                mAddCardDialog.show();
            }
        });

        holder.mDeleteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataset.remove(pos);
                //TODO: remove this list from database
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
