package com.eynav.wayspeack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eynav.wayspeack.ui.home.OneExerciseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SingupPatientActivity extends AppCompatActivity {
    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;
    Patient  patient = null;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_patient);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPass);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view ->{
            startActivity(new Intent(SingupPatientActivity.this, RegisterPatientActivity.class));
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
            mAuth.signInWithEmailAndPassword(email+"@patient.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SingupPatientActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        readAllClinician(email);
                        //                        readData(email);
                    }else{
                        Toast.makeText(SingupPatientActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

            db.collection("clinician").document(clinicianNames.get(i)).collection("Patients")
                    .whereEqualTo("IdentificationNumber", emailGet)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String dateBirth = String.valueOf(document.getData().get("dateBirth"));
                                    String gender = String.valueOf(document.getData().get("gender"));
                                    String dateMeeting = String.valueOf(document.getData().get("dateMeeting"));
                                    String hourMeeting = String.valueOf(document.getData().get("hourMeeting"));

                                    String prescribingClinician = String.valueOf(document.getData().get("prescribingClinician"));
                                    String IdentificationNumber = String.valueOf(document.getData().get("IdentificationNumber"));
                                    String clinicName = String.valueOf(document.getData().get("clinicName"));

                                     patient = new Patient(dateBirth,gender,dateMeeting,hourMeeting,prescribingClinician,IdentificationNumber,clinicName);
                                    SharedPreferences mPrefs = getSharedPreferences("name", MODE_PRIVATE);
//                                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(patient);
                                    mPrefs.edit().putString("name", json).commit();
                                    Intent clinicianOnePatient= new Intent(SingupPatientActivity.this, CameraActivity.class);

                                    clinicianOnePatient.putExtra("Patient", patient);

                                    startActivity(clinicianOnePatient);
//                                    clinicianOnePatient.putExtra("Patient", patient);
//                                    startActivity(new Intent(SingupPatientActivity.this, CameraActivity.class));

                                }
                                if (patient == null){
                                    readData(emailGet,clinicianNames,i+1);

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
    private void readClinician(String emailGet) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("clinician")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String email = String.valueOf(document.getData().get("email"));
                                String name = String.valueOf(document.getData().get("name"));
                                String clinicName = String.valueOf(document.getData().get("clinicName"));
                                Long id = (Long)(document.getData().get("id"));

                                if (emailGet.equals(email)){
                                    Clinician clinician = new Clinician(email,name, clinicName,id);
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




//                                    startActivity(new Intent(SingupPatientActivity.this, ClinicianListPatients.class));
//                                clinicians.add(clinician);
                                }

                            }

                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }});

    }
//    private void readData(String emailGet) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("clinician")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                String email = String.valueOf(document.getData().get("email"));
//                                String name = String.valueOf(document.getData().get("name"));
//                                String clinicName = String.valueOf(document.getData().get("clinicName"));
//                                Long id = (Long)(document.getData().get("id"));
//
//                                if (emailGet.equals(email)){
//                                    Clinician clinician = new Clinician(email,name, clinicName,id);
//                                    SharedPreferences mPrefs = getSharedPreferences("name", MODE_PRIVATE);
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
////                                    startActivity(new Intent(SingupPatientActivity.this, ClinicianListPatients.class));
////                                clinicians.add(clinician);
//                                }
//
//                            }
//
//                        } else {
//                            System.out.println("Error getting documents.");
//                        }
//                    }});
//
//    }

}