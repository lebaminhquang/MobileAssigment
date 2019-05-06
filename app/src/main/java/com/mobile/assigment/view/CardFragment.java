package com.mobile.assigment.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mobile.assigment.R;
import com.mobile.assigment.UserInfo;
import com.mobile.assigment.model.Card;
import com.mobile.assigment.presenter.CardLabelAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class CardFragment extends Fragment {
    LinearLayout mCardLabelLayout;
    LinearLayout mCardMemberLayout;
    LinearLayout mCardDueDateLayout;
    LinearLayout mCardChecklistLayout;

    AlertDialog mMemberDialog;
    AlertDialog mDueDateDialog;
    Activity mParentActivity;

    Button mPickDateBtn;
    Button mPickTimeBtn;
    EditText mCardDescriptionEdt;
    LinearLayout mSaveCardBtn;
    DatePickerDialog mDatePickerDialog;
    TimePickerDialog mTimePickerDialog;
    TextView mDueDateTxtView;
    TextView mDueTimeTxtView;
    TextView mCardLabelTxtView;
    TextView mCardDueDateTimeTextView;

    String mCardName;
    String mCardID;

    RecyclerView mCardLabelsRecyclerView;
    CardLabelAdapter mCardLabelsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.card_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCardLabelLayout = view.findViewById(R.id.card_label);
        mCardDescriptionEdt = view.findViewById(R.id.card_description_edt);
        mCardMemberLayout = view.findViewById(R.id.card_members);
        mCardDueDateLayout = view.findViewById(R.id.card_due_date);
        mCardChecklistLayout = view.findViewById(R.id.card_checklist);
        mCardLabelTxtView = view.findViewById(R.id.card_label_txt);
        mSaveCardBtn = view.findViewById(R.id.save_card_btn);
        mCardDueDateTimeTextView = view.findViewById(R.id.card_due_date_time_txt);

        setUpCardLabelRecyclerView();

        //setting up onclicklisteners
        mCardLabelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardLabelsRecyclerView.setVisibility(View.VISIBLE);
                mCardLabelTxtView.setVisibility(View.GONE);
            }
        });

        mCardMemberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardLabelsRecyclerView.setVisibility(View.GONE);
                mCardLabelTxtView.setVisibility(View.VISIBLE);
                mMemberDialog.show();
            }
        });

        mCardDueDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardLabelsRecyclerView.setVisibility(View.GONE);
                mCardLabelTxtView.setVisibility(View.VISIBLE);
                mDueDateDialog.show();
            }
        });

        mCardChecklistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardLabelsRecyclerView.setVisibility(View.GONE);
                mCardLabelTxtView.setVisibility(View.VISIBLE);
            }
        });

        //set up save card btn
        mSaveCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = new Card();
                card.setName(mCardName);
                card.setCardID(mCardID);
                card.setDescription(mCardDescriptionEdt.getText().toString());
                card.setDueDate(mCardDueDateTimeTextView.getText().toString());
                card.setLabelNames(mCardLabelsAdapter.getLabelNames());
                card.setLabelChecked(mCardLabelsAdapter.getLabelsChecked());
                card.setLabelColors(mCardLabelsAdapter.getLabelColors());
                UserInfo.getInstance().setCurrentCard(card);
                Card.updateCard(card);
            }
        });
    }
    public void setUpCardLabelRecyclerView() {
        //default color
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#004cff"); //blue
        colors.add("#00eaff"); //cyan
        colors.add("#a6a8ab"); //gray
        colors.add("#00ff11"); //green
        colors.add("#fff200"); //yellow
        colors.add("#ff6f00"); //orange
        colors.add("#ff0004"); //red
        colors.add("#aa00ff"); //purple

        //set up recyclerview
        mCardLabelsRecyclerView = getActivity().findViewById(R.id.card_label_recycler_view);
        mCardLabelsAdapter = new CardLabelAdapter(colors, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mCardLabelsRecyclerView.setLayoutManager(layoutManager);
        mCardLabelsRecyclerView.setAdapter(mCardLabelsAdapter);
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
                String chosenMonth = month + "";
                if (month < 10) {
                    chosenMonth = "0" + chosenMonth;
                }

                String chosenDay = dayOfMonth + "";
                if (dayOfMonth < 10) {
                    chosenDay = "0" + chosenDay;
                }
                String chosenYear = year + "";
                String str = chosenDay + "-" + chosenMonth + "-" + chosenYear;
                mDueDateTxtView.setText(str);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));

        mTimePickerDialog = new TimePickerDialog(mParentActivity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String minuteStr = minute + "";
                String hourStr = hourOfDay + "";
                if (hourOfDay < 10) {
                    hourStr = "0" + hourOfDay;
                }

                if (minute < 10) {
                    minuteStr = "0" + minuteStr;
                }

                String str = hourStr + ":" + minuteStr;
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
                        handleDueDateAndTime();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        mDueDateDialog = dueDateDialogBuilder.create();
    }

    public void handleDueDateAndTime() {
        String dueDate = mDueDateTxtView.getText().toString();
        String dueTime = mDueTimeTxtView.getText().toString();

        if (TextUtils.isEmpty(dueDate)) {
            Calendar cal = Calendar.getInstance();
            dueDate = String.format("%02d-%02d-%d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
        }
        if (TextUtils.isEmpty(dueTime)) {
            dueTime = "24:00";
        }
        String finalDueDate = dueDate + " at " + dueTime;
        mCardDueDateTimeTextView.setText(finalDueDate);
    }

    public void setCardNameAndID(String cardName, String cardID) {
        mCardName = cardName;
        mCardID = cardID;
    }

    public void loadCardData(Card card) {
        mCardDueDateTimeTextView.setText(card.getDueDate());
        mCardName = card.getName();
        mCardID = card.getCardID();

        mCardLabelsAdapter.setData(card.getLabelNames(), card.getLabelColors(), card.getLabelChecked());
    }
}
