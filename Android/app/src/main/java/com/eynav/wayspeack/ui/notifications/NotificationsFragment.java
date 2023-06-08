package com.eynav.wayspeack.ui.notifications;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eynav.wayspeack.ClinicianListPatientsAdapter;
import com.eynav.wayspeack.HomePatient;
import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.eynav.wayspeack.databinding.FragmentNotificationsBinding;
import com.eynav.wayspeack.ui.home.TypeExercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationsFragment extends Fragment {
    Patient patient;
    int count = 0;
    ImageButton ibBest1, ibBest2,ibBest3,ibBest4,ibBest5,ibBest6,ibBest7,ibBest8,ibBest9;
//    List <TypeResults> typeResultsList = new ArrayList<>();
    TextView tvPatientIdentificationNumber, tvPatientIdentificationNumber2, tvDateBirth, tvGender, tvHourMeeting,
            tvDateMeeting, tvPrescribingClinician;
    TypeResults[] typeResultsArray = new TypeResults[9];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        patient = (Patient) getActivity().getIntent().getParcelableExtra("Patient");
        return inflater.inflate(R.layout.fragment_notifications, container, false);


    }
    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        tvPatientIdentificationNumber = root.findViewById(R.id.tvPatientIdentificationNumber);
        tvPatientIdentificationNumber2 = root.findViewById(R.id.tvPatientIdentificationNumber2);
        tvDateBirth = root.findViewById(R.id.tvDateBirth);
        tvGender = root.findViewById(R.id.tvGender);
        tvHourMeeting = root.findViewById(R.id.tvHourMeeting);
        tvDateMeeting = root.findViewById(R.id.tvDateMeeting);
        tvPrescribingClinician = root.findViewById(R.id.tvPrescribingClinician);
        ibBest1= root.findViewById(R.id.ibBest1);
        ibBest2= root.findViewById(R.id.ibBest2);
        ibBest3 = root.findViewById(R.id.ibBest3);
        ibBest4= root.findViewById(R.id.ibBest4);
        ibBest5= root.findViewById(R.id.ibBest5);
        ibBest6= root.findViewById(R.id.ibBest6);
        ibBest7= root.findViewById(R.id.ibBest7);
        ibBest8= root.findViewById(R.id.ibBest8);
        ibBest9= root.findViewById(R.id.ibBest9);

        readFromFirebase();
//        tvPatientIdentificationNumber.setText(patient.getIdentificationNumber());
        tvPatientIdentificationNumber2.setText("Identification number: "+patient.getIdentificationNumber());
        tvDateBirth.setText("Date of birth: "+patient.getDateBirth());
        tvGender.setText("gender: "+patient.getGender());
        tvHourMeeting.setText("Meeting time: "+patient.getHourMeeting());
        tvDateMeeting.setText("Meeting date: "+patient.getDateMeeting());
        tvPrescribingClinician.setText("Clinician who registered: "+patient.getPrescribingClinician());


        ibBest1.setOnClickListener(l->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ibBest1.getDrawable());
            TextView textView = new TextView(getContext());
            textView.setText("00/00/00 00:00");
            textView.setTextSize(14);
            textView.setTextColor(Color.BLACK);
