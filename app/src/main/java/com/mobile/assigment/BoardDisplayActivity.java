package com.mobile.assigment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mobile.assigment.presenter.ListCardAdapter;

import java.util.ArrayList;

public class BoardDisplayActivity extends AppCompatActivity {
    private static String EXTRA_MESSAGE = "com.mobile.assignment";
    private ArrayList<String> mListCardNames = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mListCardRecyclerView;
    private ListCardAdapter mAdapter;
    private int currentItemPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_display);
        Intent intent = getIntent();
        String boardName = intent.getStringExtra(EXTRA_MESSAGE);
        getSupportActionBar().setTitle(boardName);

        loadAllListsInBoard();
        setUpCardListRecyclerView();
    }

    public void loadAllListsInBoard() {
        //TODO: implement loading from database in the future
        mListCardNames.add("To Do");
        mListCardNames.add("Doing");
        mListCardNames.add("Done");
    }

    public void setUpCardListRecyclerView() {
        mListCardRecyclerView = findViewById(R.id.list_card_recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mListCardRecyclerView.setLayoutManager(mLayoutManager);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(mListCardRecyclerView);
        mAdapter = new ListCardAdapter(mListCardNames);
        mListCardRecyclerView.setAdapter(mAdapter);
    }
}
