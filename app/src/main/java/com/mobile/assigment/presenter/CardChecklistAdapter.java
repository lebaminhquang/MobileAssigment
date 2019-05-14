package com.mobile.assigment.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.assigment.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardChecklistAdapter extends RecyclerView.Adapter<CardChecklistAdapter.CardChecklistViewHolder> {
    private ArrayList<String> mItemNameList;
    private ArrayList<Boolean> mCheckedStateList;

    public class CardChecklistViewHolder extends RecyclerView.ViewHolder{
        public CheckBox mCheckbox;
        public TextView mItemName;
        public ImageView mDeleteBtn;
        public CardChecklistViewHolder(View itemView) {
            super(itemView);
            mCheckbox = itemView.findViewById(R.id.item_checkbox);
            mItemName = itemView.findViewById(R.id.checklist_item_name);
            mDeleteBtn = itemView.findViewById(R.id.checklist_item_delete_btn);

            mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mCheckedStateList.set(getAdapterPosition(), true);
                    } else {
                        mCheckedStateList.set(getAdapterPosition(), false);
                    }
                }
            });

            mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemNameList.remove(getAdapterPosition());
                    mCheckedStateList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public CardChecklistAdapter() {
        mItemNameList = new ArrayList<>();
        mCheckedStateList = new ArrayList<>();
    }

    @Override
    public CardChecklistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_checklist_item, parent, false);
        CardChecklistViewHolder vh = new CardChecklistViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardChecklistViewHolder holder, int position) {
        holder.mItemName.setText(mItemNameList.get(position));
        holder.mCheckbox.setChecked(mCheckedStateList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemNameList.size();
    }

    public void addItem(String itemName) {
        mItemNameList.add(itemName);
        mCheckedStateList.add(false);
        notifyItemChanged(mItemNameList.size() - 1);
    }

    public ArrayList<String> getChecklistData() {
        ArrayList<String> mData = new ArrayList<>();
        for (int i = 0; i < mItemNameList.size(); i++) {
            String data = mItemNameList.get(i) + "_" + mCheckedStateList.get(i).toString();
            mData.add(data);
        }
        return mData;
    }

    public void setData(ArrayList<String> data) {
        for (String item: data) {
            String[] itemData = item.split("_");
            mItemNameList.add(itemData[0]);
            mCheckedStateList.add(Boolean.parseBoolean(itemData[1]));
        }
        notifyDataSetChanged();
    }
}


