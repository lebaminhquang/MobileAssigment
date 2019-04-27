package com.mobile.assigment.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.assigment.R;
import com.mobile.assigment.model.Board;
import com.mobile.assigment.model.Card;
import com.mobile.assigment.model.CheckList;
import com.mobile.assigment.model.Comment;
import com.mobile.assigment.model.Interface.ListCardInterface;
import com.mobile.assigment.model.ListCard;
import com.mobile.assigment.model.Team;
import com.mobile.assigment.model.User;

import java.util.ArrayList;
import java.util.List;

public class FirebaseTestActivity extends AppCompatActivity {

    private EditText edtBoardName,edtBackground,edtOwnerId;
    private CheckBox chkVisibility,chkPersonal;
    private Button btnCreateBoard;

    Team team = new Team();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        addControls();
        addEvents();

    }

    private void addControls() {
        edtBoardName = findViewById(R.id.edtBoardName);
        edtBackground = findViewById(R.id.edtBackground);
        edtOwnerId = findViewById(R.id.edtOwnerId);
        chkVisibility = findViewById(R.id.chkVisibility);
        chkPersonal = findViewById(R.id.chkPersonal);
        btnCreateBoard = findViewById(R.id.btnCreateBoard);
    }

    private void addEvents() {
        btnCreateBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Board board = new Board();
                board.setBoardName(edtBoardName.getText().toString());
                board.setBackground(edtBackground.getText().toString());
                if (chkVisibility.isChecked()) board.setVisibility(true); else board.setVisibility(false);
                Board.BoardType type;
                if (chkPersonal.isChecked()) type = Board.BoardType.PersonalBoard;
                else type = Board.BoardType.TeamBoard;
                Board.createBoard(board,edtOwnerId.getText().toString(),type);
            }
        });
    }


}
