package com.eynav.wayspeack.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eynav.wayspeack.Clinician;
import com.eynav.wayspeack.MainActivity;
import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddExerciseActivity extends AppCompatActivity {
    Button btnSaveExercise, btnCancelationExercise;
    Patient patient;
    Exercise exercise_pre;
    EditText etDateStart, etDateEnd;
    EditText stIndexOpen, etCountOpen;
    EditText stIndexClose,etCountClose;
    EditText stIndexSmile, etCountSmile;
    EditText stIndexPursing, etCountPursing;
    EditText stIndexTongueOut, etCountTongueOut;
    EditText stIndexMoveTongueRight, etCountMoveTongueRight;
    EditText stIndexMoveTongueLeft, etCountMoveTongueLeft;
    EditText stIndexLiftTongueNose, etCountLiftTongueNose;
    EditText stIndexLowerTongueChin, etCountLowerTongueChin;
    TextInputLayout eilDateStart, eilDateEnd;
    Context context = this;
    String dateAdd = new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime());
    Clinician clinician;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_exercise);
        findView();

        patient = (Patient) this.getIntent().getParcelableExtra("Patient");
        clinician = (Clinician) this.getIntent().getParcelableExtra("Clinician");

        exercise_pre = (Exercise) this.getIntent().getParcelableExtra("Exercise");
        if (exercise_pre  != null){
            etDateStart.setText(exercise_pre.getDateStart());
            etDateEnd.setText(exercise_pre.getDateEnd());
            for (TypeExercise typeExercise : exercise_pre.getTypeExercises()) {
                if (typeExercise.getType().equals("mouth_open")){
                    etCountOpen.setText(typeExercise.getCount());
                    stIndexOpen.setText(typeExercise.getIndex());
                }
                if (typeExercise.getType().equals("mouth_close")){
                    etCountClose.setText(typeExercise.getCount());
                    stIndexClose.setText(typeExercise.getIndex());
                }
                if (typeExercise.getType().equals("smile")){
                    etCountSmile.setText(typeExercise.getCount());
                    stIndexSmile.setText(typeExercise.getIndex());
                }
                if (typeExercise.getType().equals("lip_pursing")){
                    etCountPursing.setText(typeExercise.getCount());
                    stIndexPursing.setText(typeExercise.getIndex());
                }
                if (typeExercise.getType().equals("straight_tongue_out")){
                    etCountTongueOut.setText(typeExercise.getCount());
                    stIndexTongueOut.setText(typeExercise.getIndex());
                }
                if (typeExercise.getType().equals("move_tongue_to_right")){
                    etCountMoveTongueRight.setText(typeExercise.getCount());
                    stIndexMoveTongueRight.setText(typeExercise.getIndex());
                }
                if (typeExercise.getType().equals("Move_tongue_to_left")){
                    etCountMoveTongueLeft.setText(typeExercise.getCount());
                    stIndexMoveTongueLeft.setText(typeExercise.getIndex());
                }
                if (typeExercise.getType().equals("lift_tongue_to_nose")){
                    etCountLiftTongueNose.setText(typeExercise.getCount());
                    stIndexLiftTongueNose.setText(typeExercise.getIndex());
                }
                if (typeExercise.getType().equals("down_tongue_to_chin")){
                    etCountLowerTongueChin.setText(typeExercise.getCount());
                    stIndexLowerTongueChin.setText(typeExercise.getIndex());
                }
            }

        }
        btnSaveExercise.setOnClickListener(l ->{
            if (exercise_pre == null){
                if (etDateStart.getText().toString().equals("")){
                    eilDateStart.setErrorEnabled(true);
                    eilDateStart.setError("Please put a start date");
                }else {
                    eilDateStart.setErrorEnabled(false);
                    eilDateStart.setError("");
                    if (etDateEnd.getText().toString().equals("")){
                        eilDateEnd.setErrorEnabled(true);
                        eilDateEnd.setError("Please put a end date");
                    }else {
                        eilDateEnd.setErrorEnabled(false);
                        eilDateEnd.setError("");
                        Exercise exercise = getExternalInformation();
                        if (exercise.getTypeExercises().size() == 0){
                            Toast.makeText(this, "no exercises are selected", Toast.LENGTH_SHORT).show();
                        }else {
                            addExercisesToFirebase(exercise);

                        }

                    }
                }
            }else {
                if (etDateStart.getText().toString().equals("")){
                    eilDateStart.setErrorEnabled(true);
                    eilDateStart.setError("Please put a start date");
                }else {
                    eilDateStart.setErrorEnabled(false);
                    eilDateStart.setError("");
                    if (etDateEnd.getText().toString().equals("")){
                        eilDateEnd.setErrorEnabled(true);
                        eilDateEnd.setError("Please put a end date");
                    }else {
                        eilDateEnd.setErrorEnabled(false);
                        eilDateEnd.setError("");
                        if (etDateStart.getText().toString().equals(exercise_pre.getDateStart())){
                            dateAdd = exercise_pre.getDateAdd();
                            Exercise exercise = getExternalInformation();
                            if (exercise.getTypeExercises().size() == 0){
                                Toast.makeText(this, "no exercises are selected", Toast.LENGTH_SHORT).show();
                            }else {
                                addExercisesToFirebase(exercise);

                            }
                        }else {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            String dateStartF = exercise_pre.getDateStart().replace("/", "");
                            db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                                    .document(dateStartF)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Exercise exercise = getExternalInformation();
                                            if (exercise.getTypeExercises().size() == 0){
                                                Toast.makeText(context, "no exercises are selected", Toast.LENGTH_SHORT).show();
                                            }else {
                                                for (TypeExercise typeExercise: exercise_pre.getTypeExercises()) {


                                                db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                                                        .document(dateStartF).collection("Type").document(typeExercise.getType()+typeExercise.getCount())
                                                        .delete()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {


                                                            }


                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                System.out.println("Error adding document");
                                                            }
                                                        });


                                                }
                                                addExercisesToFirebase(exercise);

                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Exercise exercise = getExternalInformation();
                                            if (exercise.getTypeExercises().size() == 0){
                                                Toast.makeText(context, "no exercises are selected", Toast.LENGTH_SHORT).show();
                                            }else {
                                                addExercisesToFirebase(exercise);

                                            }
                                        }
                                    });

                        }


                    }
                }
            }




        });
        btnCancelationExercise.setOnClickListener(l ->{
            Intent clinicianOnePatient= new Intent(this, MainActivity.class);
            clinicianOnePatient.putExtra("Patient", patient);
            clinicianOnePatient.putExtra("Clinician", clinician);
            this.startActivity(clinicianOnePatient);
        });

        etDateStart.setOnClickListener(k -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                // set day of month , month and year value in the edit text
                String monthOfYear1 = "", dayOfMonth1 = "";
                if ((monthOfYear + 1) < 10){
                    monthOfYear1 = "0" + String.valueOf(monthOfYear + 1);
                }else{
                    monthOfYear1 = String.valueOf(monthOfYear + 1);
                }
                if (dayOfMonth < 10){
                    dayOfMonth1 = "0" + String.valueOf(dayOfMonth);
                }else{
                    dayOfMonth1 = String.valueOf(dayOfMonth);
                }
                String dateMeeting = dayOfMonth1 + "/" + monthOfYear1 + "/" + year;
                etDateStart.setText(dateMeeting);
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        etDateEnd.setOnClickListener(k -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                // set day of month , month and year value in the edit text
                String monthOfYear1 = "", dayOfMonth1 = "";
                if ((monthOfYear + 1) < 10){
                    monthOfYear1 = "0" + String.valueOf(monthOfYear + 1);
                }else{
                    monthOfYear1 = String.valueOf(monthOfYear + 1);
                }
                if (dayOfMonth < 10){
                    dayOfMonth1 = "0" + String.valueOf(dayOfMonth);
                }else{
                    dayOfMonth1 = String.valueOf(dayOfMonth);
                }
                String dateMeeting = dayOfMonth1 + "/" + monthOfYear1 + "/" + year;
                etDateEnd.setText(dateMeeting);
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }

    private Exercise getExternalInformation() {
        String dateStart = etDateStart.getText().toString();
        String dateEnd = etDateEnd.getText().toString();
        List<TypeExercise> typeExercises = new ArrayList<>();
        if (!(etCountOpen.getText().toString().equals("0") || etCountOpen.getText().toString().equals(""))){
            TypeExercise typeExercise = new TypeExercise("mouth_open",etCountOpen.getText().toString(),stIndexOpen.getText().toString(),"0");
            if (stIndexOpen.getText().toString().equals("0") || stIndexOpen.getText().toString().equals(""))
            {
                typeExercise = new TypeExercise("mouth_open",etCountOpen.getText().toString(),"0","0");
            }
            typeExercises.add(typeExercise);
        }
        if (!(etCountClose.getText().toString().equals("0") || etCountClose.getText().toString().equals(""))){
            TypeExercise typeExercise = new TypeExercise("mouth_close",etCountClose.getText().toString(),stIndexClose.getText().toString(),"0");
            if (stIndexClose.getText().toString().equals("0") || stIndexClose.getText().toString().equals(""))
            {
                 typeExercise = new TypeExercise("mouth_close",etCountClose.getText().toString(),"0","0");

            }
            typeExercises.add(typeExercise);
        }

        if (!(etCountSmile.getText().toString().equals("0") || etCountSmile.getText().toString().equals(""))){
            TypeExercise typeExercise = new TypeExercise("smile",etCountSmile.getText().toString(),stIndexSmile.getText().toString(),"0");
            if (stIndexSmile.getText().toString().equals("0") || stIndexSmile.getText().toString().equals(""))
            {
                typeExercise = new TypeExercise("smile",etCountSmile.getText().toString(),"0","0");

            }
            typeExercises.add(typeExercise);
        }

        if (!(etCountPursing.getText().toString().equals("0") || etCountPursing.getText().toString().equals(""))){
            TypeExercise typeExercise = new TypeExercise("lip_pursing",etCountPursing.getText().toString(),stIndexPursing.getText().toString(),"0");
            if (stIndexPursing.getText().toString().equals("0") || stIndexPursing.getText().toString().equals(""))
            {
                 typeExercise = new TypeExercise("lip_pursing",etCountPursing.getText().toString(),"0","0");

            }
            typeExercises.add(typeExercise);
        }

        if (!(etCountTongueOut.getText().toString().equals("0") || etCountTongueOut.getText().toString().equals(""))){
            TypeExercise typeExercise = new TypeExercise("straight_tongue_out",etCountTongueOut.getText().toString(),stIndexTongueOut.getText().toString(),"0");
            if (stIndexTongueOut.getText().toString().equals("0") || stIndexTongueOut.getText().toString().equals(""))
            {
                 typeExercise = new TypeExercise("straight_tongue_out",etCountTongueOut.getText().toString(),"0","0");

            }
            typeExercises.add(typeExercise);
        }

        if (!(etCountMoveTongueRight.getText().toString().equals("0") || etCountMoveTongueRight.getText().toString().equals(""))){
            TypeExercise typeExercise = new TypeExercise("move_tongue_to_right",etCountMoveTongueRight.getText().toString(),stIndexMoveTongueRight.getText().toString(),"0");
            if (stIndexMoveTongueRight.getText().toString().equals("0") || stIndexMoveTongueRight.getText().toString().equals(""))
            {
                 typeExercise = new TypeExercise("move_tongue_to_right",etCountMoveTongueRight.getText().toString(),"0","0");

            }
            typeExercises.add(typeExercise);
        }

        if (!(etCountMoveTongueLeft.getText().toString().equals("0") || etCountMoveTongueLeft.getText().toString().equals(""))){
            TypeExercise typeExercise = new TypeExercise("Move_tongue_to_left",etCountMoveTongueLeft.getText().toString(),stIndexMoveTongueLeft.getText().toString(),"0");
            if (stIndexMoveTongueLeft.getText().toString().equals("0") || stIndexMoveTongueLeft.getText().toString().equals(""))
            {
                 typeExercise = new TypeExercise("Move_tongue_to_left",etCountMoveTongueLeft.getText().toString(),"0","0");

            }
            typeExercises.add(typeExercise);
        }

        if (!(etCountLiftTongueNose.getText().toString().equals("0") || etCountLiftTongueNose.getText().toString().equals(""))){
            TypeExercise typeExercise = new TypeExercise("lift_tongue_to_nose",etCountLiftTongueNose.getText().toString(),stIndexLiftTongueNose.getText().toString(),"0");
            if (stIndexLiftTongueNose.getText().toString().equals("0") || stIndexLiftTongueNose.getText().toString().equals(""))
            {
                 typeExercise = new TypeExercise("lift_tongue_to_nose",etCountLiftTongueNose.getText().toString(),"0","0");

            }
            typeExercises.add(typeExercise);
        }

        if (!(etCountLowerTongueChin.getText().toString().equals("0") || etCountLowerTongueChin.getText().toString().equals(""))){
            TypeExercise typeExercise = new TypeExercise("down_tongue_to_chin",etCountLowerTongueChin.getText().toString(),stIndexLowerTongueChin.getText().toString(),"0");
            if (stIndexLowerTongueChin.getText().toString().equals("0") || stIndexLowerTongueChin.getText().toString().equals(""))
            {
                 typeExercise = new TypeExercise("down_tongue_to_chin",etCountLowerTongueChin.getText().toString(),"0","0");

            }
            typeExercises.add(typeExercise);
        }

        Exercise exercise = new Exercise(dateStart,dateEnd,dateAdd,typeExercises);
        System.out.println(exercise);
        return exercise;



    }

    private void findView() {
        btnSaveExercise = findViewById(R.id.btnSaveExercise);
        btnCancelationExercise = findViewById(R.id.btnCancelationExercise);
        etDateStart= findViewById(R.id.etDateStart);
        etDateEnd= findViewById(R.id.etDateEnd);
        stIndexOpen= findViewById(R.id.stIndexOpen);
        etCountOpen= findViewById(R.id.etCountOpen);
        stIndexClose= findViewById(R.id.stIndexClose);
        etCountClose= findViewById(R.id.etCountClose);
        stIndexSmile= findViewById(R.id.stIndexSmile);
        etCountSmile= findViewById(R.id.etCountSmile);
        stIndexPursing= findViewById(R.id.stIndexPursing);
        etCountPursing= findViewById(R.id.etCountPursing);
        stIndexTongueOut= findViewById(R.id.stIndexTongueOut);
        etCountTongueOut= findViewById(R.id.etCountTongueOut);
        stIndexMoveTongueRight= findViewById(R.id.stIndexMoveTongueRight);
        etCountMoveTongueRight= findViewById(R.id.etCountMoveTongueRight);
        stIndexMoveTongueLeft= findViewById(R.id.stIndexMoveTongueLeft);
        etCountMoveTongueLeft= findViewById(R.id.etCountMoveTongueLeft);
        stIndexLiftTongueNose= findViewById(R.id.stIndexLiftTongueNose);
        etCountLiftTongueNose= findViewById(R.id.etCountLiftTongueNose);
        stIndexLowerTongueChin= findViewById(R.id.stIndexLowerTongueChin);
        etCountLowerTongueChin= findViewById(R.id.etCountLowerTongueChin);
        eilDateStart = findViewById(R.id.eilDateStart);
        eilDateEnd = findViewById(R.id.eilDateEnd);

    }

    private void addExercisesToFirebase(Exercise exercise) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> Exercise = new HashMap<>();
        Exercise.put("dateAdd", exercise.getDateAdd());
        Exercise.put("dateStart", exercise.getDateStart());
        Exercise.put("dateEnd", exercise.getDateEnd());
        String dateStartF = exercise.getDateStart().replace("/", "");

        db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                .document(dateStartF)
                .set(Exercise)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        addExercisesTypeToFirebase(exercise,0);
//                        loadingdialog.dismissdialog();

                    }


                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });

    }
    private void addExercisesTypeToFirebase(Exercise exercise,int index) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (index < exercise.getTypeExercises().size()){
            TypeExercise typeExercise = exercise.getTypeExercises().get(index);
            Map<String, Object> Exercise = new HashMap<>();
            Exercise.put("type", typeExercise.getType());
            Exercise.put("count", typeExercise.getCount());
            Exercise.put("index", typeExercise.getIndex());
            Exercise.put("countDo", typeExercise.getCountDo());

            String dateStartF = exercise.getDateStart().replace("/", "");

            db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                    .document(dateStartF).collection("Type").document(typeExercise.getType()+typeExercise.getCount())
                    .set(Exercise)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            addExercisesTypeToFirebase(exercise,index+1);
//                        loadingdialog.dismissdialog();

                        }


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Error adding document");
                        }
                    });


        }else {
            Intent clinicianOnePatient= new Intent(this, MainActivity.class);
            clinicianOnePatient.putExtra("Patient", patient);
            clinicianOnePatient.putExtra("Clinician", clinician);
            this.startActivity(clinicianOnePatient);
        }

    }
}