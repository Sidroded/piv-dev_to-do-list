package com.sidroded.todolist.settings;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;
import com.sidroded.todolist.auth.login.LoginLayout;


public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth mAuth;
    FirebaseUser user;
    EditText newEmailEditText;
    Button setNewEmail;
    EditText newPasswordEditText;
    EditText newPasswordCheckEditText;
    Button setNewPassword;


    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();

        Button logout_button = view.findViewById(R.id.settings_log_out_button);
        newEmailEditText = view.findViewById(R.id.edit_text_view_edit_email);
        setNewEmail = view.findViewById(R.id.button_save_edit_email);
        newPasswordEditText = view.findViewById(R.id.edit_text_view_edit_password);
        newPasswordCheckEditText = view.findViewById(R.id.edit_text_check_edit_password);
        setNewPassword = view.findViewById(R.id.button_save_edit_password);
        logout_button.setOnClickListener(v -> {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();

            Intent intent = new Intent(getContext(), LoginLayout.class);
            startActivity(intent);
        });

        setNewEmail.setOnClickListener(v -> {
            if (!(newEmailEditText.getText().toString().isEmpty()) && (newEmailEditText.getText().toString().contains("@"))) {
                user.updateEmail(newEmailEditText.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User email address updated.");
                                Toast.makeText(getContext(), "Ваш email змінено", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Для того щоб змінити email заново пройдіть авторизацію", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Введіть ваш email", Toast.LENGTH_SHORT).show();
            }

        });

        setNewPassword.setOnClickListener(v -> {
            if (!(newPasswordEditText.getText().toString().isEmpty() && newPasswordCheckEditText.getText().toString().isEmpty()) &&
            newPasswordEditText.getText().toString().equals(newPasswordCheckEditText.getText().toString())) {

                user.updatePassword(newPasswordEditText.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User password updated.");
                                Toast.makeText(getContext(), "Пароль успішно змінено", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Для того щоб змінити пароль заново пройдіть авторизацію", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else if (newPasswordEditText.getText().toString().isEmpty() && newPasswordCheckEditText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Заповніть всі поля", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Паролі не співпадають", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        menu.clear();//например убрать все элементы меню.
    }
}