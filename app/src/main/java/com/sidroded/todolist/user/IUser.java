package com.sidroded.todolist.user;

import com.google.firebase.auth.FirebaseUser;

public interface IUser {
    String getEmail();
    String getPassword();
    FirebaseUser getUser();
}
