package com.sidroded.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
    }

    public void checkRegister(View view) {
        EditText emailField = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText passwordField = (EditText) findViewById(R.id.editTextTextPassword);

        if (Objects.nonNull(emailField) && Objects.nonNull(passwordField)) {
            email = emailField.getText().toString();
            password = passwordField.getText().toString();


        } else {

        }

    }

}