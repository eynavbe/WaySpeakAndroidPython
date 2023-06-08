package com.eynav.wayspeack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eynav.wayspeack.ui.home.Exercise;
import com.eynav.wayspeack.ui.home.ExercisesAdapter;
import com.eynav.wayspeack.ui.home.Experience;
import com.eynav.wayspeack.ui.home.TypeExercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePatient extends AppCompatActivity {
    Patient patient;
    Exercise exercise;
    GridLayout mainGrid;
    TextView tvDayForProgram;
    LinearProgressIndicator lpiDayForProgram;
    CardView cvExecution1,cvExecution2,cvExecution3,cvExecution4,cvExecution5,cvExecution6,cvExecution7,cvExecution8,cvExecution9;
    TextView tvExecutionQuantity1,tvNameExercise1;
    TypeExercise type1 , type2 , type3 , type4 , type5 , type6 , type7 , type8, type9;
    TextView tvExecutionQuantity2,tvNameExercise2;
    TextView tvExecutionQuantity3,tvNameExercise3;
    TextView tvExecutionQuantity4,tvNameExercise4;
    TextView tvExecutionQuantity5,tvNameExercise5;
    TextView tvExecutionQuantity6,tvNameExercise6;
    TextView tvExecutionQuantity7,tvNameExercise7;
    TextView tvExecutionQuantity8,tvNameExercise8;
    TextView tvExecutionQuantity9,tvNameExercise9;
    int countProgram = 0, countDoProgram = 0;
    List<TypeExercise> typeExerciseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_patient);
        findById();

        patient = (Patient) getIntent().getParcelableExtra("Patient");
        readExercisesFromFirebase(patient);
        //Set Event
        //        setToggleEvent(mainGrid);

        setSingleEvent(mainGrid);

    }

    private void findById() {
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        tvDayForProgram = findViewById(R.id.tvDayForProgram);
        lpiDayForProgram = findViewById(R.id.lpiDayForProgram);
        cvExecution1 = findViewById(R.id.cvExecution1);
        cvExecution2 = findViewById(R.id.cvExecution2);
        cvExecution3 = findViewById(R.id.cvExecution3);
        cvExecution4 = findViewById(R.id.cvExecution4);
        cvExecution5 = findViewById(R.id.cvExecution5);
        cvExecution6 = findViewById(R.id.cvExecution6);
        cvExecution7 = findViewById(R.id.cvExecution7);
        cvExecution8 = findViewById(R.id.cvExecution8);
        cvExecution9 = findViewById(R.id.cvExecution9);
        tvExecutionQuantity1 = findViewById(R.id.tvExecutionQuantity1);
        tvNameExercise1 = findViewById(R.id.tvNameExercise1);
        tvExecutionQuantity2 = findViewById(R.id.tvExecutionQuantity2);
        tvNameExercise2 = findViewById(R.id.tvNameExercise2);
        tvExecutionQuantity3 = findViewById(R.id.tvExecutionQuantity3);
        tvNameExercise3 = findViewById(R.id.tvNameExercise3);
        tvExecutionQuantity4 = findViewById(R.id.tvExecutionQuantity4);
        tvNameExercise4 = findViewById(R.id.tvNameExercise4);
        tvExecutionQuantity5 = findViewById(R.id.tvExecutionQuantity5);
        tvNameExercise5 = findViewById(R.id.tvNameExercise5);
        tvExecutionQuantity6 = findViewById(R.id.tvExecutionQuantity6);
        tvNameExercise6 = findViewById(R.id.tvNameExercise6);
        tvExecutionQuantity7 = findViewById(R.id.tvExecutionQuantity7);
        tvNameExercise7 = findViewById(R.id.tvNameExercise7);
        tvExecutionQuantity8 = findViewById(R.id.tvExecutionQuantity8);
        tvNameExercise8 = findViewById(R.id.tvNameExercise8);
        tvExecutionQuantity9 = findViewById(R.id.tvExecutionQuantity9);
        tvNameExercise9 = findViewById(R.id.tvNameExercise9);


    }

    private void setToggleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(HomePatient.this, "State : True", Toast.LENGTH_SHORT).show();

                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(HomePatient.this, "State : False", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);

            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Intent intent = new Intent(HomePatient.this,PatientOneExerciseActivity.class);
