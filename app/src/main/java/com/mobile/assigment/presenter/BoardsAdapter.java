package com.mobile.assigment.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.assigment.R;

import java.util.ArrayList;

public class BoardsAdapter extends RecyclerView.Adapter<BoardsAdapter.BoardViewHolder> {
    private ArrayList<String> mDataset;
    private View.OnClickListener mClickListener;

    public class BoardViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public BoardViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.board_project_name);
        }
    }

    public BoardsAdapter(ArrayList<String> dataset) {
        mDataset = dataset;
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.board_recycler_view_item, parent, false);
        BoardViewHolder vh = new BoardViewHolder(v);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(v);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(BoardViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position));
    }

    public void addBoard(String boardName) {
        mDataset.add(boardName);
        notifyItemChanged(mDataset.size() - 1);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
