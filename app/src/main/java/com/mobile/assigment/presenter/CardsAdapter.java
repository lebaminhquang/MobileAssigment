package com.mobile.assigment.presenter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.assigment.R;
import com.mobile.assigment.UserInfo;
import com.mobile.assigment.model.Card;
import com.mobile.assigment.model.Interface.OnCardLoadedForFragmentCallback;

import java.util.ArrayList;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> implements OnCardSavedCallback {
    private String mListID;
    private ArrayList<Card> mCardList;
    OnCardClickedCallback mCardClickedCallback;
    OnCardLoadedForFragmentCallback mCardLoadedCallback;

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView mCardName;
        public TextView mCardDueDate;
        public TextView mCardChecklistProgress;
        public LinearLayout mDueDateLayout;
        public LinearLayout mChecklistLayout;
        public LinearLayout mDescriptionLayout;
        public CardViewHolder(View v) {
            super(v);
            mCardName = v.findViewById(R.id.item_card_name_txt);
            mCardDueDate = v.findViewById(R.id.item_card_due_date_txt);
            mCardChecklistProgress = v.findViewById(R.id.item_card_checklist_txt);
            mDueDateLayout = v.findViewById(R.id.item_card_due_date_layout);
            mChecklistLayout = v.findViewById(R.id.item_card_checklist_layout);
            mDescriptionLayout = v.findViewById(R.id.item_card_description_layout);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mCardClickedCallback.displayCardFragment(mCardList.get(position).getName(), mCardList.get(position).getCardID());
                    UserInfo.getInstance().setCurrentListID(mListID);
                    UserInfo.getInstance().setCurrentCardsAdapter(CardsAdapter.this);
                    UserInfo.getInstance().setCurrentCardPosition(position);
                    Card.getCardForFragment(mCardList.get(position).getCardID(), mCardLoadedCallback);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Card currentCard = mCardList.get(position);

        holder.mCardName.setText(currentCard.getName());

        if (!TextUtils.isEmpty(currentCard.getDueDate())) {
            holder.mCardDueDate.setText(currentCard.getDueDate());
            holder.mDueDateLayout.setVisibility(View.VISIBLE);
        }

        if (!currentCard.getChecklist().isEmpty()) {
            int numberOfTasks = currentCard.getChecklist().size();
            int numberOfCompletedTasks = 0;
            for (int i = 0; i < numberOfTasks; i++) {
                if (currentCard.getChecklist().get(i).contains("true")) {
                    numberOfCompletedTasks += 1;
                }
            }
            String progressText = numberOfCompletedTasks + "/" + numberOfTasks;
            holder.mCardChecklistProgress.setText(progressText);
            holder.mChecklistLayout.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(currentCard.getDescription())) {
            holder.mDescriptionLayout.setVisibility(View.VISIBLE);
        }
    }


    public CardsAdapter(OnCardClickedCallback cardClickedCallback, OnCardLoadedForFragmentCallback cardLoadedCallback) {
        mCardList = new ArrayList<>();
        mCardClickedCallback = cardClickedCallback;
        mCardLoadedCallback = cardLoadedCallback;
    }

    public void addNewCard(String cardName, String cardID) {
        Card newCard = new Card();
        newCard.setName(cardName);
        newCard.setCardID(cardID);
        mCardList.add(newCard);
        notifyItemChanged(mCardList.size() - 1);
    }

    public void setListID(String ID) {
        mListID = ID;
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
        return mCardList.size();
    }

    @Override
    public void onCardSaved(Card card) {
        int position = UserInfo.getInstance().getCurrentCardPosition();
        mCardList.set(position, card);
        notifyItemChanged(position);
    }

    public void loadCardFromDatabase(Card card) {
        mCardList.add(card);
        notifyItemChanged(mCardList.size() - 1);
    }
}
