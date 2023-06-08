package com.eynav.wayspeack.ui.home.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.eynav.wayspeack.ui.home.Exercise;
import com.eynav.wayspeack.ui.home.Experience;
import com.eynav.wayspeack.ui.home.TypeExercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientProgress extends Fragment {

    Exercise exercise;
    Patient patient;
    ConstraintLayout clProgressIndicator1,clProgressIndicator2,clProgressIndicator3,clProgressIndicator4,clProgressIndicator5,clProgressIndicator6,clProgressIndicator7,clProgressIndicator8,clProgressIndicator9;
    TextView tvProgressIndicator1,tvProgressIndicatorPercentage1;
    CircularProgressIndicator circularProgressIndicator1;
    TextView tvProgressIndicator2,tvProgressIndicatorPercentage2;
    CircularProgressIndicator circularProgressIndicator2;
    TextView tvProgressIndicator3,tvProgressIndicatorPercentage3;
    CircularProgressIndicator circularProgressIndicator3;
    TextView tvProgressIndicator4,tvProgressIndicatorPercentage4;
    CircularProgressIndicator circularProgressIndicator4;
    TextView tvProgressIndicator5,tvProgressIndicatorPercentage5;
    CircularProgressIndicator circularProgressIndicator5;
    TextView tvProgressIndicator6,tvProgressIndicatorPercentage6;
    CircularProgressIndicator circularProgressIndicator6;
    TextView tvProgressIndicator7,tvProgressIndicatorPercentage7;
    CircularProgressIndicator circularProgressIndicator7;
    TextView tvProgressIndicator8,tvProgressIndicatorPercentage8;
    CircularProgressIndicator circularProgressIndicator8;
    TextView tvProgressIndicator9,tvProgressIndicatorPercentage9;
    CircularProgressIndicator circularProgressIndicator9;
    TypeExercise[] typeExerciseList = new TypeExercise[9];
    HashMap<String, Integer> typeKey = new HashMap<String, Integer>();

    View root;
    public PatientProgress(Exercise exercise, Patient patient) {
        this.exercise = exercise;
        this.patient = patient;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.patient_progress, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
//        BarChartView graphView = new BarChartView(getContext(), new int[]{50, 80, 120}, new String[]{"Label 1", "Label 2", "Label 3"});
        this.root = root;
        getExercisesTypeIndexToFirebase();
//        HashMap<String, Integer> typeKey = new HashMap<String, Integer>();
//        typeKey.put("mouth_open", 0);
//        typeKey.put("mouth_close", 1);
//        typeKey.put("smile", 2);
//        typeKey.put("lip_pursing", 3);
//        typeKey.put("straight_tongue_out", 4);
//        typeKey.put("move_tongue_to_right", 5);
//        typeKey.put("Move_tongue_to_left", 6);
//        typeKey.put("lift_tongue_to_nose", 7);
//        typeKey.put("down_tongue_to_chin", 8);
        clProgressIndicator1 =  root.findViewById(R.id.clProgressIndicator1);
        clProgressIndicator2 =  root.findViewById(R.id.clProgressIndicator2);
        clProgressIndicator3 =  root.findViewById(R.id.clProgressIndicator3);
        clProgressIndicator4 =  root.findViewById(R.id.clProgressIndicator4);
        clProgressIndicator5 =  root.findViewById(R.id.clProgressIndicator5);
        clProgressIndicator6 =  root.findViewById(R.id.clProgressIndicator6);
        clProgressIndicator7 =  root.findViewById(R.id.clProgressIndicator7);
        clProgressIndicator8 =  root.findViewById(R.id.clProgressIndicator8);
        clProgressIndicator9 =  root.findViewById(R.id.clProgressIndicator9);

        circularProgressIndicator1 = root.findViewById(R.id.circularProgressIndicator1);
         tvProgressIndicator1 = root.findViewById(R.id.tvProgressIndicator1);
         tvProgressIndicatorPercentage1 = root.findViewById(R.id.tvProgressIndicatorPercentage1);
        circularProgressIndicator1.setOnClickListener(l ->{
//            התוצאה הכי טובה בביצוע התרגיל הזה - תמונה
//            המספרים: כמה פעמיים הוא ביצע את התרגיל וכמה פעמיים הוא ביצע בהצלחה את התרגיל הזה
//            האם היה כאן התקדמות
            System.out.println("kkkkkkkkncnckkn1");
            System.out.println(typeExerciseList);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            int k = 0;
            for (int i = 0; i < typeExerciseList.length; i++) {
                if (typeExerciseList[i] != null){
                    if (typeExerciseList[i].getIndex().equals("1")){
                        k = typeKey.get(typeExerciseList[i].getType());
                    }
                }

            }
            TextView textViewType = new TextView(getContext());
            textViewType.setText("Type: "+typeExerciseList[k].getType());
            textViewType.setTextSize(14);
            textViewType.setTextColor(Color.BLACK);
            parentLayout.addView(textViewType);
            TextView textViewCount = new TextView(getContext());
            textViewCount.setText("Count: "+typeExerciseList[k].getCount());
            textViewCount.setTextSize(14);
            textViewCount.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCount);
            TextView textViewCountDo = new TextView(getContext());
            textViewCountDo.setText("CountDo: "+typeExerciseList[k].getCountDo());
            textViewCountDo.setTextSize(14);
            textViewCountDo.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCountDo);
            TextView textViewIndex = new TextView(getContext());
            textViewIndex.setText("Index: "+typeExerciseList[k].getIndex()+"\n");
            textViewIndex.setTextSize(14);
            textViewIndex.setTextColor(Color.BLACK);
            parentLayout.addView(textViewIndex);
            for (int i = 0; i < typeExerciseList[k].getExperienceList().size(); i++) {
//                TODO imageView typeExerciseList[k].getExperienceList().get(i).getImage()
                ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(typeExerciseList[k].getExperienceList().get(i).getImage()).into(imageView);
                parentLayout.addView(imageView);
                TextView textViewDate = new TextView(getContext());
                textViewDate.setText("\n"+"Date: "+typeExerciseList[k].getExperienceList().get(i).getDate());
                textViewDate.setTextSize(14);
                textViewDate.setTextColor(Color.BLACK);
                parentLayout.addView(textViewDate);
                TextView textViewSuccess = new TextView(getContext());
                textViewSuccess.setText("Success: "+typeExerciseList[k].getExperienceList().get(i).getSuccess());
                textViewSuccess.setTextSize(14);
                textViewSuccess.setTextColor(Color.BLACK);
                parentLayout.addView(textViewSuccess);
                TextView textViewProgress = new TextView(getContext());
                textViewProgress.setText("Progress: "+typeExerciseList[k].getExperienceList().get(i).getProgress());
                textViewProgress.setTextSize(14);
                textViewProgress.setTextColor(Color.BLACK);
                parentLayout.addView(textViewProgress);
            }

            builder.setView(parentLayout);
            builder.show();

        });

         circularProgressIndicator2 = root.findViewById(R.id.circularProgressIndicator2);
         tvProgressIndicator2 = root.findViewById(R.id.tvProgressIndicator2);
         tvProgressIndicatorPercentage2 = root.findViewById(R.id.tvProgressIndicatorPercentage2);
