package com.mobile.assigment.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.assigment.BoardDisplayActivity;
import com.mobile.assigment.R;
import com.mobile.assigment.presenter.BoardsAdapter;

import java.util.ArrayList;

public class BoardsFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private BoardsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mAddBoardButton;
    private ArrayList<String> mDataset;
    private AlertDialog mAddBoardDialog;
    private EditText mAddBoardTxt;
    private static String EXTRA_MESSAGE = "com.mobile.assignment";

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
        mAddBoardButton = getActivity().findViewById(R.id.add_board_btn);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BoardsAdapter(mDataset);
        mAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mRecyclerView.indexOfChild(v);
                View childView = mRecyclerView.getChildAt(pos);
                TextView boardNameTextView = (TextView) childView.findViewById(R.id.board_project_name);
                String boardName = boardNameTextView.getText().toString();
                switchToBoardActivity(boardName);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_board_dialog_view, null);
        mAddBoardTxt = dialogView.findViewById(R.id.new_board_name_txt);
        AlertDialog.Builder addBoardBuilder = new AlertDialog.Builder(getActivity());

        addBoardBuilder.setView(dialogView)
                .setTitle("Create Board")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.addBoard(mAddBoardTxt.getText().toString());
                        mAddBoardTxt.setText("");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        mAddBoardDialog = addBoardBuilder.create();
        mAddBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddBoardDialog.show();
            }
        });
    }
    public void switchToBoardActivity(String boardName) {
        //switch to new activity
        Intent newIntent = new Intent(getActivity(), BoardDisplayActivity.class);
        newIntent.putExtra(EXTRA_MESSAGE, boardName);
        getActivity().startActivity(newIntent);
    }
    public void setData(ArrayList<String> data) {
        mDataset = data;
    }
}
