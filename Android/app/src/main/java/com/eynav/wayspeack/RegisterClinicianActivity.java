package com.eynav.wayspeack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterClinicianActivity extends AppCompatActivity {
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword, etRegClinicName, etRegName;
    TextView tvLoginHere;
    Button btnRegister;

    FirebaseAuth mAuth;
    Activity activity = this;
    Loading loadingdialog = new Loading(activity);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_clinician);
        etRegName = findViewById(R.id.etRegName);

        etRegClinicName = findViewById(R.id.etRegClinicName);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> {

            createUser();
        });

        tvLoginHere.setOnClickListener(view -> {
            startActivity(new Intent(RegisterClinicianActivity.this, SingupClinicianActivity.class));
        });
    }

    private void createUser() {
        String name = etRegName.getText().toString();
        String clinicName = etRegClinicName.getText().toString();
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            etRegName.setError("Full name cannot be empty");
            etRegName.requestFocus();
        } else if (TextUtils.isEmpty(clinicName)) {
            etRegClinicName.setError("Clinic name cannot be empty");
            etRegClinicName.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etRegPassword.setError("Password cannot be empty");
            etRegPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        loadingdialog.startLoadingdialog();

                        readIdClinician(name, clinicName, email);

                    } else {
                        Toast.makeText(RegisterClinicianActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            });
        }
    }

    private void readIdClinician(String name, String clinicName, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("idClinician").document("idClinician")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Long id = (Long) (document.getData().get("id"));
                                addToData(name, clinicName, email, id);

                            }
                        }
                    }
                });
    }

    private void updateIdClinician(Long id, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Long id2 = id + 1;
        db.collection("idClinician").document("idClinician")
                .update("id", id2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadingdialog.dismissdialog();
                        SharedPreferences shareType = getSharedPreferences("emailClinician", MODE_PRIVATE);
                        Toast.makeText(RegisterClinicianActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                        shareType.edit().putString("emailClinician", email).commit();

                        startActivity(new Intent(RegisterClinicianActivity.this, SingupClinicianActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private void addToData(String name, String clinicName, String email, Long id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> clinician = new HashMap<>();
        clinician.put("name", name);
        clinician.put("clinicName", clinicName);
        clinician.put("email", email);
        clinician.put("id", id);
        db.collection("clinician").document(clinicName).collection(email).document(email)
                .set(clinician)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot added");
                        addToData2(name, clinicName, email, id);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                        System.out.println(e.toString());
                    }
                });
    }


    private void addToData2(String name, String clinicName, String email, Long id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> clinician = new HashMap<>();
        clinician.put("id", id);
        db.collection("clinician").document(clinicName)
                .set(clinician)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot added");
                        updateIdClinician(id, email);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                        System.out.println(e.toString());
                    }
                });
    }

}