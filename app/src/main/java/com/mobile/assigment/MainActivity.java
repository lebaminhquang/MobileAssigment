package com.mobile.assigment;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mobile.assigment.authentication.LogInActivity;
import com.mobile.assigment.model.User;
import com.mobile.assigment.view.AboutFragment;
import com.mobile.assigment.view.BoardsFragment;
import com.mobile.assigment.view.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;
    private FirebaseAuth firebaseAuth;
    //fragments
    private BoardsFragment mBoardsFragment;
    private SettingsFragment mSettingsFragment;
    private AboutFragment mAboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        changeToCurrentUserInfo();

        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        //setting up action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //setting up navigation drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

        //set profile name and email
        View navheader = navigationView.getHeaderView(0);
        TextView profileName = navheader.findViewById(R.id.profile_name);
        TextView profileEmail = navheader.findViewById(R.id.profile_email);
        profileEmail.setText(UserInfo.getInstance().getEmail());
        profileName.setText(UserInfo.getInstance().getName());

        //create fragments
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        //add fragment showing boards as default
        mBoardsFragment = new BoardsFragment();
        mSettingsFragment = new SettingsFragment();
        mAboutFragment = new AboutFragment();

        fragmentTransaction.add(R.id.main_frame, mBoardsFragment);
        fragmentTransaction.commit();
    }

    public void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_boards:
                fragment = mBoardsFragment;
                break;
            case R.id.nav_settings:
                fragment = mSettingsFragment;
                break;
            case R.id.nav_about:
                fragment = mAboutFragment;
                break;
            case R.id.nav_logout:
                {
                    firebaseAuth.signOut();
                    startActivity(new Intent(MainActivity.this, LogInActivity.class));
                    finish();
                } break;
        }
        try {
            mFragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
            // Highlight the selected item has been done by NavigationView
            item.setChecked(true);
            // Set action bar title
            setTitle(item.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeToCurrentUserInfo() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        String email = firebaseAuth.getCurrentUser().getEmail();
        UserInfo.getInstance().setUserEmail(email);
        UserInfo.getInstance().setUserName(email.split("@")[0]);
        UserInfo.getInstance().setId(userId);
    }
}
