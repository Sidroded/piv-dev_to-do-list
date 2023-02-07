package com.sidroded.todolist.friends;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;
import com.sidroded.todolist.auth.login.LoginLayout;

import java.util.Objects;

public class AddFriendDialog extends AppCompatDialogFragment {
    private TextInputEditText friendEmail;
    private String newFriend;
    FirebaseFirestore db;

    private AddFriendListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
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
                    //тут инициализация имейла друга
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newFriend = Objects.requireNonNull(friendEmail.getText()).toString();
                        class Lol{
                            String friend;

                            public String getFriend() {
                                return friend;
                            }
                            Lol(String friend){
                                this.friend =friend;
                            }
                        }
                        Lol ab= new Lol(newFriend.toString());

                        db.collection("friends"+MainActivity.getUser().getUser().getUid().toString()).add(ab);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                       //listener.applyText(newFriend);
                       //FriendsFragment.addFriend(newFriend);

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
