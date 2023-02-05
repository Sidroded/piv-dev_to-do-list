package com.sidroded.todolist.auth.passrecovery;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.sidroded.todolist.R;


public class Recovery extends AppCompatDialogFragment {
    private TextInputEditText userEmailEditText;
    private String userEmail;
    FirebaseAuth auth;
    private Recovery.RecoveryListener listener;
    private final String TAG = "Recovery";


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        auth = FirebaseAuth.getInstance();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_recovery, null);

        userEmailEditText = view.findViewById(R.id.recovery_set_email_edit_text);

        builder.setView(view)
                .setNegativeButton("скасувати", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("отримати листа", new DialogInterface.OnClickListener() { //тут инициализация имейла друга
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userEmail = userEmailEditText.getText().toString();
                        auth.sendPasswordResetEmail(userEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Email sent.");
                                        }
                                    }
                                });
                    }
                });

        return builder.create();
    }


    public interface RecoveryListener {
        void applyText(String friendEmail);
    }
}