//        circularProgressIndicator2.setProgress(10);
//        tvProgressIndicator2.setText("תרגיל 2");
//        tvProgressIndicatorPercentage2.setText("10%");
        circularProgressIndicator2.setOnClickListener(l ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            int k = 1;
            for (int i = 0; i < typeExerciseList.length; i++) {
                if (typeExerciseList[i] != null) {

                    if (typeExerciseList[i].getIndex().equals("2")) {
                        k = typeKey.get(typeExerciseList[i].getType());
                    }
                }
            }
            TextView textViewType = new TextView(getContext());
            textViewType.setText("Type: "+typeExerciseList[k].getType());
            textViewType.setTextSize(14);
            textViewType.setTextColor(Color.BLACK);
            parentLayout.addView(textViewType);
            TextView textViewCount = new TextView(getContext());
            textViewCount.setText("Count: "+typeExerciseList[k].getCount());
            textViewCount.setTextSize(14);
            textViewCount.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCount);
            TextView textViewCountDo = new TextView(getContext());
            textViewCountDo.setText("CountDo: "+typeExerciseList[k].getCountDo());
            textViewCountDo.setTextSize(14);
            textViewCountDo.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCountDo);
            TextView textViewIndex = new TextView(getContext());
            textViewIndex.setText("Index: "+typeExerciseList[k].getIndex()+"\n");
            textViewIndex.setTextSize(14);
            textViewIndex.setTextColor(Color.BLACK);
            parentLayout.addView(textViewIndex);
            for (int i = 0; i < typeExerciseList[k].getExperienceList().size(); i++) {
                ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(typeExerciseList[k].getExperienceList().get(i).getImage()).into(imageView);
                parentLayout.addView(imageView);

                TextView textViewDate = new TextView(getContext());
                textViewDate.setText("\n"+"Date: "+typeExerciseList[k].getExperienceList().get(i).getDate());
                textViewDate.setTextSize(14);
                textViewDate.setTextColor(Color.BLACK);
                parentLayout.addView(textViewDate);
                TextView textViewSuccess = new TextView(getContext());
                textViewSuccess.setText("Success: "+typeExerciseList[k].getExperienceList().get(i).getSuccess());
                textViewSuccess.setTextSize(14);
                textViewSuccess.setTextColor(Color.BLACK);
                parentLayout.addView(textViewSuccess);
                TextView textViewProgress = new TextView(getContext());
                textViewProgress.setText("Progress: "+typeExerciseList[k].getExperienceList().get(i).getProgress());
                textViewProgress.setTextSize(14);
                textViewProgress.setTextColor(Color.BLACK);
                parentLayout.addView(textViewProgress);
            }

            builder.setView(parentLayout);
            builder.show();
        });

         circularProgressIndicator3 = root.findViewById(R.id.circularProgressIndicator3);
         tvProgressIndicator3 = root.findViewById(R.id.tvProgressIndicator3);
         tvProgressIndicatorPercentage3 = root.findViewById(R.id.tvProgressIndicatorPercentage3);
