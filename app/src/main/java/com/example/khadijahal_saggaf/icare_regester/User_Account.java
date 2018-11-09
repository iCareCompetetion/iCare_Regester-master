package com.example.khadijahal_saggaf.icare_regester;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Account extends AppCompatActivity {
    TextView Account_name,Account_email,Account_username;
    FirebaseUser user;
    DatabaseReference reference;

    String email ;
    String id ;
    String name ;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Account_name = findViewById(R.id.textView_name);
        Account_email = findViewById(R.id.textView_email);
        Account_username = findViewById(R.id.textView_username);


        Intent i=getIntent();

        Account_name.setText(i.getStringExtra("name"));
        Account_username.setText(i.getStringExtra("username"));
        Account_email.setText(i.getStringExtra("email"));

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home); //replace with intent home screen
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);//replace with intent my patient list screen
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications); //replace with intent notifications list screen
                    return true;
            }
            return false;
        }
    };


}