//                    intent.putExtra("info","This is activity from card item index  "+finalI);
//                    startActivity(intent);
                    Intent clinicianOnePatient= new Intent(HomePatient.this, PatientOneExerciseActivity.class);
                    switch (finalI+1){
                        case 1:
                            clinicianOnePatient.putExtra("info",type1);
                            break;
                        case 2:
                            clinicianOnePatient.putExtra("info",type2);

                            break;
                        case 3:
                            clinicianOnePatient.putExtra("info",type3);

                            break;
                        case 4:
                            clinicianOnePatient.putExtra("info",type4);

                            break;
                        case 5:
                            clinicianOnePatient.putExtra("info",type5);

                            break;
                        case 6:
                            clinicianOnePatient.putExtra("info",type6);

                            break;
                        case 7:
                            clinicianOnePatient.putExtra("info",type7);

                            break;
                        case 8:
                            clinicianOnePatient.putExtra("info",type8);

                            break;
                        case 9:
                            clinicianOnePatient.putExtra("info",type9);

                            break;
                    }
                    clinicianOnePatient.putExtra("Patient", patient);
                    clinicianOnePatient.putExtra("Exercise", exercise);

                    startActivity(clinicianOnePatient);

                }
            });
        }
    }


    private void readExercisesFromFirebase(Patient patient) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        System.out.println(patient.getClinicName());
        System.out.println(patient.getIdentificationNumber());
        db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String dateAdd = String.valueOf(document.getData().get("dateAdd"));
                                String dateStart = String.valueOf(document.getData().get("dateStart"));
                                System.out.println("dateStart "+dateStart);
                                String dateEnd = String.valueOf(document.getData().get("dateEnd"));
                                String[] dateStartSplit = dateStart.split("/", 5);
                                String[] dateEndSplit = dateEnd.split("/", 5);
//                                Date dateBefore = new Date(Integer.parseInt(dateStartSplit[2]), Integer.parseInt(dateStartSplit[1]) - 1, Integer.parseInt(dateStartSplit[0]));
                                LocalDate dateBefore = LocalDate.of(Integer.parseInt(dateStartSplit[2]), Integer.parseInt(dateStartSplit[1]), Integer.parseInt(dateStartSplit[0]));

//                                Date dateAfter = new Date(Integer.parseInt(dateEndSplit[2])+1, Integer.parseInt(dateEndSplit[1]) - 1, Integer.parseInt(dateEndSplit[0]));
                                LocalDate dateAfter = LocalDate.of(Integer.parseInt(dateEndSplit[2]), Integer.parseInt(dateEndSplit[1]), Integer.parseInt(dateEndSplit[0]));

//                                String dateToday = new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime());
//                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                                LocalDate currentDate = LocalDate.now();
                                System.out.println("dateBefore "+dateBefore);
                                System.out.println("dateAfter "+dateAfter);
                                System.out.println("currentDate "+currentDate);

//                                String[] dateTodaySplit = dateToday.split("/", 5);
                                if (currentDate.isAfter(dateBefore) && dateAfter.isAfter(currentDate)) {
                                    List<TypeExercise> typeExercises = new ArrayList<>();
                                    exercise = new Exercise(dateStart,dateEnd,dateAdd, typeExercises);
                                    System.out.println("triiiiiii");
//                                    String[] dateStartSplit = exercise.getDateStart().split("/", 5);
//                                    String[] dateEndSplit = exercise.getDateEnd().split("/", 5);
//                                    Date dateBefore = new Date(Integer.parseInt(dateStartSplit[2]), Integer.parseInt(dateStartSplit[1]), Integer.parseInt(dateStartSplit[0]));
//                                    Date dateAfter = new Date(Integer.parseInt(dateEndSplit[2]), Integer.parseInt(dateEndSplit[1]), Integer.parseInt(dateEndSplit[0]));

//                                    long dateBeforeInMs = dateBefore.getTime();
//                                    long dateAfterInMs = dateAfter.getTime();
//
//                                    long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);
//
//                                    long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
//                                    daysDiff = daysDiff +1;
                                    long daysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);

