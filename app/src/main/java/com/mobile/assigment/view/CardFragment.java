package com.mobile.assigment.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobile.assigment.R;

import java.util.Calendar;
import java.util.Date;

public class CardFragment extends Fragment {
    LinearLayout mCardLabelLayout;
    LinearLayout mCardMemberLayout;
    LinearLayout mCardDueDateLayout;
    LinearLayout mCardChecklistLayout;
    LinearLayout mCardLabelList;

    AlertDialog mMemberDialog;
    AlertDialog mDueDateDialog;
    Activity mParentActivity;

    Button mPickDateBtn;
    Button mPickTimeBtn;
    DatePickerDialog mDatePickerDialog;
    TimePickerDialog mTimePickerDialog;
    TextView mDueDateTxtView;
    TextView mDueTimeTxtView;
    TextView mCardLabelTxtView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.card_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCardLabelLayout = view.findViewById(R.id.card_label);
        mCardMemberLayout = view.findViewById(R.id.card_members);
        mCardDueDateLayout = view.findViewById(R.id.card_due_date);
        mCardChecklistLayout = view.findViewById(R.id.card_checklist);
        mCardLabelList = view.findViewById(R.id.card_label_list);
        mCardLabelTxtView = view.findViewById(R.id.card_label_txt);

        //setting up onclicklisteners
        mCardLabelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardLabelList.setVisibility(View.VISIBLE);
                mCardLabelTxtView.setVisibility(View.GONE);
            }
        });

        mCardMemberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardLabelList.setVisibility(View.GONE);
                mCardLabelTxtView.setVisibility(View.VISIBLE);
                mMemberDialog.show();
            }
        });

        mCardDueDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardLabelList.setVisibility(View.GONE);
                mCardLabelTxtView.setVisibility(View.VISIBLE);
                mDueDateDialog.show();
            }
        });

        mCardChecklistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardLabelList.setVisibility(View.GONE);
                mCardLabelTxtView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setUp(Activity parentActivity) {
        mParentActivity = parentActivity;
        AlertDialog.Builder memberDialogBuilder = new AlertDialog.Builder(mParentActivity);
        memberDialogBuilder.setTitle("Card Members")
                .setMessage("Change member here")
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        mMemberDialog = memberDialogBuilder.create();


        LayoutInflater inflater = mParentActivity.getLayoutInflater();
        View dueDateView = inflater.inflate(R.layout.due_date_dialog_view, null);
        mPickDateBtn = dueDateView.findViewById(R.id.due_date_btn);
        mPickTimeBtn = dueDateView.findViewById(R.id.due_time_btn);
        mDueDateTxtView = dueDateView.findViewById(R.id.due_date_txt);
        mDueTimeTxtView = dueDateView.findViewById(R.id.due_time_txt);

        Calendar cal = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(mParentActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String str = dayOfMonth + "/" + month + "/" + year;
                mDueDateTxtView.setText(str);
            }
        }, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));

        mTimePickerDialog = new TimePickerDialog(mParentActivity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String str = hourOfDay + ":" + minute;
                mDueTimeTxtView.setText(str);
            }
        },0,0, true);

        mPickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog.show();
            }
        });
        mPickTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerDialog.show();
            }
        });

        AlertDialog.Builder dueDateDialogBuilder = new AlertDialog.Builder(mParentActivity);
        dueDateDialogBuilder.setTitle("Due Date")
                .setView(dueDateView)
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        mDueDateDialog = dueDateDialogBuilder.create();
    }
}