//        circularProgressIndicator3.setProgress(30);
//        tvProgressIndicator3.setText("תרגיל 3");
//        tvProgressIndicatorPercentage3.setText("30%");
        circularProgressIndicator3.setOnClickListener(l ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            int k = 2;
            for (int i = 0; i < typeExerciseList.length; i++) {
                if (typeExerciseList[i] != null) {

                    if (typeExerciseList[i].getIndex().equals("3")) {
                        k = typeKey.get(typeExerciseList[i].getType());
                    }
                }
            }
            TextView textViewType = new TextView(getContext());
            textViewType.setText("Type: "+typeExerciseList[k].getType());
            textViewType.setTextSize(14);
            textViewType.setTextColor(Color.BLACK);
            parentLayout.addView(textViewType);
            TextView textViewCount = new TextView(getContext());
            textViewCount.setText("Count: "+typeExerciseList[k].getCount());
            textViewCount.setTextSize(14);
            textViewCount.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCount);
            TextView textViewCountDo = new TextView(getContext());
            textViewCountDo.setText("CountDo: "+typeExerciseList[k].getCountDo());
            textViewCountDo.setTextSize(14);
            textViewCountDo.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCountDo);
            TextView textViewIndex = new TextView(getContext());
            textViewIndex.setText("Index: "+typeExerciseList[k].getIndex()+"\n");
            textViewIndex.setTextSize(14);
            textViewIndex.setTextColor(Color.BLACK);
            parentLayout.addView(textViewIndex);
            for (int i = 0; i < typeExerciseList[k].getExperienceList().size(); i++) {
                ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(typeExerciseList[k].getExperienceList().get(i).getImage()).into(imageView);
                parentLayout.addView(imageView);

                TextView textViewDate = new TextView(getContext());
                textViewDate.setText("\n"+"Date: "+typeExerciseList[k].getExperienceList().get(i).getDate());
                textViewDate.setTextSize(14);
                textViewDate.setTextColor(Color.BLACK);
                parentLayout.addView(textViewDate);
                TextView textViewSuccess = new TextView(getContext());
                textViewSuccess.setText("Success: "+typeExerciseList[k].getExperienceList().get(i).getSuccess());
                textViewSuccess.setTextSize(14);
                textViewSuccess.setTextColor(Color.BLACK);
                parentLayout.addView(textViewSuccess);
                TextView textViewProgress = new TextView(getContext());
                textViewProgress.setText("Progress: "+typeExerciseList[k].getExperienceList().get(i).getProgress());
                textViewProgress.setTextSize(14);
                textViewProgress.setTextColor(Color.BLACK);
                parentLayout.addView(textViewProgress);
            }

            builder.setView(parentLayout);
            builder.show();
        });

         circularProgressIndicator4 = root.findViewById(R.id.circularProgressIndicator4);
         tvProgressIndicator4 = root.findViewById(R.id.tvProgressIndicator4);
         tvProgressIndicatorPercentage4 = root.findViewById(R.id.tvProgressIndicatorPercentage4);
//        circularProgressIndicator4.setProgress(10);
//        tvProgressIndicator4.setText("תרגיל 4");
//        tvProgressIndicatorPercentage4.setText("10%");
        circularProgressIndicator4.setOnClickListener(l ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            int k = 3;
            for (int i = 0; i < typeExerciseList.length; i++) {
                if (typeExerciseList[i] != null) {

                    if (typeExerciseList[i].getIndex().equals("4")) {
                        k = typeKey.get(typeExerciseList[i].getType());
                    }
                }
            }
            TextView textViewType = new TextView(getContext());
            textViewType.setText("Type: "+typeExerciseList[k].getType());
            textViewType.setTextSize(14);
            textViewType.setTextColor(Color.BLACK);
            parentLayout.addView(textViewType);
            TextView textViewCount = new TextView(getContext());
            textViewCount.setText("Count: "+typeExerciseList[k].getCount());
            textViewCount.setTextSize(14);
            textViewCount.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCount);
            TextView textViewCountDo = new TextView(getContext());
            textViewCountDo.setText("CountDo: "+typeExerciseList[k].getCountDo());
            textViewCountDo.setTextSize(14);
            textViewCountDo.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCountDo);
            TextView textViewIndex = new TextView(getContext());
            textViewIndex.setText("Index: "+typeExerciseList[k].getIndex()+"\n");
            textViewIndex.setTextSize(14);
            textViewIndex.setTextColor(Color.BLACK);
            parentLayout.addView(textViewIndex);
            for (int i = 0; i < typeExerciseList[k].getExperienceList().size(); i++) {
                ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(typeExerciseList[k].getExperienceList().get(i).getImage()).into(imageView);
                parentLayout.addView(imageView);

                TextView textViewDate = new TextView(getContext());
                textViewDate.setText("\n"+"Date: "+typeExerciseList[k].getExperienceList().get(i).getDate());
                textViewDate.setTextSize(14);
                textViewDate.setTextColor(Color.BLACK);
                parentLayout.addView(textViewDate);
                TextView textViewSuccess = new TextView(getContext());
                textViewSuccess.setText("Success: "+typeExerciseList[k].getExperienceList().get(i).getSuccess());
                textViewSuccess.setTextSize(14);
                textViewSuccess.setTextColor(Color.BLACK);
                parentLayout.addView(textViewSuccess);
                TextView textViewProgress = new TextView(getContext());
                textViewProgress.setText("Progress: "+typeExerciseList[k].getExperienceList().get(i).getProgress());
                textViewProgress.setTextSize(14);
                textViewProgress.setTextColor(Color.BLACK);
                parentLayout.addView(textViewProgress);
            }

            builder.setView(parentLayout);
            builder.show();
        });

         circularProgressIndicator5 = root.findViewById(R.id.circularProgressIndicator5);
         tvProgressIndicator5 = root.findViewById(R.id.tvProgressIndicator5);
         tvProgressIndicatorPercentage5 = root.findViewById(R.id.tvProgressIndicatorPercentage5);
