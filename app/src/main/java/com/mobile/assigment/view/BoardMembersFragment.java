package com.mobile.assigment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.assigment.R;
import com.mobile.assigment.presenter.BoardMemberAdapter;

import java.util.ArrayList;

public class BoardMembersFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private BoardMemberAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mMemberList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.board_members_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ArrayList<String> data = new ArrayList<>();
        data.add("Member 1");
        data.add("Member 2");
        data.add("Member 3");

        mMemberList = data;
        setUpRecyclerView();
    }

    public void setUpRecyclerView() {
        mRecyclerView = getActivity().findViewById(R.id.board_members_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BoardMemberAdapter(mMemberList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
