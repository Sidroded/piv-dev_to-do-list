package com.sidroded.todolist.user;

import com.google.firebase.auth.FirebaseUser;

public class User implements IUser {
    private final String email;
    private final String password;
    private final FirebaseUser user;

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
    public FirebaseUser getUser() {
        return user;
    }

}