//        circularProgressIndicator5.setProgress(10);
//        tvProgressIndicator5.setText("תרגיל 5");
//        tvProgressIndicatorPercentage5.setText("10%");
        circularProgressIndicator5.setOnClickListener(l ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            int k = 4;
            for (int i = 0; i < typeExerciseList.length; i++) {
                if (typeExerciseList[i] != null) {

                    if (typeExerciseList[i].getIndex().equals("5")) {
                        k = typeKey.get(typeExerciseList[i].getType());
                    }
                }
            }
            TextView textViewType = new TextView(getContext());
            textViewType.setText("Type: "+typeExerciseList[k].getType());
            textViewType.setTextSize(14);
            textViewType.setTextColor(Color.BLACK);
            parentLayout.addView(textViewType);
            TextView textViewCount = new TextView(getContext());
            textViewCount.setText("Count: "+typeExerciseList[k].getCount());
            textViewCount.setTextSize(14);
            textViewCount.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCount);
            TextView textViewCountDo = new TextView(getContext());
            textViewCountDo.setText("CountDo: "+typeExerciseList[k].getCountDo());
            textViewCountDo.setTextSize(14);
            textViewCountDo.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCountDo);
            TextView textViewIndex = new TextView(getContext());
            textViewIndex.setText("Index: "+typeExerciseList[k].getIndex()+"\n");
            textViewIndex.setTextSize(14);
            textViewIndex.setTextColor(Color.BLACK);
            parentLayout.addView(textViewIndex);
            for (int i = 0; i < typeExerciseList[k].getExperienceList().size(); i++) {
                ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(typeExerciseList[k].getExperienceList().get(i).getImage()).into(imageView);
                parentLayout.addView(imageView);

                TextView textViewDate = new TextView(getContext());
                textViewDate.setText("\n"+"Date: "+typeExerciseList[k].getExperienceList().get(i).getDate());
                textViewDate.setTextSize(14);
                textViewDate.setTextColor(Color.BLACK);
                parentLayout.addView(textViewDate);
                TextView textViewSuccess = new TextView(getContext());
                textViewSuccess.setText("Success: "+typeExerciseList[k].getExperienceList().get(i).getSuccess());
                textViewSuccess.setTextSize(14);
                textViewSuccess.setTextColor(Color.BLACK);
                parentLayout.addView(textViewSuccess);
                TextView textViewProgress = new TextView(getContext());
                textViewProgress.setText("Progress: "+typeExerciseList[k].getExperienceList().get(i).getProgress());
                textViewProgress.setTextSize(14);
                textViewProgress.setTextColor(Color.BLACK);
                parentLayout.addView(textViewProgress);
            }

            builder.setView(parentLayout);
            builder.show();
        });

         circularProgressIndicator6 = root.findViewById(R.id.circularProgressIndicator6);
         tvProgressIndicator6 = root.findViewById(R.id.tvProgressIndicator6);
         tvProgressIndicatorPercentage6 = root.findViewById(R.id.tvProgressIndicatorPercentage6);
//        circularProgressIndicator6.setProgress(10);
//        tvProgressIndicator6.setText("תרגיל 6");
//        tvProgressIndicatorPercentage6.setText("10%");
        circularProgressIndicator6.setOnClickListener(l ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            int k = 5;
            for (int i = 0; i < typeExerciseList.length; i++) {
                if (typeExerciseList[i] != null) {

                    if (typeExerciseList[i].getIndex().equals("6")) {
                        k = typeKey.get(typeExerciseList[i].getType());
                    }
                }
            }
            TextView textViewType = new TextView(getContext());
            textViewType.setText("Type: "+typeExerciseList[k].getType());
            textViewType.setTextSize(14);
            textViewType.setTextColor(Color.BLACK);
            parentLayout.addView(textViewType);
            TextView textViewCount = new TextView(getContext());
            textViewCount.setText("Count: "+typeExerciseList[k].getCount());
            textViewCount.setTextSize(14);
            textViewCount.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCount);
            TextView textViewCountDo = new TextView(getContext());
            textViewCountDo.setText("CountDo: "+typeExerciseList[k].getCountDo());
            textViewCountDo.setTextSize(14);
            textViewCountDo.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCountDo);
            TextView textViewIndex = new TextView(getContext());
            textViewIndex.setText("Index: "+typeExerciseList[k].getIndex()+"\n");
            textViewIndex.setTextSize(14);
            textViewIndex.setTextColor(Color.BLACK);
            parentLayout.addView(textViewIndex);
            for (int i = 0; i < typeExerciseList[k].getExperienceList().size(); i++) {
                ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(typeExerciseList[k].getExperienceList().get(i).getImage()).into(imageView);
                parentLayout.addView(imageView);

                TextView textViewDate = new TextView(getContext());
                textViewDate.setText("\n"+"Date: "+typeExerciseList[k].getExperienceList().get(i).getDate());
                textViewDate.setTextSize(14);
                textViewDate.setTextColor(Color.BLACK);
                parentLayout.addView(textViewDate);
                TextView textViewSuccess = new TextView(getContext());
                textViewSuccess.setText("Success: "+typeExerciseList[k].getExperienceList().get(i).getSuccess());
                textViewSuccess.setTextSize(14);
                textViewSuccess.setTextColor(Color.BLACK);
                parentLayout.addView(textViewSuccess);
                TextView textViewProgress = new TextView(getContext());
                textViewProgress.setText("Progress: "+typeExerciseList[k].getExperienceList().get(i).getProgress());
                textViewProgress.setTextSize(14);
                textViewProgress.setTextColor(Color.BLACK);
                parentLayout.addView(textViewProgress);
            }

            builder.setView(parentLayout);
            builder.show();
        });

         circularProgressIndicator7 = root.findViewById(R.id.circularProgressIndicator7);
         tvProgressIndicator7 = root.findViewById(R.id.tvProgressIndicator7);
         tvProgressIndicatorPercentage7 = root.findViewById(R.id.tvProgressIndicatorPercentage7);
