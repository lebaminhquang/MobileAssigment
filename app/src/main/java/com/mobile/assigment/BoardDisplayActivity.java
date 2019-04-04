package com.mobile.assigment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class BoardDisplayActivity extends AppCompatActivity {
    private static String EXTRA_MESSAGE = "com.mobile.assignment";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_display);
        Intent intent = getIntent();
        String boardName = intent.getStringExtra(EXTRA_MESSAGE);

        getSupportActionBar().setTitle(boardName);
    }
}
