package com.sidroded.todolist.friends;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


import com.google.android.material.textfield.TextInputEditText;
import com.sidroded.todolist.R;

public class AddFriendDialog extends AppCompatDialogFragment  {
    private TextInputEditText friendEmail;
    private String newFriend;
    private AddFriendListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_friend, null);

        friendEmail = view.findViewById(R.id.add_friend_set_email_edit_text);

        builder.setView(view)
                .setNegativeButton("скасувати", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("додати", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newFriend = friendEmail.getText().toString();
                        listener.applyText(newFriend);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddFriendListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }

    }


    public interface AddFriendListener {
        void applyText(String friendEmail);
    }
}
