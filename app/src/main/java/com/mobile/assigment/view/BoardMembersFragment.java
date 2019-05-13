package com.mobile.assigment.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.assigment.R;
import com.mobile.assigment.UserInfo;
import com.mobile.assigment.presenter.BoardMemberAdapter;

import java.util.ArrayList;

public class BoardMembersFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private BoardMemberAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mMemberList;
    private EditText mNewMemberNameTxt;
    private Button mAddMemberBtn;
    private View dialogView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogView = inflater.inflate(R.layout.add_member_dialog_view, null);
        return inflater.inflate(R.layout.board_members_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMemberList = UserInfo.getInstance().getCurrentBoard().getMemberList();
        setUpRecyclerView();
    }

    public void setUpRecyclerView() {
        mRecyclerView = getActivity().findViewById(R.id.board_members_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BoardMemberAdapter(mMemberList);
        mAdapter.setParentActivity(getActivity());
        mAdapter.addMemberDialog();
        mRecyclerView.setAdapter(mAdapter);
    }


}
