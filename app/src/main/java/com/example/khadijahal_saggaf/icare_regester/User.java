package com.example.khadijahal_saggaf.icare_regester;

public class User {

    public String name ,email,username;

    public User (){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email,String username) {
        this.name = name;
        this.email = email;
        this.username=username;
    }
}