//                                    System.out.println(" The number of days between dates: " + daysDiff);
                                    String text = "נשאר "+daysBetween+" ימים לתרגול";
//
                                    tvDayForProgram.setText(text);
                                    readExercisesTypeFromFirebase( patient);
                                    System.out.println("The date has not passed today's date.");
                                } else {
                                    // The date has passed today's date or is the same as today's date
                                    // Perform your desired actions here
                                    System.out.println("The date has passed today's date.");
                                }
//                                List<TypeExercise> typeExercises = new ArrayList<>();
//                                Exercise exercise = new Exercise(dateStart,dateEnd,dateAdd, typeExercises);
//                                exerciseList.add(exercise);
                            }

                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }});
    }

    private void readExercisesTypeFromFirebase(Patient patient) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
            String dateStartF = exercise.getDateStart().replace("/", "");

            db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                    .document(dateStartF).collection("Type")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int i = 0;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String type = String.valueOf(document.getData().get("type"));
                                    String count = String.valueOf(document.getData().get("count"));
                                    String index = String.valueOf(document.getData().get("index"));
                                    String countDo = String.valueOf(document.getData().get("countDo"));
                                    countProgram += Integer.parseInt(count);
                                    List<Experience> experienceList = new ArrayList<>();

                                    TypeExercise typeExercise = new TypeExercise(type,count,index,countDo,experienceList);
                                    typeExerciseList.add(typeExercise);
                                }

                                getExperienceToFirebase(0);




                            } else {
                                System.out.println("Error getting documents.");
                            }
                        }});


    }

    private void getExperienceToFirebase(int i) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (i < typeExerciseList.size()) {


            String dateStartF = exercise.getDateStart().replace("/", "");

            db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                    .document(dateStartF).collection("Type").document(typeExerciseList.get(i).getType() + typeExerciseList.get(i).getCount()).collection("Experience")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<Experience> experienceList = new ArrayList<>();
                                int countDo1 = 0;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String id = document.getId();
                                    String date = String.valueOf(document.getData().get("date"));
                                    String image = String.valueOf(document.getData().get("Image"));
                                    Boolean success = (Boolean) (document.getData().get("success"));
                                    Boolean progress = (Boolean) (document.getData().get("progress"));

                                    if ((success) && (countDo1 < Integer.parseInt(typeExerciseList.get(i).getCount()))){
                                        countDo1+=1;
                                    }

                                    Experience experience = new Experience(image, date, success, progress);
                                    experienceList.add(experience);

                                }
                                countDoProgram += countDo1;

                                typeExerciseList.get(i).setExperienceList(experienceList);
                                getExperienceToFirebase(i+1);

                            } else {
                                System.out.println("Error getting documents.");
                            }
                        }
                    });

        }else {
            exercise.setTypeExercises(typeExerciseList);
            int countDo2 =0;

            int progress = (countDoProgram * 100) / countProgram;
            lpiDayForProgram.setProgress(progress);
            if (progress == 0) {
                lpiDayForProgram.setProgress(1);

            }
            for (int j = 0; j < typeExerciseList.size(); j++) {
                switch (typeExerciseList.get(j).getIndex()) {
                    case "1":
                        cvExecution1.setVisibility(View.VISIBLE);
                        tvNameExercise1.setText("תרגיל 1");
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity1.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type1 = typeExerciseList.get(j);
                        break;
                    case "2":
                        cvExecution2.setVisibility(View.VISIBLE);
                        tvNameExercise2.setText("תרגיל 2");
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity2.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type2 = typeExerciseList.get(j);

                        break;
                    case "3":
                        cvExecution3.setVisibility(View.VISIBLE);
                        tvNameExercise3.setText("תרגיל 3");
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity3.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type3 = typeExerciseList.get(j);

                        break;
                    case "4":
                        cvExecution4.setVisibility(View.VISIBLE);
                        tvNameExercise4.setText("תרגיל 4");
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity4.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type4 = typeExerciseList.get(j);

                        break;
                    case "5":
                        cvExecution5.setVisibility(View.VISIBLE);
                        tvNameExercise5.setText("תרגיל 5");
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity5.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type5 = typeExerciseList.get(j);

                        break;
                    case "6":
                        cvExecution6.setVisibility(View.VISIBLE);
                        tvNameExercise6.setText("תרגיל 6");
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity6.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type6 = typeExerciseList.get(j);

                        break;
                    case "7":
                        cvExecution7.setVisibility(View.VISIBLE);
                        tvNameExercise7.setText("תרגיל 7");
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity7.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type7 = typeExerciseList.get(j);

                        break;
                    case "8":
                        cvExecution8.setVisibility(View.VISIBLE);
                        tvNameExercise8.setText("תרגיל 8");
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity8.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type8 = typeExerciseList.get(j);

                        break;
                    case "9":
                        cvExecution9.setVisibility(View.VISIBLE);
                        tvNameExercise9.setText("תרגיל 9");
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity9.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type9 = typeExerciseList.get(j);

                        break;
                }
            }
            for (int j = 0; j < typeExerciseList.size(); j++) {
                if (typeExerciseList.get(j).getIndex().equals("0")) {
                    if (tvExecutionQuantity1.getText().toString().equals("")) {
                        cvExecution1.setVisibility(View.VISIBLE);
                        tvNameExercise1.setText(typeExerciseList.get(j).getType());
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity1.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type1 = typeExerciseList.get(j);

                    } else if (tvExecutionQuantity2.getText().toString().equals("")) {
                        cvExecution2.setVisibility(View.VISIBLE);
                        tvNameExercise2.setText(typeExerciseList.get(j).getType());
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity2.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        tvExecutionQuantity2.setText(String.valueOf(typeExerciseList.get(j).getCountDo() + "/" + typeExerciseList.get(j).getCount()));
                    } else if (tvExecutionQuantity3.getText().toString().equals("")) {
                        cvExecution3.setVisibility(View.VISIBLE);
                        tvNameExercise3.setText(typeExerciseList.get(j).getType());
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity3.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type3 = typeExerciseList.get(j);

                    } else if (tvExecutionQuantity4.getText().toString().equals("")) {
                        cvExecution4.setVisibility(View.VISIBLE);
                        tvNameExercise4.setText(typeExerciseList.get(j).getType());
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity4.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type4 = typeExerciseList.get(j);

                    } else if (tvExecutionQuantity5.getText().toString().equals("")) {
                        cvExecution5.setVisibility(View.VISIBLE);
                        tvNameExercise5.setText(typeExerciseList.get(j).getType());
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity5.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type5 = typeExerciseList.get(j);
                    } else if (tvExecutionQuantity6.getText().toString().equals("")) {
                        cvExecution6.setVisibility(View.VISIBLE);
                        tvNameExercise6.setText(typeExerciseList.get(j).getType());
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity6.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type6 = typeExerciseList.get(j);

                    } else if (tvExecutionQuantity7.getText().toString().equals("")) {
                        cvExecution7.setVisibility(View.VISIBLE);
                        tvNameExercise7.setText(typeExerciseList.get(j).getType());
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity7.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type7 = typeExerciseList.get(j);

                    } else if (tvExecutionQuantity8.getText().toString().equals("")) {
                        cvExecution8.setVisibility(View.VISIBLE);
                        tvNameExercise8.setText(typeExerciseList.get(j).getType());
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity8.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type8 = typeExerciseList.get(j);

                    } else if (tvExecutionQuantity9.getText().toString().equals("")) {
                        cvExecution9.setVisibility(View.VISIBLE);
                        tvNameExercise9.setText(typeExerciseList.get(j).getType());
                        countDo2 =0;
                        for (int k = 0; k < typeExerciseList.get(j).getExperienceList().size(); k++) {

                            if ((typeExerciseList.get(j).getExperienceList().get(k).getSuccess()) && (countDo2 < Integer.parseInt(typeExerciseList.get(j).getCount()))) {
                                countDo2 += 1;
                            }
                        }
                        tvExecutionQuantity9.setText(String.valueOf(countDo2 + "/" + typeExerciseList.get(j).getCount()));
                        type9 = typeExerciseList.get(j);

                    }
                }
            }
        }
    }
}