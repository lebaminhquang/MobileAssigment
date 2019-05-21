package com.mobile.assigment.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.assigment.R;
import com.mobile.assigment.model.Card;
import com.mobile.assigment.model.Interface.OnCardLoadedForFragmentCallback;
import com.mobile.assigment.model.Interface.OnCardLoadedForListCallback;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ListCardViewHolder> implements OnCardLoadedForListCallback{
    private ArrayList<String> mListCardNameLst;
    private ArrayList<String> mListCardIDLst;
    private Activity mParentActivity;
    private OnCardClickedCallback mCardClickedCallback;
    private OnCardLoadedForFragmentCallback mCardLoadedCallback;
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

        public void setUpAdapter(OnCardClickedCallback cardClickedCallback, OnCardLoadedForFragmentCallback cardLoadedCallback) {
            mAdapter = new CardsAdapter(cardClickedCallback, cardLoadedCallback);
            mAdapter.setListID(mListCardIDLst.get(getAdapterPosition()));
            mCardsRecyclerView.setAdapter(mAdapter);
        }
    }

    public ListCardAdapter(OnCardClickedCallback cardClickedCallback, OnCardLoadedForFragmentCallback cardLoadedCallback) {
        mListCardNameLst = new ArrayList<>();
        mListCardIDLst = new ArrayList<>();

        mCardClickedCallback = cardClickedCallback;
        mCardLoadedCallback = cardLoadedCallback;
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
                        String newCardName = mNewCardNameTxt.getText().toString();
                        String newCardId = pushNewCardToDatabase(newCardName, pos);
                        mCurrentList.mAdapter.addNewCard(newCardName, newCardId);
                        mNewCardNameTxt.setText("");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setTitle("New Card Name");

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
        holder.mListNameTextView.setText(mListCardNameLst.get(position));

        holder.setUpAdapter(mCardClickedCallback, mCardLoadedCallback);
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
                mListCardNameLst.remove(pos);
                //TODO: remove this list from database
                notifyDataSetChanged();
            }
        });
    }

    public int addList(String listName, String listID) {
        mListCardNameLst.add(listName);
        mListCardIDLst.add(listID);
        notifyItemChanged(mListCardNameLst.size() - 1);
        return mListCardIDLst.size() - 1;
    }

    @Override
    public int getItemCount() {
        return mListCardNameLst.size();
    }

    public void clear() {
        mListCardNameLst.clear();
        mListCardIDLst.clear();
        notifyDataSetChanged();
    }

    public String pushNewCardToDatabase(String newCardName, int position) {
        Card card = new Card();
        card.setName(newCardName);
        String listID = mListCardIDLst.get(position);
        return Card.createCard(card, listID);
    }

    public void addCardToList(ListCardViewHolder holder, int position, String cardName, String cardID) {
        holder.mAdapter.addNewCard(cardName, cardID);
    }

    @Override
    public void onCardLoadedForList(ListCardAdapter.ListCardViewHolder holder, Card card) {
        holder.mAdapter.loadCardFromDatabase(card);
    }
}
