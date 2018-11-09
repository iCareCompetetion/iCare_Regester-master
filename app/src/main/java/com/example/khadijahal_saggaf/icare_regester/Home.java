package com.example.khadijahal_saggaf.icare_regester;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Home extends AppCompatActivity {

    TextView Account_name,Account_email,Account_username;
    FirebaseUser user;
    DatabaseReference reference;

    static String email ;
    static String id ;
    static String name ;
    static String username;


    Button signout,addPatient,blank_Page,patient_home,User_Account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        signout=findViewById(R.id.signOut);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home.this,Login.class));
            }
        });

        addPatient=findViewById(R.id.addPatient);
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home.this,Main2Activity.class));
            }
        });

        blank_Page=findViewById(R.id.button3);
        blank_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Blank.class));

            }
        });

        patient_home=findViewById(R.id.button4);
        patient_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Patient_Home.class));

            }
        });

        User_Account=findViewById(R.id.button5);
        User_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //retrive data
                Account_name = findViewById(R.id.textView_name);
                Account_email = findViewById(R.id.textView_email);
                Account_username = findViewById(R.id.textView_username);


                user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    email=user.getEmail();
                    id=user.getUid();

                    reference=FirebaseDatabase.getInstance().getReference("Users").child(id);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            name=dataSnapshot.child("name").getValue().toString();

                            username=dataSnapshot.child("username").getValue().toString();

                            Intent i=new Intent(Home.this,User_Account.class);
                            i.putExtra("name",name);
                            i.putExtra("email",email);
                            i.putExtra("username",username);

                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Home.this,"There is error while access your data",Toast.LENGTH_LONG).show();

                        }
                    });

                }//end retrive data

            }
        });

    }
}
