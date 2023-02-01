package com.sidroded.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sidroded.todolist.auth.login.LoginLayout;
import com.sidroded.todolist.user.User;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
User user;
BottomNavigationView bottomNavigationView;
CalendarFragment calendarFragment = new CalendarFragment();
SettingsFragment settingFragment = new SettingsFragment();
FriendsFragment friendsFragment = new FriendsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView =findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.calendar,calendarFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.calendar_item:
                    getSupportFragmentManager().beginTransaction().replace(R.id.calendar,calendarFragment).commit();
                    return true;
                case R.id.friends_item:
                    getSupportFragmentManager().beginTransaction().replace(R.id.calendar,friendsFragment).commit();
                    return true;
                case R.id.settings_item:
                    getSupportFragmentManager().beginTransaction().replace(R.id.calendar,settingFragment).commit();
                    return true;

            }
            return false;
        });

        mAuth = FirebaseAuth.getInstance();

    }

    public void lol(View view) {
        Intent intent = new Intent(this, LoginLayout.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            user=new User(currentUser.getEmail(),"",currentUser);
            currentUser.reload();

            Toast.makeText(MainActivity.this, "Авторизація успішна", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginLayout.class);
            startActivity(intent);
        }
    }
}