//TODO textView.setText(typeResultsArray[0].getDate());
            parentLayout.addView(imageView);
            parentLayout.addView(textView);
            builder.setView(parentLayout);
            builder.show();
        });
        ibBest2.setOnClickListener(l->{
//TODO check  imageView.set
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ibBest2.getDrawable());
            TextView textView = new TextView(getContext());
            textView.setText(typeResultsArray[1].getDate());
            textView.setTextSize(14);
            textView.setTextColor(Color.BLACK);
            parentLayout.addView(imageView);
            parentLayout.addView(textView);
            builder.setView(parentLayout);
            builder.show();
        });
        ibBest3.setOnClickListener(l->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ibBest3.getDrawable());
            TextView textView = new TextView(getContext());
            textView.setText(typeResultsArray[2].getDate());
            textView.setTextSize(14);
            textView.setTextColor(Color.BLACK);
            parentLayout.addView(imageView);
            parentLayout.addView(textView);
            builder.setView(parentLayout);
            builder.show();
        });
        ibBest4.setOnClickListener(l->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ibBest4.getDrawable());
            TextView textView = new TextView(getContext());
            textView.setText(typeResultsArray[3].getDate());
            textView.setTextSize(14);
            textView.setTextColor(Color.BLACK);
            parentLayout.addView(imageView);
            parentLayout.addView(textView);
            builder.setView(parentLayout);
            builder.show();
        });
        ibBest5.setOnClickListener(l->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ibBest5.getDrawable());
            TextView textView = new TextView(getContext());
            textView.setText(typeResultsArray[4].getDate());
            textView.setTextSize(14);
            textView.setTextColor(Color.BLACK);
            parentLayout.addView(imageView);
            parentLayout.addView(textView);
            builder.setView(parentLayout);
            builder.show();
        });
        ibBest6.setOnClickListener(l->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ibBest6.getDrawable());
            TextView textView = new TextView(getContext());
            textView.setText(typeResultsArray[5].getDate());
            textView.setTextSize(14);
            textView.setTextColor(Color.BLACK);
            parentLayout.addView(imageView);
            parentLayout.addView(textView);
            builder.setView(parentLayout);
            builder.show();
        });
        ibBest7.setOnClickListener(l->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ibBest7.getDrawable());
            TextView textView = new TextView(getContext());
            textView.setText(typeResultsArray[6].getDate());
            textView.setTextSize(14);
            textView.setTextColor(Color.BLACK);
            parentLayout.addView(imageView);
            parentLayout.addView(textView);
            builder.setView(parentLayout);
            builder.show();
        });
        ibBest8.setOnClickListener(l->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ibBest8.getDrawable());
            TextView textView = new TextView(getContext());
            textView.setText(typeResultsArray[7].getDate());
            textView.setTextSize(14);
            textView.setTextColor(Color.BLACK);
            parentLayout.addView(imageView);
            parentLayout.addView(textView);
            builder.setView(parentLayout);
            builder.show();
        });
        ibBest9.setOnClickListener(l->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ibBest9.getDrawable());
            TextView textView = new TextView(getContext());
            textView.setText(typeResultsArray[8].getDate());
            textView.setTextSize(14);
            textView.setTextColor(Color.BLACK);
            parentLayout.addView(imageView);
            parentLayout.addView(textView);
            builder.setView(parentLayout);
            builder.show();
        });



        tvPatientIdentificationNumber.setVisibility(View.VISIBLE);
        tvPatientIdentificationNumber.setText("");
        String text = patient.getIdentificationNumber();
        new CountDownTimer(text.length()*100,100){

            @Override
            public void onTick(long l) {
                tvPatientIdentificationNumber.setText(tvPatientIdentificationNumber.getText().toString()+text.charAt(count));
                count++;
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void readFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Integer> typeKey = new HashMap<String, Integer>();
        typeKey.put("mouth_open", 0);
        typeKey.put("mouth_close", 1);
        typeKey.put("smile", 2);
        typeKey.put("lip_pursing", 3);
        typeKey.put("straight_tongue_out", 4);
        typeKey.put("move_tongue_to_right", 5);
        typeKey.put("Move_tongue_to_left", 6);
        typeKey.put("lift_tongue_to_nose", 7);
        typeKey.put("down_tongue_to_chin", 8);

        //  "mouth_close"
//       "smile"
//        "lip_pursing"
//   "straight_tongue_out"
//"move_tongue_to_right"
//    "Move_tongue_to_left"
//    "lift_tongue_to_nose"
//  "down_tongue_to_chin"
        db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber())
                .collection("BestResults")
//                .document(dateStartF).collection("Type").document(typeExercise.getType()+typeExercise.getCount()).collection("Experience").document(formattedDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<TypeResults> typeExerciseList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String date = String.valueOf(document.getData().get("date"));
                                String image = String.valueOf(document.getData().get("image"));
                                TypeResults typeResults = new TypeResults(id,date,image);

//                                typeExerciseList.add(typeResults);
//                                System.out.println();
                                if (typeKey.get(id) != null){
                                    typeResultsArray[typeKey.get(id)] = typeResults;
                                    if (!image.equals("")){
                                        switch (typeKey.get(id)){
                                            case 0:
//                                                ibBest1.setImageBitmap();
                                                break;
                                            case 1:
//                                                ibBest2.setImageBitmap();
                                                break;
                                            case 2:
//                                                ibBest3.setImageBitmap();
                                                break;
                                            case 3:
//                                                ibBest4.setImageBitmap();
                                                break;
                                            case 4:
//                                                ibBest5.setImageBitmap();
                                                break;
                                            case 5:
//                                                ibBest6.setImageBitmap();
                                                break;
                                            case 6:
//                                                ibBest7.setImageBitmap();
                                                break;
                                            case 7:
//                                                ibBest8.setImageBitmap();
                                                break;
                                            case 8:
//                                                ibBest9.setImageBitmap();
                                                break;

                                            //     TODO set image of typeResultsList


                                        }
                                    }
                                }
                            }
                            //"mouth_open"



//                            TODO set image of typeResultsList


                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }});


    }
}