//        circularProgressIndicator7.setProgress(10);
//        tvProgressIndicator7.setText("תרגיל 7");
//        tvProgressIndicatorPercentage7.setText("10%");
        circularProgressIndicator7.setOnClickListener(l ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            int k = 6;
            for (int i = 0; i < typeExerciseList.length; i++) {
                if (typeExerciseList[i] != null) {

                    if (typeExerciseList[i].getIndex().equals("7")) {
                        k = typeKey.get(typeExerciseList[i].getType());
                    }
                }
            }
            TextView textViewType = new TextView(getContext());
            textViewType.setText("Type: "+typeExerciseList[k].getType());
            textViewType.setTextSize(14);
            textViewType.setTextColor(Color.BLACK);
            parentLayout.addView(textViewType);
            TextView textViewCount = new TextView(getContext());
            textViewCount.setText("Count: "+typeExerciseList[k].getCount());
            textViewCount.setTextSize(14);
            textViewCount.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCount);
            TextView textViewCountDo = new TextView(getContext());
            textViewCountDo.setText("CountDo: "+typeExerciseList[k].getCountDo());
            textViewCountDo.setTextSize(14);
            textViewCountDo.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCountDo);
            TextView textViewIndex = new TextView(getContext());
            textViewIndex.setText("Index: "+typeExerciseList[k].getIndex()+"\n");
            textViewIndex.setTextSize(14);
            textViewIndex.setTextColor(Color.BLACK);
            parentLayout.addView(textViewIndex);
            for (int i = 0; i < typeExerciseList[k].getExperienceList().size(); i++) {
                ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(typeExerciseList[k].getExperienceList().get(i).getImage()).into(imageView);
                parentLayout.addView(imageView);

                TextView textViewDate = new TextView(getContext());
                textViewDate.setText("\n"+"Date: "+typeExerciseList[k].getExperienceList().get(i).getDate());
                textViewDate.setTextSize(14);
                textViewDate.setTextColor(Color.BLACK);
                parentLayout.addView(textViewDate);
                TextView textViewSuccess = new TextView(getContext());
                textViewSuccess.setText("Success: "+typeExerciseList[k].getExperienceList().get(i).getSuccess());
                textViewSuccess.setTextSize(14);
                textViewSuccess.setTextColor(Color.BLACK);
                parentLayout.addView(textViewSuccess);
                TextView textViewProgress = new TextView(getContext());
                textViewProgress.setText("Progress: "+typeExerciseList[k].getExperienceList().get(i).getProgress());
                textViewProgress.setTextSize(14);
                textViewProgress.setTextColor(Color.BLACK);
                parentLayout.addView(textViewProgress);
            }

            builder.setView(parentLayout);
            builder.show();
        });

         circularProgressIndicator8 = root.findViewById(R.id.circularProgressIndicator8);
         tvProgressIndicator8 = root.findViewById(R.id.tvProgressIndicator8);
         tvProgressIndicatorPercentage8 = root.findViewById(R.id.tvProgressIndicatorPercentage8);
