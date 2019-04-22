package com.mobile.assigment.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.assigment.R;

import java.util.ArrayList;

public class BoardMemberAdapter extends RecyclerView.Adapter<BoardMemberAdapter.BoardMemberViewHolder> {
    private ArrayList<String> mMemberList;

    public class BoardMemberViewHolder extends RecyclerView.ViewHolder {
        public TextView mMemberNameTxt;
        public BoardMemberViewHolder(View view) {
            super(view);
            mMemberNameTxt = view.findViewById(R.id.board_member_name);
        }
    }

    @Override
    public void onBindViewHolder(BoardMemberViewHolder holder, int position) {
        holder.mMemberNameTxt.setText(mMemberList.get(position));
    }

    public BoardMemberAdapter(ArrayList<String> memberList) {
        mMemberList = memberList;
    }

    @Override
    public BoardMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.board_member_item, parent, false);
        BoardMemberViewHolder vh = new BoardMemberViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return mMemberList.size();
    }
}
