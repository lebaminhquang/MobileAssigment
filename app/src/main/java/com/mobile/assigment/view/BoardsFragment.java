package com.mobile.assigment.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.assigment.R;
import com.mobile.assigment.presenter.BoardsAdapter;

import java.util.ArrayList;

public class BoardsFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mDataset;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {



        return inflater.inflate(R.layout.boards_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //TODO: find way to load data, this is just a dummy test
        ArrayList<String> dummyData = new ArrayList<String>();
        dummyData.add("BTL Mobile");
        dummyData.add("BTL PPL");
        dummyData.add("Project");
        setData(dummyData);
        setUpRecyclerView();
    }

    public void setUpRecyclerView() {
        mRecyclerView = getActivity().findViewById(R.id.boards_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BoardsAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setData(ArrayList<String> data) {
        mDataset = data;
    }
}