//        circularProgressIndicator8.setProgress(10);
//        tvProgressIndicator8.setText("תרגיל 8");
//        tvProgressIndicatorPercentage8.setText("10%");
        circularProgressIndicator8.setOnClickListener(l ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            int k = 7;
            for (int i = 0; i < typeExerciseList.length; i++) {
                if (typeExerciseList[i] != null) {

                    if (typeExerciseList[i].getIndex().equals("8")) {
                        k = typeKey.get(typeExerciseList[i].getType());
                    }
                }
            }
            TextView textViewType = new TextView(getContext());
            textViewType.setText("Type: "+typeExerciseList[k].getType());
            textViewType.setTextSize(14);
            textViewType.setTextColor(Color.BLACK);
            parentLayout.addView(textViewType);
            TextView textViewCount = new TextView(getContext());
            textViewCount.setText("Count: "+typeExerciseList[k].getCount());
            textViewCount.setTextSize(14);
            textViewCount.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCount);
            TextView textViewCountDo = new TextView(getContext());
            textViewCountDo.setText("CountDo: "+typeExerciseList[k].getCountDo());
            textViewCountDo.setTextSize(14);
            textViewCountDo.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCountDo);
            TextView textViewIndex = new TextView(getContext());
            textViewIndex.setText("Index: "+typeExerciseList[k].getIndex()+"\n");
            textViewIndex.setTextSize(14);
            textViewIndex.setTextColor(Color.BLACK);
            parentLayout.addView(textViewIndex);
            for (int i = 0; i < typeExerciseList[k].getExperienceList().size(); i++) {
                ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(typeExerciseList[k].getExperienceList().get(i).getImage()).into(imageView);
                parentLayout.addView(imageView);

                TextView textViewDate = new TextView(getContext());
                textViewDate.setText("\n"+"Date: "+typeExerciseList[k].getExperienceList().get(i).getDate());
                textViewDate.setTextSize(14);
                textViewDate.setTextColor(Color.BLACK);
                parentLayout.addView(textViewDate);
                TextView textViewSuccess = new TextView(getContext());
                textViewSuccess.setText("Success: "+typeExerciseList[k].getExperienceList().get(i).getSuccess());
                textViewSuccess.setTextSize(14);
                textViewSuccess.setTextColor(Color.BLACK);
                parentLayout.addView(textViewSuccess);
                TextView textViewProgress = new TextView(getContext());
                textViewProgress.setText("Progress: "+typeExerciseList[k].getExperienceList().get(i).getProgress());
                textViewProgress.setTextSize(14);
                textViewProgress.setTextColor(Color.BLACK);
                parentLayout.addView(textViewProgress);
            }

            builder.setView(parentLayout);
            builder.show();
        });

         circularProgressIndicator9 = root.findViewById(R.id.circularProgressIndicator9);
         tvProgressIndicator9 = root.findViewById(R.id.tvProgressIndicator9);
         tvProgressIndicatorPercentage9 = root.findViewById(R.id.tvProgressIndicatorPercentage9);
//        circularProgressIndicator9.setProgress(10);
//        tvProgressIndicator9.setText("תרגיל 9");
//        tvProgressIndicatorPercentage9.setText("10%");
        circularProgressIndicator9.setOnClickListener(l ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            int k = 8;
            for (int i = 0; i < typeExerciseList.length; i++) {
                if (typeExerciseList[i] != null) {
                    if (typeExerciseList[i].getIndex().equals("9")) {
                        k = typeKey.get(typeExerciseList[i].getType());
                    }
                }
            }
            TextView textViewType = new TextView(getContext());
            textViewType.setText("Type: "+typeExerciseList[k].getType());
            textViewType.setTextSize(14);
            textViewType.setTextColor(Color.BLACK);
            parentLayout.addView(textViewType);
            TextView textViewCount = new TextView(getContext());
            textViewCount.setText("Count: "+typeExerciseList[k].getCount());
            textViewCount.setTextSize(14);
            textViewCount.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCount);
            TextView textViewCountDo = new TextView(getContext());
            textViewCountDo.setText("CountDo: "+typeExerciseList[k].getCountDo());
            textViewCountDo.setTextSize(14);
            textViewCountDo.setTextColor(Color.BLACK);
            parentLayout.addView(textViewCountDo);
            TextView textViewIndex = new TextView(getContext());
            textViewIndex.setText("Index: "+typeExerciseList[k].getIndex()+"\n");
            textViewIndex.setTextSize(14);
            textViewIndex.setTextColor(Color.BLACK);
            parentLayout.addView(textViewIndex);
            for (int i = 0; i < typeExerciseList[k].getExperienceList().size(); i++) {
                ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(typeExerciseList[k].getExperienceList().get(i).getImage()).into(imageView);
                parentLayout.addView(imageView);

                TextView textViewDate = new TextView(getContext());
                textViewDate.setText("\n"+"Date: "+typeExerciseList[k].getExperienceList().get(i).getDate());
                textViewDate.setTextSize(14);
                textViewDate.setTextColor(Color.BLACK);
                parentLayout.addView(textViewDate);
                TextView textViewSuccess = new TextView(getContext());
                textViewSuccess.setText("Success: "+typeExerciseList[k].getExperienceList().get(i).getSuccess());
                textViewSuccess.setTextSize(14);
                textViewSuccess.setTextColor(Color.BLACK);
                parentLayout.addView(textViewSuccess);
                TextView textViewProgress = new TextView(getContext());
                textViewProgress.setText("Progress: "+typeExerciseList[k].getExperienceList().get(i).getProgress());
                textViewProgress.setTextSize(14);
                textViewProgress.setTextColor(Color.BLACK);
                parentLayout.addView(textViewProgress);
            }

            builder.setView(parentLayout);
            builder.show();
        });





