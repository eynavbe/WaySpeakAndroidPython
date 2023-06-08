package com.eynav.wayspeack.ui.home.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.eynav.wayspeack.ui.home.Exercise;
import com.eynav.wayspeack.ui.home.TypeExercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProgramOneExercise extends Fragment {
    List<Exercise> exerciseList = new ArrayList<>();
    Patient patient;
    Exercise exercise;
    TextView etDateStart, etDateEnd, stIndexOpen, etCountOpen, stIndexClose, etCountClose, stIndexSmile,
    etCountSmile,stIndexPursing , etCountPursing, stIndexTongueOut, etCountTongueOut, stIndexMoveTongueRight,
    etCountMoveTongueRight, stIndexMoveTongueLeft,etCountMoveTongueLeft, stIndexLiftTongueNose,
    etCountLiftTongueNose, stIndexLowerTongueChin, etCountLowerTongueChin;
    LinearLayout llOpen, llClose, llSmile, llPursing, llTongueOut, llMoveTongueRight, llMoveTongueLeft
            , llLiftTongueNose, llLowerTongueChin;

    public ProgramOneExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.program_one_exercise, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        findById(root);

//        patient = (Patient) getActivity().getIntent().getParcelableExtra("Patient");
        etDateStart.setText("תאריך התחלה: "+exercise.getDateStart());
        etDateEnd.setText("תאריך סיום: "+exercise.getDateStart());
        llOpen.setVisibility(View.GONE);
        llClose.setVisibility(View.GONE);
        llSmile.setVisibility(View.GONE);
        llPursing.setVisibility(View.GONE);
        llTongueOut.setVisibility(View.GONE);
        llMoveTongueRight.setVisibility(View.GONE);
        llMoveTongueLeft.setVisibility(View.GONE);
        llLiftTongueNose.setVisibility(View.GONE);
        llLowerTongueChin.setVisibility(View.GONE);
        for (TypeExercise typeExercises: exercise.getTypeExercises()) {
            if (typeExercises.getType().equals("mouth_open")){
                llOpen.setVisibility(View.VISIBLE);
                stIndexOpen.setText(typeExercises.getIndex());
                etCountOpen.setText(typeExercises.getCount());

            }
            if (typeExercises.getType().equals("mouth_close")){
                llClose.setVisibility(View.VISIBLE);
                stIndexClose.setText(typeExercises.getIndex());
                etCountClose.setText(typeExercises.getCount());

            }
            if (typeExercises.getType().equals("smile")){

                llSmile.setVisibility(View.VISIBLE);
                stIndexSmile.setText(typeExercises.getIndex());
                etCountSmile.setText(typeExercises.getCount());

            }
            if (typeExercises.getType().equals("lip_pursing")){

                llPursing.setVisibility(View.VISIBLE);
                stIndexPursing.setText(typeExercises.getIndex());
                etCountPursing.setText(typeExercises.getCount());

            }
            if (typeExercises.getType().equals("straight_tongue_out")){

                llTongueOut.setVisibility(View.VISIBLE);
                stIndexTongueOut.setText(typeExercises.getIndex());
                etCountTongueOut.setText(typeExercises.getCount());

            }
            if (typeExercises.getType().equals("move_tongue_to_right")){

                llMoveTongueRight.setVisibility(View.VISIBLE);
                stIndexMoveTongueRight.setText(typeExercises.getIndex());
                etCountMoveTongueRight.setText(typeExercises.getCount());

            }
            if (typeExercises.getType().equals("Move_tongue_to_left")){

                llMoveTongueLeft.setVisibility(View.VISIBLE);
                stIndexMoveTongueLeft.setText(typeExercises.getIndex());
                etCountMoveTongueLeft.setText(typeExercises.getCount());

            }
            if (typeExercises.getType().equals("lift_tongue_to_nose")){

                llLiftTongueNose.setVisibility(View.VISIBLE);
                stIndexLiftTongueNose.setText(typeExercises.getIndex());
                etCountLiftTongueNose.setText(typeExercises.getCount());

            }
            if (typeExercises.getType().equals("down_tongue_to_chin")){
                llLowerTongueChin.setVisibility(View.VISIBLE);
                stIndexLowerTongueChin.setText(typeExercises.getIndex());
                etCountLowerTongueChin.setText(typeExercises.getCount());
            }


        }
//        readExercisesFromFirebase(patient);

    }

    private void findById(View root) {
        etDateStart = root.findViewById(R.id.etDateStart);
        etDateEnd = root.findViewById(R.id.etDateEnd);
        stIndexOpen = root.findViewById(R.id.stIndexOpen);
        etCountOpen = root.findViewById(R.id.etCountOpen);
        stIndexClose = root.findViewById(R.id.stIndexClose);
        etCountClose = root.findViewById(R.id.etCountClose);
        stIndexSmile = root.findViewById(R.id.stIndexSmile);
        etCountSmile = root.findViewById(R.id.etCountSmile);
        stIndexPursing  = root.findViewById(R.id.stIndexPursing);
        etCountPursing  = root.findViewById(R.id.etCountPursing);
        stIndexTongueOut  = root.findViewById(R.id.stIndexTongueOut);
        etCountTongueOut  = root.findViewById(R.id.etCountTongueOut);
        stIndexMoveTongueRight = root.findViewById(R.id.stIndexMoveTongueRight);
        etCountMoveTongueRight = root.findViewById(R.id.etCountMoveTongueRight);
        stIndexMoveTongueLeft = root.findViewById(R.id.stIndexMoveTongueLeft);
        etCountMoveTongueLeft = root.findViewById(R.id.etCountMoveTongueLeft);
        stIndexLiftTongueNose = root.findViewById(R.id.stIndexLiftTongueNose);
        etCountLiftTongueNose = root.findViewById(R.id.etCountLiftTongueNose);
        stIndexLowerTongueChin = root.findViewById(R.id.stIndexLowerTongueChin);
        etCountLowerTongueChin = root.findViewById(R.id.etCountLowerTongueChin);
        llOpen = root.findViewById(R.id.llOpen);
        llClose = root.findViewById(R.id.llClose);
        llSmile = root.findViewById(R.id.llSmile);
        llPursing = root.findViewById(R.id.llPursing);
        llTongueOut = root.findViewById(R.id.llTongueOut);
        llMoveTongueRight = root.findViewById(R.id.llMoveTongueRight);
        llMoveTongueLeft = root.findViewById(R.id.llMoveTongueLeft);
        llLiftTongueNose = root.findViewById(R.id.llLiftTongueNose);
        llLowerTongueChin = root.findViewById(R.id.llLowerTongueChin);
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
                                String dateEnd = String.valueOf(document.getData().get("dateEnd"));
                                List<TypeExercise> typeExercises = new ArrayList<>();
                                Exercise exercise = new Exercise(dateStart,dateEnd,dateAdd, typeExercises);
                                exerciseList.add(exercise);
                            }
                            readExercisesTypeFromFirebase( patient,0);

                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }});
    }

    private void readExercisesTypeFromFirebase(Patient patient, int i) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (exerciseList.size() > i){
            String dateStartF = exerciseList.get(i).getDateStart().replace("/", "");

            db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                    .document(dateStartF).collection("Type")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<TypeExercise> typeExerciseList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String type = String.valueOf(document.getData().get("type"));
                                    String count = String.valueOf(document.getData().get("count"));
                                    String index = String.valueOf(document.getData().get("index"));
                                    String countDo = String.valueOf(document.getData().get("countDo"));

                                    TypeExercise typeExercise = new TypeExercise(type,count,index,countDo);
                                    typeExerciseList.add(typeExercise);
                                }
                                exerciseList.get(i).setTypeExercises(typeExerciseList);
                                readExercisesTypeFromFirebase( patient,i+1);

                            } else {
                                System.out.println("Error getting documents.");
                            }
                        }});
        }else {



        }

    }
}