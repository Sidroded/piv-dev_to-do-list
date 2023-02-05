package com.sidroded.todolist.auth.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;
import com.sidroded.todolist.auth.passrecovery.Recovery;
import com.sidroded.todolist.auth.register.RegisterLayout;
import com.sidroded.todolist.friends.AddFriendDialog;


public class LoginLayout extends AppCompatActivity {

    private EditText email_login;
    private EditText password_login;
    private Button button_login;
    private TextView text_register;
    private TextView recovery_text;
    private FirebaseAuth mAuth;
    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        mAuth = FirebaseAuth.getInstance();
        email_login = findViewById(R.id.editTextTextEmailAddressLogin);
        password_login = findViewById(R.id.editTextTextPasswordLogin);
        button_login = findViewById(R.id.login_btn);
        text_register = findViewById(R.id.textRegister);
        recovery_text = findViewById(R.id.login_recovery_password_text_view);

        toolbar = getSupportActionBar();
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(androidx.appcompat.R.attr.colorAccent, typedValue, true);
        int titleColor = typedValue.data;

        toolbar.hide();

        text_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginLayout.this, RegisterLayout.class);
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email_login.getText().toString().isEmpty() || password_login.getText().toString().isEmpty()) {
                    Toast.makeText(LoginLayout.this, "Заповніть всі поля", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email_login.getText().toString(), password_login.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(LoginLayout.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginLayout.this, "Невірний імейл або пароль", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        recovery_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        Recovery recovery = new Recovery();
        recovery.show(getSupportFragmentManager(), "Оновити пароль");
    }
}