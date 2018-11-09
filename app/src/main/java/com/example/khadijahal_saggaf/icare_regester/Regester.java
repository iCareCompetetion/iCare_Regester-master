package com.example.khadijahal_saggaf.icare_regester;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Regester extends AppCompatActivity implements View.OnClickListener{

     static String name;
     static String useremail;
     static String username;
    EditText editTextName , editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView Loin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        editTextName = (EditText)findViewById(R.id.editText4);
        editTextEmail = (EditText)findViewById(R.id.editText3);
        editTextPassword = (EditText)findViewById(R.id.editText5);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.LogIn_Text).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!= null){
            //handle the already register
            //Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
        }
    }


    private void registerUser() {
        name = editTextName.getText().toString().trim();
        useremail = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        username=useremail.substring(0,useremail.indexOf('@'));



        if (name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if (useremail.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            editTextEmail.setError("Please enter a valid email ");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(useremail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    User user = new User(name,useremail,username);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"User Registered Successful",Toast.LENGTH_SHORT).show();

                                //start Home Activity after success SignUp
                                startActivity(new Intent(Regester.this,Home.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "no cannot add user as root", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    //Toast.makeText(getApplicationContext(),"Account Created Successful",Toast.LENGTH_SHORT).show();

                } else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static String getName(){
        return name;
    }
    public static String getEmail(){
        return useremail;
    }
    public static String getUserName(){
        return username;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                registerUser();
                break;

            case R.id.LogIn_Text:
                startActivity(new Intent(this,Login.class));
               break;
        }

    }
}
