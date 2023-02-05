package com.sidroded.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.sidroded.todolist.friends.AddFriendDialog;
import com.sidroded.todolist.friends.FriendsFragment;
import com.sidroded.todolist.settings.SettingsFragment;
import com.sidroded.todolist.user.User;

public class MainActivity extends AppCompatActivity implements AddFriendDialog.AddFriendListener {
    public static User user;



    BottomNavigationView bottomNavigationView;
    CalendarFragment calendarFragment = new CalendarFragment();
    SettingsFragment settingFragment = new SettingsFragment();
    FriendsFragment friendsFragment = new FriendsFragment();
    ActionBar toolbar;
    NavController navCo;



    FirebaseAuth mAuth;
     static FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(androidx.appcompat.R.attr.colorAccent, typedValue, true);
        int titleColor = typedValue.data;

        toolbar.setTitle(Html.fromHtml("<font face = 'arial-bold' color='" + titleColor + "'>Ваші справи</font>"));


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navCo = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calendar_item:
                    navCo.navigate(R.id.calendar);
                    toolbar.setTitle(Html.fromHtml("<font face = 'arial-bold' color='" + titleColor + "'>Ваші справи</font>"));

                    return true;
                case R.id.friends_item:
                    navCo.navigate(R.id.friends);
                    toolbar.setTitle(Html.fromHtml("<font face = 'rubik-bold' color='" + titleColor + "'>Список учасників</font>"));

                    return true;
                case R.id.settings_item:
                    navCo.navigate(R.id.settings);
                    toolbar.setTitle(Html.fromHtml("<font face = 'rubik-bold' color='" + titleColor + "'>Налаштування</font>"));

                    return true;

            }
            return false;
        });

        mAuth = FirebaseAuth.getInstance();
    }
    public static User getUser() {
        return user;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
         currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            user = new User(currentUser.getEmail(), "", currentUser);
            currentUser.reload();
            navCo.navigate(R.id.calendar);

            //Toast.makeText(MainActivity.this, "Авторизація успішна", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginLayout.class);
            startActivity(intent);
        }
    }

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
            case R.id.calendar_main_ic:
                navCo.navigate(R.id.calendar);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //Никита тут ты получащь строку друга
    @Override
    public void applyText(String friendEmail) {

    }
}