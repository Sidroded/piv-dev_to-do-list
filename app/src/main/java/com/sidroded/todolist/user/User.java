package com.sidroded.todolist.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User implements IUser {
    String email;
    String password;
    FirebaseAuth user;

    public User(String email, String password, FirebaseUser user) {
        this.email = email;
        this.password = password;
        this.user = user;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public FirebaseAuth getUser() {
        return user;
    }

}
