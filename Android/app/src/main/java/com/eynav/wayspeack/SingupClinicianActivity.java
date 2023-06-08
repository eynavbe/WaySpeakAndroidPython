package com.eynav.wayspeack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SingupClinicianActivity extends AppCompatActivity {
    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_clinician);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPass);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(view -> {
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view ->{
            startActivity(new Intent(SingupClinicianActivity.this, RegisterClinicianActivity.class));
        });
    }

    private void loginUser(){
        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            etLoginEmail.setError("Email cannot be empty");
            etLoginEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            etLoginPassword.setError("Password cannot be empty");
            etLoginPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SingupClinicianActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        readAllClinician(email);

                    }else{
                        Toast.makeText(SingupClinicianActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void readAllClinician(String email) {
        List<String> clinicianNames = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("clinician")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println("sjjsss");
                            System.out.println("task.getResult() "+task.getResult().size());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("document "+document.getId());
                                clinicianNames.add(document.getId());
//                                String email = String.valueOf(document.getData().get("email"));
//                                String name = String.valueOf(document.getData().get("name"));
//                                String clinicName = String.valueOf(document.getData().get("clinicName"));
//                                Long id = (Long)(document.getData().get("id"));
//
//                                if (emailGet.equals(email)){
//                                    Clinician clinician = new Clinician(email,name, clinicName,id);
//                                    SharedPreferences  mPrefs = getSharedPreferences("name", MODE_PRIVATE);
////                                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
//                                    Gson gson = new Gson();
//                                    String json = gson.toJson(clinician);
//                                    mPrefs.edit().putString("name", json).commit();
////                                    prefsEditor.commit();
////
////                                    SharedPreferences shareType = getSharedPreferences("name", MODE_PRIVATE);
////
////                                    // save your string in SharedPreferences
////                                    shareType.edit().putString("name", email).commit();
//
//
//
//
//                                    startActivity(new Intent(SingupClinicianActivity.this, ClinicianListPatients.class));
////                                clinicians.add(clinician);
//                                }

                            }
                            readData(email,clinicianNames,0);


                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }});


    }

    private void readData(String emailGet, List<String> clinicianNames, int i) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (clinicianNames.size() > i) {

            db.collection("clinician").document(clinicianNames.get(i)).collection(emailGet)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                    System.out.println("document " + document.getId());
                                    String email = String.valueOf(document.getData().get("email"));
                                    String name = String.valueOf(document.getData().get("name"));
                                    String clinicName = String.valueOf(document.getData().get("clinicName"));
                                    Long id = (Long) (document.getData().get("id"));

                                    if (emailGet.equals(email)) {
                                        Clinician clinician = new Clinician(email, name, clinicName, id);
                                        SharedPreferences mPrefs = getSharedPreferences("name", MODE_PRIVATE);
//                                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                        Gson gson = new Gson();
                                        String json = gson.toJson(clinician);
                                        mPrefs.edit().putString("name", json).commit();
//                                    prefsEditor.commit();
//
//                                    SharedPreferences shareType = getSharedPreferences("name", MODE_PRIVATE);
//
//                                    // save your string in SharedPreferences
//                                    shareType.edit().putString("name", email).commit();

                                        Intent clinicianOnePatient= new Intent(SingupClinicianActivity.this, ClinicianListPatients.class);
                                        clinicianOnePatient.putExtra("Clinician", clinician);
                                        startActivity(clinicianOnePatient);
//                                        startActivity(new Intent(SingupClinicianActivity.this, ClinicianListPatients.class));
//                                clinicians.add(clinician);
                                    }

                                }
                                if (task.getResult().size() == 0) {
                                    readData(emailGet, clinicianNames, i+1);
                                }

                            } else {
                                System.out.println("Error getting documents.");
                                readData(emailGet, clinicianNames, i+1);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            readData(emailGet, clinicianNames, i+1);

                        }
                    });
        }

    }


    }

