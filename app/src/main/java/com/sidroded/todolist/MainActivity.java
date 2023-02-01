package com.sidroded.todolist;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sidroded.todolist.auth.login.LoginLayout;
import com.sidroded.todolist.user.User;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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