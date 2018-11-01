package com.example.khadijahal_saggaf.icare_regester;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener  {

    String gender;
    EditText editTextPatientName , editTextDescription, editTextAge;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextPatientName = (EditText)findViewById(R.id.editText_PatientName);
        editTextDescription = (EditText)findViewById(R.id.editText_description);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //the type of gender will be sent at sending data to firebase while floating clicked
               // Snackbar.make(view, gender, Snackbar.LENGTH_LONG)
                     //   .setAction("Action", null).show();
                registerPatient();
            }
        });

    }

    public void showList(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                gender="Male";
                return true;
            case R.id.item2:
                gender="Female";
                return true;
            default:
                return false;
        }
    }

    public void registerPatient(){
        final String patientName = editTextPatientName.getText().toString().trim();
        final String diseaseDescription = editTextDescription.getText().toString().trim();
        final String patientAge = editTextAge.getText().toString().trim();
        final String patientGender = gender;
        if (patientName.isEmpty()) {
            editTextPatientName.setError("Patient Name is required");
            editTextPatientName.requestFocus();
            return;
        }
        if (diseaseDescription.isEmpty()) {
            editTextDescription.setError("Description is required");
            editTextDescription.requestFocus();
            return;
        }

        if (patientAge.isEmpty()) {
            editTextAge.setError("Patient Age is required");
            editTextAge.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //String patientId = database.getReference().child("Patients").push().getKey();
        Patient patient = new Patient(patientName,diseaseDescription,patientAge,patientGender);
        FirebaseDatabase.getInstance().getReference("Patients")
                .child(FirebaseDatabase.getInstance().getReference().push().getKey())
                .setValue(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Patient Registered Successful",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}
