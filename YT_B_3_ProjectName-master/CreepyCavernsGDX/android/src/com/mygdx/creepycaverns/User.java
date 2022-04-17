package com.mygdx.creepycaverns;

/**
 * Created by Brennyn on 9/28/2017.
 */

public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