//        int[] dataPoints2 = {50, 80, 120, 90, 110, 120};
//        String[] xAxisLabels2 = {"Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6"};
//        BarChartView graphView2 = root.findViewById(R.id.graphView2);
//        graphView2.setData(dataPoints2, xAxisLabels2);
//
//
//        int[] dataPoints3 = {50, 80, 120, 90, 110, 120};
//        String[] xAxisLabels3 = {"Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6"};
//        BarChartView graphView3 = root.findViewById(R.id.graphView3);
//        graphView3.setData(dataPoints3, xAxisLabels3);
//
//
//        int[] dataPoints4 = {50, 80, 120, 90, 110, 120};
//        String[] xAxisLabels4 = {"Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6"};
//        BarChartView graphView4 = root.findViewById(R.id.graphView4);
//        graphView4.setData(dataPoints4, xAxisLabels4);
//
//
//        int[] dataPoints5 = {50, 80, 120, 90, 110, 120};
//        String[] xAxisLabels5 = {"Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6"};
//        BarChartView graphView5 = root.findViewById(R.id.graphView5);
//        graphView5.setData(dataPoints5, xAxisLabels5);
//
//
//        int[] dataPoints6 = {50, 80, 120, 90, 110, 120};
//        String[] xAxisLabels6 = {"Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6"};
//        BarChartView graphView6 = root.findViewById(R.id.graphView6);
//        graphView6.setData(dataPoints6, xAxisLabels6);
//
//
//        int[] dataPoints7 = {50, 80, 120, 90, 110, 120};
//        String[] xAxisLabels7 = {"Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6"};
//        BarChartView graphView7 = root.findViewById(R.id.graphView7);
//        graphView7.setData(dataPoints7, xAxisLabels7);
//
//
//        int[] dataPoints8 = {50, 80};
//        String[] xAxisLabels8 = {"Label 1", "Label 6"};
//        BarChartView graphView8 = root.findViewById(R.id.graphView8);
//        graphView8.setData(dataPoints8, xAxisLabels8);
//
//
//        int[] dataPoints9 = {50, 80, 120, 90, 110, 120};
//        String[] xAxisLabels9 = {"Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6"};
//        BarChartView graphView9 = root.findViewById(R.id.graphView9);
//        graphView9.setData(dataPoints1, xAxisLabels9);


//
//        BarChartView.dataPoints =  new int[]{50, 80, 120, 90, 110,120,50, 80, 120, 90, 110,120};;
//        BarChartView.xAxisLabels= new String[]{"Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6","Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6"};
//        BarChartView graphView2 = root.findViewById(R.id.graphView2);
//        graphView2.invalidate();
    }
    private void getExercisesTypeIndexToFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        typeKey.put("mouth_open", 0);
//        typeKey.put("mouth_close", 1);
//        typeKey.put("smile", 2);
//        typeKey.put("lip_pursing", 3);
//        typeKey.put("straight_tongue_out", 4);
//        typeKey.put("move_tongue_to_right", 5);
//        typeKey.put("Move_tongue_to_left", 6);
//        typeKey.put("lift_tongue_to_nose", 7);
//        typeKey.put("down_tongue_to_chin", 8);
        String dateStartF = exercise.getDateStart().replace("/", "");
        int[] dataPoints2 = new int[9];
        db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                .document(dateStartF).collection("Type")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int j = 8;
                            int count11 = 0;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String type = String.valueOf(document.getData().get("type"));
                                String index = String.valueOf(document.getData().get("index"));
                                if (!(index.equals("0") || index.equals(""))) {
                                    typeKey.put(type, (Integer.parseInt(index)-1));
                                }else {
                                    typeKey.put(type, j);
                                    j -=1;
                                }

                            }
                            getExercisesTypeToFirebase(typeKey);

                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }});


    }
    private void getExercisesTypeToFirebase(HashMap<String, Integer> typeKey) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        HashMap<String, Integer> typeKey = new HashMap<String, Integer>();
//        typeKey.put("mouth_open", 0);
//        typeKey.put("mouth_close", 1);
//        typeKey.put("smile", 2);
//        typeKey.put("lip_pursing", 3);
//        typeKey.put("straight_tongue_out", 4);
//        typeKey.put("move_tongue_to_right", 5);
//        typeKey.put("Move_tongue_to_left", 6);
//        typeKey.put("lift_tongue_to_nose", 7);
//        typeKey.put("down_tongue_to_chin", 8);
        String dateStartF = exercise.getDateStart().replace("/", "");
//        int[] dataPoints2 = new int[9];
        db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                .document(dateStartF).collection("Type")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int j = 8;
                            int count11 = 0;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String type = String.valueOf(document.getData().get("type"));
                                String count = String.valueOf(document.getData().get("count"));
                                String index = String.valueOf(document.getData().get("index"));
                                String countDo = String.valueOf(document.getData().get("countDo"));
                                List<Experience> experienceList = new ArrayList<>();
//                                if (!(index.equals("") || index.equals("0"))){
//                                    dataPoints2[Integer.parseInt(index)] = Integer.parseInt(countDo);
//                                }else {
//                                    dataPoints2[j] = Integer.parseInt(countDo);
//                                    j-=1;
//                                }
                                TypeExercise typeExercise = new TypeExercise(type,count,index,countDo,experienceList);
                                if (typeKey.get(type) != null) {
                                    typeExerciseList[typeKey.get(type)] = typeExercise;
                                    count11 += 1;

                                }
                            }
                            int[] dataPoints1 = new int[count11];
                            String[] xAxisLabels1 = new String[count11];
                            int p = 0;
                            for (int i = 0; i < typeExerciseList.length; i++) {
                                if (typeExerciseList[i] != null){
                                    if (Integer.parseInt(typeExerciseList[i].getCount()) > 0){
                                        dataPoints1[p] = Integer.parseInt(typeExerciseList[i].getCountDo());
                                        xAxisLabels1[p] = "תרגיל " + (p+1);
                                        p+=1;
                                    }
                                }
                            }
