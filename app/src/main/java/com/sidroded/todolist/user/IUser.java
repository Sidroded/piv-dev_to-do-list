package com.sidroded.todolist.user;

import com.google.firebase.auth.FirebaseAuth;

public interface IUser {
    String getEmail();
    String getPassword();
    FirebaseAuth getUser();
}
