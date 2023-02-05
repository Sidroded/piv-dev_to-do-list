package com.sidroded.todolist.auth.register;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;

public class RegisterLayout extends AppCompatActivity {
    private static final String TAG = "MyApp";

    private EditText email_register;
    private EditText password_register;
    private Button button_register;
    private FirebaseAuth mAuth;
    ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        mAuth = FirebaseAuth.getInstance();

        email_register = findViewById(R.id.editTextTextEmailAddress);
        password_register = findViewById(R.id.editTextTextPassword);
        button_register = findViewById(R.id.register_btn);

        toolbar = getSupportActionBar();
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(androidx.appcompat.R.attr.colorAccent, typedValue, true);
        int titleColor = typedValue.data;

        toolbar.setTitle(Html.fromHtml("<font face = 'rubik-bold' color='" + titleColor + "'>To Do List</font>"));

        button_register.setOnClickListener(v -> {
            if (email_register.getText().toString().isEmpty() || password_register.getText().toString().isEmpty()) {
                Toast.makeText(RegisterLayout.this, "Заповніть всі поля", Toast.LENGTH_SHORT).show();
            } else if (password_register.getText().toString().length() < 6) {
                Toast.makeText(RegisterLayout.this, "Пароль має бути більше 6 символів", Toast.LENGTH_SHORT).show();
            } else if (!email_register.getText().toString().contains("@")) {
                Toast.makeText(RegisterLayout.this, "Введіть ваш email", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email_register.getText().toString(), password_register.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {

                                Intent intent = new Intent(RegisterLayout.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterLayout.this, "Ой, щось не так", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}