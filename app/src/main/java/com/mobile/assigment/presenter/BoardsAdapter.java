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

    public static class BoardViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public BoardViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.board_project_name);
        }
    }

    public BoardsAdapter(ArrayList<String> dataset) {
        mDataset = dataset;
    }

    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.board_recycler_view_item, parent, false);
        BoardViewHolder vh = new BoardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BoardViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
