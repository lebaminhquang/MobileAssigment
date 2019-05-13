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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.assigment.authentication.LogInActivity;
import com.mobile.assigment.model.User;
import com.mobile.assigment.view.AboutFragment;
import com.mobile.assigment.view.BoardsFragment;
import com.mobile.assigment.view.SettingsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;
    private FirebaseAuth firebaseAuth;
    //fragments
    private BoardsFragment mBoardsFragment;
    private SettingsFragment mSettingsFragment;
    private AboutFragment mAboutFragment;

    //  Real-time database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        changeToCurrentUserInfo();

        getAllUserList();

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
        TextView profileAvatar = navheader.findViewById(R.id.profile_avatar);
        profileEmail.setText(UserInfo.getInstance().getEmail());
        profileName.setText(UserInfo.getInstance().getName().toUpperCase());
        profileAvatar.setText(UserInfo.getInstance().getName().substring(0,1).toUpperCase());

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

    public void getAllUserList() {
        // Read from the database
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<User> listUser = new ArrayList<>();
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot ds : iterable) {
                    User user = ds.getValue(User.class);
                    listUser.add(user);
                }
                UserList.getInstance().setAllUsers(listUser);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
