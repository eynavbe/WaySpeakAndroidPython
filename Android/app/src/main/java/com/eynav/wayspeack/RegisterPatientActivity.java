package com.eynav.wayspeack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class RegisterPatientActivity extends AppCompatActivity {
    TextInputEditText etRegEmail,etRegClinicName;
    TextInputEditText etRegPassword;
    TextView tvLoginHere;
    Button btnRegister;
    Patient patient = null;

    FirebaseAuth mAuth;
    Activity activity = this;
    Loading loadingdialog = new Loading(activity);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);

        etRegEmail = findViewById(R.id.etRegEmailPatient);
        etRegPassword = findViewById(R.id.etRegPassPatient);
        tvLoginHere = findViewById(R.id.tvLoginHerePatient);
        btnRegister = findViewById(R.id.btnRegisterPatient);
        etRegClinicName = findViewById(R.id.etRegClinicNamePatient);
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> {

            createUser();
        });

        tvLoginHere.setOnClickListener(view -> {
            startActivity(new Intent(RegisterPatientActivity.this, SingupPatientActivity.class));
        });
    }

    private void createUser() {

        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();
        String clinicName = etRegClinicName.getText().toString();

        if (TextUtils.isEmpty(clinicName)) {
            etRegClinicName.setError("Clinic name cannot be empty");
            etRegClinicName.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etRegPassword.setError("Password cannot be empty");
            etRegPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email+"@patient.com", password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        loadingdialog.startLoadingdialog();

                        readIdClinician(email,clinicName);

                    } else {
                        Toast.makeText(RegisterPatientActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            });
        }
    }

    private void readIdClinician(String email, String clinicName) {
        System.out.println("email "+email);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("clinician").document(clinicName).collection("Patients")
                    .whereEqualTo("IdentificationNumber", email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("TAG", document.getId() + " => " + document.getData());
                                    loadingdialog.dismissdialog();
                                    String dateBirth = String.valueOf(document.getData().get("dateBirth"));
                                    String gender = String.valueOf(document.getData().get("gender"));
                                    String dateMeeting = String.valueOf(document.getData().get("dateMeeting"));
                                    String hourMeeting = String.valueOf(document.getData().get("hourMeeting"));

                                    String prescribingClinician = String.valueOf(document.getData().get("prescribingClinician"));
                                    String IdentificationNumber = String.valueOf(document.getData().get("IdentificationNumber"));
                                    String clinicName = String.valueOf(document.getData().get("clinicName"));

                                    patient = new Patient(dateBirth,gender,dateMeeting,hourMeeting,prescribingClinician,IdentificationNumber,clinicName);

                                }
                                if (patient == null){
                                    loadingdialog.dismissdialog();
                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    user.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("TAG", "User account deleted.");
                                                        loadingdialog.dismissdialog();
                                                        Toast.makeText(RegisterPatientActivity.this, "Error: The details entered are incorrect", Toast.LENGTH_SHORT).show();

                                                    }else {
                                                        Toast.makeText(RegisterPatientActivity.this, "Error: The details entered are incorrect", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }else {
                                    startActivity(new Intent(RegisterPatientActivity.this, SingupPatientActivity.class));
                                    finish();
                                }
                            } else {
                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("TAG", "User account deleted.");
                                                    loadingdialog.dismissdialog();
                                                    Toast.makeText(RegisterPatientActivity.this, "Error: The details entered are incorrect", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(RegisterPatientActivity.this, "Error: The details entered are incorrect", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("TAG", "User account deleted.");
                                                loadingdialog.dismissdialog();
                                                Toast.makeText(RegisterPatientActivity.this, "Error: The details entered are incorrect", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(RegisterPatientActivity.this, "Error: The details entered are incorrect", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    });



    }

}