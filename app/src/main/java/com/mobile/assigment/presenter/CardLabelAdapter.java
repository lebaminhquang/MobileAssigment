package com.mobile.assigment.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.assigment.R;

import java.util.ArrayList;

public class CardLabelAdapter extends RecyclerView.Adapter<CardLabelAdapter.CardLabelViewHolder> {
    private ArrayList<String> mLabelNames;
    private ArrayList<String> mLabelColors;
    private ArrayList<Boolean> mLabelsChecked;
    private Activity mParentActivity;

    private EditText mLabelNameEdt;
    private AlertDialog mLabelNameDialog;
    private int mCurrentLabelPosition;

    public class CardLabelViewHolder extends RecyclerView.ViewHolder {
        public TextView mCardLabelNameTxt;
        public ImageView mCardLabelChecked;
        public ImageButton mEditCardLabelBtn;
        public LinearLayout mCardLabelBackground;
        public Boolean mIsChecked;

        public CardLabelViewHolder(View itemView) {
            super(itemView);
            mCardLabelChecked = itemView.findViewById(R.id.card_label_checked);
            mCardLabelNameTxt = itemView.findViewById(R.id.card_label_name);
            mEditCardLabelBtn = itemView.findViewById(R.id.edit_card_label);
            mCardLabelBackground = itemView.findViewById(R.id.card_label_background_layout);
            mIsChecked = false;
            setUpCheckedStatus();
        }

        public void setUpCheckedStatus() {
            mCardLabelBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mLabelsChecked.get(position)) {
                        mCardLabelChecked.setVisibility(View.GONE);
                        mLabelsChecked.set(position, false);
                    } else {
                        mCardLabelChecked.setVisibility(View.VISIBLE);
                        mLabelsChecked.set(position, true);
                    }
                }
            });

            mEditCardLabelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentLabelPosition = getAdapterPosition();
                    mLabelNameDialog.show();
                }
            });
        }
    }

    public CardLabelAdapter(ArrayList<String> labelColors, Activity activity) {
        mLabelColors = labelColors;
        mLabelNames = new ArrayList<>();
        mLabelsChecked = new ArrayList<>();
        for (String color: labelColors) {
            mLabelNames.add("");
            mLabelsChecked.add(false);
        }

        mParentActivity = activity;
        setUpLabelNameDialog();
    }

    public void setUpLabelNameDialog() {
        LayoutInflater inflater = mParentActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_card_label_name_dialog_view, null);
        mLabelNameEdt = dialogView.findViewById(R.id.card_label_name_edt);

        AlertDialog.Builder builder = new AlertDialog.Builder(mParentActivity);
        builder.setView(dialogView).setTitle("Label Name")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String labelName = mLabelNameEdt.getText().toString();
                        mLabelNames.set(mCurrentLabelPosition, labelName);
                        notifyItemChanged(mCurrentLabelPosition);
                        mLabelNameEdt.setText("");
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        mLabelNameDialog = builder.create();
    }

    @Override
    public CardLabelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_label_item, parent, false);

        CardLabelViewHolder vh = new CardLabelViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardLabelViewHolder holder, int position) {
        holder.mCardLabelNameTxt.setText(mLabelNames.get(position));
        holder.mCardLabelBackground.setBackgroundColor(Color.parseColor(mLabelColors.get(position)));
        if (mLabelsChecked.get(position)) {
            holder.mCardLabelChecked.setVisibility(View.VISIBLE);
        } else {
            holder.mCardLabelChecked.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mLabelColors.size();
    }

    public ArrayList<String> getLabelNames() {
        return mLabelNames;
    }

    public ArrayList<String> getLabelColors() {
        return mLabelColors;
    }

    public ArrayList<Boolean> getLabelsChecked() {
        return mLabelsChecked;
    }

    public void setData(ArrayList<String> names, ArrayList<String> colors, ArrayList<Boolean> checked) {
        if (names != null) {
            mLabelNames = names;
        }
        if (colors != null) {
            mLabelColors = colors;
        }
        if (checked != null) {
            mLabelsChecked = checked;
        }
        notifyDataSetChanged();
    }
}
