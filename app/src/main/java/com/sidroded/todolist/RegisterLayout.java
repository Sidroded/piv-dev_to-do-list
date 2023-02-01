package com.sidroded.todolist;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class RegisterLayout extends AppCompatActivity {
    private static final String TAG = "MyApp";

    private EditText email_register;
    private EditText password_register;
    private Button button_register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        mAuth = FirebaseAuth.getInstance();

        email_register = findViewById(R.id.editTextTextEmailAddress);
        password_register = findViewById(R.id.editTextTextPassword);
        button_register = findViewById(R.id.register_btn);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email_register.getText().toString().isEmpty() || password_register.getText().toString().isEmpty()||password_register.getText().length()>=6) {
                    Toast.makeText(RegisterLayout.this, "Fields cannot by empty", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email_register.getText().toString(), password_register.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Intent intent = new Intent(RegisterLayout.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterLayout.this, "Something wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

}