package com.mobile.assigment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mobile.assigment.presenter.ListCardAdapter;
import com.mobile.assigment.view.BoardMembersFragment;
import com.mobile.assigment.view.BoardSettingFragment;

import java.util.ArrayList;

public class BoardDisplayActivity extends AppCompatActivity {
    private static String EXTRA_MESSAGE = "com.mobile.assignment";
    private ArrayList<String> mListCardNames = new ArrayList<>();
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_display);
        mDrawerLayout = findViewById(R.id.board_display_drawer_layout);
        mNavigationView = findViewById(R.id.board_display_nav_view);
        mToolbar = findViewById(R.id.board_display_toolbar);

        Intent intent = getIntent();
        mBoardName = intent.getStringExtra(EXTRA_MESSAGE);
        setSupportActionBar(mToolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(mBoardName);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back);

        loadAllListsInBoard();
        setUpCardListRecyclerView();

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
        //TODO: implement loading from database in the future
        mListCardNames.add("To Do");
        mListCardNames.add("Doing");
        mListCardNames.add("Done");
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
                    mFragmentManager.popBackStack();
                    mListCardRecyclerView.setVisibility(View.VISIBLE);
                    findViewById(R.id.board_settings).setVisibility(View.VISIBLE);
                    mToolbar.setTitle(mBoardName);;
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
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
        mAdapter = new ListCardAdapter(mListCardNames);
        mAdapter.setParentActivity(BoardDisplayActivity.this);
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
}
