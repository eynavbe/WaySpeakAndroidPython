package com.eynav.wayspeack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    Button btnClinician,btnTreated;
    SharedPreferences shareType;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        btnClinician = findViewById(R.id.btnClinician);
        btnTreated = findViewById(R.id.btnTreated);
        btnClinician.setOnClickListener(l ->{
            // get or create SharedPreferences
            shareType = getSharedPreferences("type", MODE_PRIVATE);

            // save your string in SharedPreferences
            shareType.edit().putString("type", "Clinician").commit();

            if(user == null){
                startActivity(new Intent(MainActivity2.this, RegisterClinicianActivity.class));
            }
            else{
                startActivity(new Intent(MainActivity2.this, SingupClinicianActivity.class));

//                startActivity(new Intent(MainActivity2.this, SignupHallActivity.class));

            }
        });
        btnTreated.setOnClickListener(l ->{
            // get or create SharedPreferences
            shareType = getSharedPreferences("type", MODE_PRIVATE);

            // save your string in SharedPreferences
            shareType.edit().putString("type", "Patient").commit();


            if(user == null){
//                startActivity(new Intent(MainActivity2.this, RegisterTreatedActivity.class));
                startActivity(new Intent(MainActivity2.this, RegisterPatientActivity.class));

            }
            else{
                startActivity(new Intent(MainActivity2.this, SingupPatientActivity.class));

//                startActivity(new Intent(MainActivity2.this, MainActivity.class));

            }
        });
    }
}