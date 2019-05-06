package com.mobile.assigment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mobile.assigment.model.Board;
import com.mobile.assigment.model.Card;
import com.mobile.assigment.model.Interface.OnCardLoadedCallback;
import com.mobile.assigment.model.Interface.OnCardsInListReceivedCallback;
import com.mobile.assigment.model.Interface.OnListsInBoardReceived;
import com.mobile.assigment.model.ListCard;
import com.mobile.assigment.presenter.ListCardAdapter;
import com.mobile.assigment.presenter.OnCardClickedCallback;
import com.mobile.assigment.view.BoardMembersFragment;
import com.mobile.assigment.view.BoardSettingFragment;
import com.mobile.assigment.view.CardFragment;

import java.util.Map;

public class BoardDisplayActivity extends AppCompatActivity implements OnCardClickedCallback,
        OnListsInBoardReceived, OnCardsInListReceivedCallback, OnCardLoadedCallback {
    private static String EXTRA_MESSAGE = "com.mobile.assignment";
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mListCardRecyclerView;
    private ListCardAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private String mBoardName;

    private FragmentManager mFragmentManager;
    private BoardMembersFragment mBoardMembersFragment;
    private BoardSettingFragment mBoardSettingFragment;
    private CardFragment mCardFragment;

    private LinearLayout mCardNameLinearLayout;
    private EditText mCardNameEditText;

    private AlertDialog mAddListDialog;
    private EditText mNewListNameEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_display);
        mDrawerLayout = findViewById(R.id.board_display_drawer_layout);
        mNavigationView = findViewById(R.id.board_display_nav_view);
        mToolbar = findViewById(R.id.board_display_toolbar);
        mCardNameLinearLayout = findViewById(R.id.card_name_layout);
        mCardNameEditText = findViewById(R.id.card_name_txt);

        Intent intent = getIntent();
        mBoardName = intent.getStringExtra(EXTRA_MESSAGE);
        setSupportActionBar(mToolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(mBoardName);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back);

        setUpCardListRecyclerView();
        loadAllListsInBoard();
        setUpAddListDialog();

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

        mFragmentManager = getSupportFragmentManager();
        mBoardMembersFragment = new BoardMembersFragment();
        mBoardSettingFragment = new BoardSettingFragment();
        mCardFragment = new CardFragment();
        mCardFragment.setUp(BoardDisplayActivity.this);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_boards_settings:
                fragment = mBoardSettingFragment;
                break;
            case R.id.nav_boards_activity:
                break;
            case R.id.nav_boards_members:
                fragment = mBoardMembersFragment;
                break;
        }
        try {
            switchToAnotherFragment(fragment, menuItem.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mDrawerLayout.closeDrawers();
    }

    public void loadAllListsInBoard() {
        //clear all lists to avoid adding twice
        Board.getListCardInBoard(UserInfo.getInstance().getCurrentBoard().getBoardID(), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.board_display_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                if (mFragmentManager.getBackStackEntryCount() > 0) {
                    switchBackToMainFragment();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            case R.id.add_list_menu_btn:
                mAddListDialog.show();
                return true;
            case R.id.board_settings:
                mDrawerLayout.openDrawer(GravityCompat.END);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpCardListRecyclerView() {
        mListCardRecyclerView = findViewById(R.id.list_card_recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mListCardRecyclerView.setLayoutManager(mLayoutManager);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(mListCardRecyclerView);
        mAdapter = new ListCardAdapter(this, this);
        mAdapter.setParentActivity(BoardDisplayActivity.this);
        mAdapter.setUpAddCardDialogBox();
        mListCardRecyclerView.setAdapter(mAdapter);
    }


    public void switchToAnotherFragment(Fragment fragment, CharSequence fragmentName) {
        //hide the menu
        findViewById(R.id.board_settings).setVisibility(View.GONE);

        //hide the main view
        mListCardRecyclerView.setVisibility(View.GONE);

        //switch to the new fragment
        mFragmentManager.beginTransaction().replace(R.id.board_content, fragment).addToBackStack(fragmentName.toString()).commit();
        mToolbar.setTitle(fragmentName);
    }

    public void setUpAddListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_list_dialog_view, null);
        mNewListNameEditText = dialogView.findViewById(R.id.new_list_name_txt);

        builder.setView(dialogView).setTitle("New List Name")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newListName = mNewListNameEditText.getText().toString();
                        String newListID = pushNewListCardToDatabase(newListName);
                        mAdapter.addList(newListName, newListID);
                        mNewListNameEditText.setText("");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        mAddListDialog = builder.create();
    }

    @Override
    public void displayCardFragment(String cardName, String cardID) {
        //change toolbar
        mCardNameLinearLayout.setVisibility(View.VISIBLE);
        mCardNameEditText.setText(cardName);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.cardFragmentToolbarBackground));
        mCardFragment.setCardNameAndID(cardName, cardID);

        //hide the menu
        findViewById(R.id.board_settings).setVisibility(View.GONE);
        findViewById(R.id.add_list_menu_btn).setVisibility(View.GONE);
        //hide the main view
        mListCardRecyclerView.setVisibility(View.GONE);
        mFragmentManager.beginTransaction().replace(R.id.board_content, mCardFragment)
                .addToBackStack("Card_fragment: " + cardName).commit();
    }

    public void switchBackToMainFragment() {
        //change toolbar
        mFragmentManager.popBackStack();
        mCardNameLinearLayout.setVisibility(View.GONE);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mToolbar.setTitle(mBoardName);;
        //unhide the menu
        findViewById(R.id.board_settings).setVisibility(View.VISIBLE);
        findViewById(R.id.add_list_menu_btn).setVisibility(View.VISIBLE);
        //unhide the lists of cards
        mListCardRecyclerView.setVisibility(View.VISIBLE);
    }

    public String pushNewListCardToDatabase(String listName) {
        ListCard lst = new ListCard();
        lst.setListCardName(listName);
        return ListCard.createListCard(lst, UserInfo.getInstance().getCurrentBoard().getBoardID());
    }

    @Override
    public void onReceiveLists(Map<String, String> lists) {
        //add list
        for (Map.Entry<String, String> list: lists.entrySet()) {
            int position = mAdapter.addList(list.getValue(), list.getKey());
            //get the cards
            ListCard.getAllCardsInList(list.getKey(), position, this);
        }
    }

    @Override
    public void onCardsInListReceived(int position, Map<String, String> cards) {
        RecyclerView.ViewHolder holder = mListCardRecyclerView.findViewHolderForAdapterPosition(position);
        for (Map.Entry<String, String> card: cards.entrySet()) {
            mAdapter.addCardToList((ListCardAdapter.ListCardViewHolder) holder, position, card.getValue(), card.getKey());
        }
    }

    @Override
    public void onCardLoaded(Card card) {
        mCardFragment.loadCardData(card);
    }
}
