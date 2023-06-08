package com.eynav.wayspeack.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eynav.wayspeack.Clinician;
import com.eynav.wayspeack.MainActivity;
import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    Patient patient;
    int count = 0;
    RecyclerView rvExercises;
    FloatingActionButton fabAddExercises;
    ExercisesAdapter exercisesAdapter;
    List <Exercise> exerciseList = new ArrayList<>();
    Context context;
    Clinician nameUser;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        patient = (Patient) getActivity().getIntent().getParcelableExtra("Patient");
        nameUser = (Clinician) getActivity().getIntent().getParcelableExtra("Clinician");

        System.out.println("patient "+patient);
        Gson gson = new Gson();
        context = getContext();

        String json = context.getSharedPreferences("name", MODE_PRIVATE).getString("name", "");
//        nameUser = gson.fromJson(json, Clinician.class);
        return inflater.inflate(R.layout.fragment_home, container, false);


    }
    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        rvExercises = root.findViewById(R.id.rvExercises);
        fabAddExercises = root.findViewById(R.id.fabAddExercises);
        if (exercisesAdapter != null){
            exercisesAdapter.clear();
        }
        readExercisesFromFirebase(patient);

        fabAddExercises.setOnClickListener(l -> {
            Intent addExerciseActivity= new Intent(context, AddExerciseActivity.class);
            addExerciseActivity.putExtra("Patient", patient);
            addExerciseActivity.putExtra("Clinician", nameUser);

            context.startActivity(addExerciseActivity);
        });

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
            rvExercises.setLayoutManager(new LinearLayoutManager(context));
            exercisesAdapter = new ExercisesAdapter(context, exerciseList,patient,nameUser);
            rvExercises.setAdapter(exercisesAdapter);
        }

    }



}