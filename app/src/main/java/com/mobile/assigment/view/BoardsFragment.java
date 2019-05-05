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
import com.mobile.assigment.UserInfo;
import com.mobile.assigment.model.Board;
import com.mobile.assigment.model.Interface.BoardInterface;
import com.mobile.assigment.model.Interface.UserInterface;
import com.mobile.assigment.model.User;
import com.mobile.assigment.presenter.BoardsAdapter;

import java.util.ArrayList;
import java.util.Map;

public class BoardsFragment extends Fragment implements BoardInterface, UserInterface{
    private RecyclerView mRecyclerView;
    private BoardsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mAddBoardButton;
    private AlertDialog mAddBoardDialog;
    private EditText mAddBoardTxt;
    private ArrayList<Board> mBoardList = new ArrayList<>();
    private static String EXTRA_MESSAGE = "com.mobile.assignment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.boards_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUpRecyclerView();
        loadAllBoards();
    }

    public void setUpRecyclerView() {
        mRecyclerView = getActivity().findViewById(R.id.boards_recycler_view);
        mAddBoardButton = getActivity().findViewById(R.id.add_board_btn);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BoardsAdapter();
        mAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mRecyclerView.indexOfChild(v);
                //load the clicked board
                UserInfo.getInstance().setCurrentBoard(mBoardList.get(pos));

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
                        String newBoardName = mAddBoardTxt.getText().toString();
                        mAdapter.addBoard(newBoardName);
                        pushBoardToDatabase(newBoardName);
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

    public void loadAllBoards() {
        User.getUser(UserInfo.getInstance().getId(), this);
    }

    public void pushBoardToDatabase(String boardName) {
        Board newBoard = new Board();
        newBoard.setBoardName(boardName);
        Board.createBoard(newBoard, UserInfo.getInstance().getId(), Board.BoardType.PersonalBoard);
    }

    @Override
    public void OnBoardReceived(Board board) {
        mBoardList.add(board);
        mAdapter.addBoard(board.getBoardName());
    }

    @Override
    public void OnUserReceived(User user) {
        if (user.getPersonalBoards() != null) {
            for (String boardId : user.getPersonalBoards().values()) {
                Board.getBoard(boardId, this);
            }
        }
    }
}
