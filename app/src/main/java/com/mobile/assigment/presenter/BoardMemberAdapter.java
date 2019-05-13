package com.mobile.assigment.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.assigment.R;
import com.mobile.assigment.UserInfo;
import com.mobile.assigment.UserList;
import com.mobile.assigment.model.Board;
import com.mobile.assigment.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardMemberAdapter extends RecyclerView.Adapter<BoardMemberAdapter.BoardMemberViewHolder> {
    private ArrayList<String> mMemberList;
    private Activity mParentActivity;
    private EditText mNewMemberNameTxt;
    private Button mAddMemberBtn;
    private AlertDialog mAddMemberDialog;

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

    public void addMemberDialog() {
        final LayoutInflater inflater = mParentActivity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_member_dialog_view, null);
        mNewMemberNameTxt = dialogView.findViewById(R.id.new_member_name_txt);

        mAddMemberBtn = mParentActivity.findViewById(R.id.add_member);
        mAddMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(mParentActivity);
                a_builder.setView(dialogView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String memberName = mNewMemberNameTxt.getText().toString();
                                mMemberList.add(memberName);
                                mNewMemberNameTxt.setText("");

                                Board board = UserInfo.getInstance().getCurrentBoard();
                                board.setMemberList(mMemberList);
                                Board.updateBoard(board);

                                ArrayList<User> listUser = UserList.getInstance().getAllUsers();
                                for(User user : listUser) {
                                    if(user.getName().equalsIgnoreCase(memberName)) {
                                        Map<String, String> personalBoards = user.getPersonalBoards();
                                        personalBoards.put(board.getBoardID(), board.getBoardID());
                                        user.setPersonalBoards(personalBoards);
                                        User.updateUserInfo(user);
                                        break;
                                    }
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                mAddMemberDialog = a_builder.create();
                mAddMemberDialog.setTitle("Add new member to " + UserInfo.getInstance().getCurrentBoard().getBoardName().toUpperCase());
                mAddMemberDialog.show();
            }
        });
    }

    @Override
    public BoardMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.board_member_item, parent, false);
        BoardMemberViewHolder vh = new BoardMemberViewHolder(v);
        return vh;
    }

    public void setParentActivity(Activity activity) {
        this.mParentActivity = activity;
    }

    @Override
    public int getItemCount() {
        return mMemberList.size();
    }
}
