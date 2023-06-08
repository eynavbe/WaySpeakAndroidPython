package com.eynav.wayspeack.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eynav.wayspeack.Clinician;
import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExercisesAdapterHolder> {
    private List<Exercise> exerciseList;
    private Patient patient;
    Context context;
    Clinician clinician;
    public ExercisesAdapter(Context context, List<Exercise> exerciseList, Patient patient, Clinician clinician) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.patient = patient;
        this.clinician = clinician;
    }


    @NonNull
    @Override
    public ExercisesAdapter.ExercisesAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.clinician_list_exercises_card_view, parent, false);
        return new ExercisesAdapter.ExercisesAdapterHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesAdapter.ExercisesAdapterHolder holder, @SuppressLint("RecyclerView") int position) {
        Exercise exercise = exerciseList.get(position);
//        holder.tvPatientName.setText(exercise.getIdentificationNumber());
        holder.exercise = exercise;
        holder.tvExerciseName.setText(exercise.getDateStart());
//        String[] dateStartSplit = exercise.getDateStart().split("/", 5);
//        String[] dateEndSplit = exercise.getDateEnd().split("/", 5);
//        LocalDate dateBefore = LocalDate.of(Integer.parseInt(dateStartSplit[2]), Integer.parseInt(dateStartSplit[1]), Integer.parseInt(dateStartSplit[0]));
//        LocalDate dateAfter = LocalDate.of(Integer.parseInt(dateEndSplit[2]), Integer.parseInt(dateEndSplit[1]), Integer.parseInt(dateEndSplit[0]));

//        long dateBeforeInMs = dateBefore.getTime();
//        long dateAfterInMs = dateAfter.getTime();
//
//        long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);
//
//        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
//        daysDiff = daysDiff +1;
//        System.out.println(" The number of days between dates: " + daysDiff);
        StringBuilder types = new StringBuilder();
        for (TypeExercise typeExercises: exercise.getTypeExercises()) {
            types.append(typeExercises.getType()).append(", ");
        }
        holder.tvExerciseDate.setText(String.valueOf(types));

        holder.imUpdateExercise.setOnClickListener(l ->{
            Intent clinicianOnePatient= new Intent(context, AddExerciseActivity.class);

            clinicianOnePatient.putExtra("Exercise", exercise);
            clinicianOnePatient.putExtra("Patient", patient);

            context.startActivity(clinicianOnePatient);
        });
        holder.imDeleteExercise.setOnClickListener(l ->{
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String dateStartF = exercise.getDateStart().replace("/", "");

            db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                    .document(dateStartF)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                            for (TypeExercise typeExercise: exercise.getTypeExercises()) {


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


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });



        });

    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Exercise> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        exerciseList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    public void clear() {
        int size = exerciseList.size();
        exerciseList.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ExercisesAdapterHolder extends RecyclerView.ViewHolder {
        Exercise exercise;
        TextView tvExerciseName,tvExerciseDate;
        ImageView imUpdateExercise, imDeleteExercise;
        LinearLayout cartExercise;

        public ExercisesAdapterHolder(@NonNull View itemView) {
            super(itemView);
            cartExercise = itemView.findViewById(R.id.cartExercise);
            tvExerciseName = itemView.findViewById(R.id.tvExerciseName);
            tvExerciseDate = itemView.findViewById(R.id.tvExerciseDate);
            imUpdateExercise = itemView.findViewById(R.id.imUpdateExercise);
            imDeleteExercise = itemView.findViewById(R.id.imDeleteExercise);
            itemView.setOnClickListener(l -> {
//                SharedPreferences shareHall = context.getSharedPreferences("hall", MODE_PRIVATE);
//
//                // save your string in SharedPreferences
//                shareHall.edit().putString("hall", hallName).commit();
//                Intent clinicianOnePatient= new Intent(itemView.getContext(), MainActivity.class);

//                clinicianOnePatient.putExtra("Exercise", exercise);

                // get or create SharedPreferences
//                SharedPreferences shareType = itemView.getContext().getSharedPreferences("type", MODE_PRIVATE);
//
//                // save your string in SharedPreferences
//                shareType.edit().putString("type", "Client").commit();
//                itemView.getContext().startActivity(clinicianOnePatient);

//                Fragment myFragment = new AccountSummary(exercise, "Exercise");
//                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();



                Intent clinicianOnePatient= new Intent(itemView.getContext(), OneExerciseActivity.class);

                clinicianOnePatient.putExtra("Exercise", exercise);
                clinicianOnePatient.putExtra("Patient", patient);
                clinicianOnePatient.putExtra("Clinician", clinician);


                itemView.getContext().startActivity(clinicianOnePatient);

            });
        }
    }
}
