package com.example.khadijahal_saggaf.icare_regester;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private static int SPLASH_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //firebase instance
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //if the user already logIn jump to the home screen
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(MainActivity.this,Home.class));

        //else , display start screen and move to logIn after miliseconds
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(MainActivity.this,Login.class);
                    startActivity(i);
                    finish();

                }
            },SPLASH_TIME_OUT);

        }
    }
}
