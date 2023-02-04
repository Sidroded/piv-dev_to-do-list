package com.sidroded.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sidroded.todolist.auth.login.LoginLayout;
import com.sidroded.todolist.calendar.CalendarFragment;
import com.sidroded.todolist.friends.FriendsFragment;
import com.sidroded.todolist.settings.SettingsFragment;
import com.sidroded.todolist.user.User;

public class MainActivity extends AppCompatActivity {
    User user;

    BottomNavigationView bottomNavigationView;
    CalendarFragment calendarFragment = new CalendarFragment();
    SettingsFragment settingFragment = new SettingsFragment();
    FriendsFragment friendsFragment = new FriendsFragment();
    Toolbar toolbar;
    NavController navCo;
     FirebaseAuth mAuth;
    static FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navCo = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calendar_item:
                    navCo.navigate(R.id.calendar);
                    toolbar.setTitle(R.string.calendar_fragment_text);

                    return true;
                case R.id.friends_item:
                    navCo.navigate(R.id.friends);
                    toolbar.setTitle(R.string.friends_fragment_tool_text);

                    return true;
                case R.id.settings_item:
                    navCo.navigate(R.id.settings);
                    toolbar.setTitle(R.string.settings_fragment_tool_text);

                    return true;

            }
            return false;
        });

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
         currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            user = new User(currentUser.getEmail(), "", currentUser);
            currentUser.reload();

            //Toast.makeText(MainActivity.this, "Авторизація успішна", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginLayout.class);
            startActivity(intent);
        }
    }
    public static FirebaseUser getUser(){return currentUser;}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar_item_ic:
                navCo.navigate(R.id.calendarCellsFragment);
                break;
            case R.id.filter_item_ic:
                Toast.makeText(this, "Хули тыкашь мразь а?", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}