//                            int[] dataPoints1 = {50, 80, 120, 90, 110, 120, 90, 110, 120};
//                            String[] xAxisLabels1 = {"תרגיל 1", "תרגיל 2", "תרגיל 3", "תרגיל 4", "תרגיל 5", "תרגיל 6", "תרגיל 7", "תרגיל 8", "תרגיל 9"};
                            BarChartView graphView1 = root.findViewById(R.id.graphView1);
                            graphView1.setData(dataPoints1, xAxisLabels1);
                            getExperienceToFirebase(0, 0);
                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }});


    }

    private void getExperienceToFirebase(int i,int p) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (i < typeExerciseList.length) {


            String dateStartF = exercise.getDateStart().replace("/", "");
            if (typeExerciseList[i] != null) {
                db.collection("clinician").document(patient.getClinicName()).collection("Patients").document(patient.getIdentificationNumber()).collection("Exercises")
                        .document(dateStartF).collection("Type").document(typeExerciseList[i].getType() + typeExerciseList[i].getCount()).collection("Experience")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int y = p;

                                    List<Experience> experienceList = new ArrayList<>();
                                    int count1 = 0;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String id = document.getId();
                                        String date = String.valueOf(document.getData().get("date"));
                                        String image = String.valueOf(document.getData().get("Image"));
                                        Boolean success = (Boolean) (document.getData().get("success"));
                                        Boolean progress = (Boolean) (document.getData().get("progress"));

                                        Experience experience = new Experience(image, date, success, progress);
                                        experienceList.add(experience);
                                        if ((success) && (Integer.parseInt(typeExerciseList[i].getCount()) > count1)) {
                                            count1 += 1;
                                        }

                                    }
                                    typeExerciseList[i].setExperienceList(experienceList);




//                                    for (int i = 0; i < typeExerciseList.length; i++) {
//                                        if (typeExerciseList[i] != null){
//                                            if (Integer.parseInt(typeExerciseList[i].getCount()) > 0){
//                                                dataPoints1[p] = Integer.parseInt(typeExerciseList[i].getCountDo());
//                                                xAxisLabels1[p] = "תרגיל " + (p+1);
//                                                p+=1;
//                                            }
//                                        }
//                                    }




                                    if (!(typeExerciseList[i].getCount().equals("0") || typeExerciseList[i].getCount().equals(""))){


                                    int progress = (count1 * 100) / (Integer.parseInt(typeExerciseList[i].getCount()));
                                    switch (i){
                                        case 0:
                                            circularProgressIndicator1.setProgress(progress);
                                            tvProgressIndicator1.setText("תרגיל "+(y+1));
                                            y +=1;
                                            tvProgressIndicatorPercentage1.setText(progress + "%");
                                            clProgressIndicator1.setVisibility(View.VISIBLE);
                                            break;
                                        case 1:
                                            circularProgressIndicator2.setProgress(progress);
                                            tvProgressIndicator2.setText("תרגיל "+(y+1));
                                            y +=1;
                                            clProgressIndicator2.setVisibility(View.VISIBLE);

                                            tvProgressIndicatorPercentage2.setText(progress + "%");
                                            break;
                                        case 2:
                                            clProgressIndicator3.setVisibility(View.VISIBLE);

                                            circularProgressIndicator3.setProgress(progress);
                                            tvProgressIndicator3.setText("תרגיל "+(y+1));
                                            y +=1;
                                            tvProgressIndicatorPercentage3.setText(progress + "%");
                                            break;
                                        case 3:
                                            clProgressIndicator4.setVisibility(View.VISIBLE);
                                            circularProgressIndicator4.setProgress(progress);
                                            tvProgressIndicator4.setText("תרגיל "+(y+1));
                                            y +=1;
                                            tvProgressIndicatorPercentage4.setText(progress + "%");
                                            break;
                                        case 4:
                                            clProgressIndicator5.setVisibility(View.VISIBLE);

                                            circularProgressIndicator5.setProgress(progress);
                                            tvProgressIndicator5.setText("תרגיל "+(y+1));
                                            y +=1;
                                            tvProgressIndicatorPercentage5.setText(progress + "%");
                                            break;
                                        case 5:
                                            clProgressIndicator6.setVisibility(View.VISIBLE);
                                            circularProgressIndicator6.setProgress(progress);
                                            tvProgressIndicator6.setText("תרגיל "+(y+1));
                                            y +=1;
                                            tvProgressIndicatorPercentage6.setText(progress + "%");

                                            break;
                                        case 6:
                                            clProgressIndicator7.setVisibility(View.VISIBLE);
                                            circularProgressIndicator7.setProgress(progress);
                                            tvProgressIndicator7.setText("תרגיל "+(y+1));
                                            y +=1;
                                            tvProgressIndicatorPercentage7.setText(progress + "%");

                                            break;
                                        case 7:
                                            clProgressIndicator8.setVisibility(View.VISIBLE);
                                            circularProgressIndicator8.setProgress(progress);
                                            tvProgressIndicator8.setText("תרגיל "+(y+1));
                                            y +=1;
                                            tvProgressIndicatorPercentage8.setText(progress + "%");

                                            break;
                                        case 8:
                                            clProgressIndicator9.setVisibility(View.VISIBLE);
                                            circularProgressIndicator9.setProgress(progress);
                                            tvProgressIndicator9.setText("תרגיל "+(y+1));
                                            y +=1;
                                            tvProgressIndicatorPercentage9.setText(progress + "%");

                                            break;
                                    }
                                    }
                                    getExperienceToFirebase(i + 1,y);

                                } else {
                                    System.out.println("Error getting documents.");
                                }
                            }
                        });

            } else {

                getExperienceToFirebase(i + 1, p);

            }
        } else {

        }

    }
}