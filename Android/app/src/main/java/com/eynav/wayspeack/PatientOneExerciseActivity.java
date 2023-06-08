package com.eynav.wayspeack;

import android.os.Bundle;

import com.eynav.wayspeack.ui.home.Exercise;
import com.eynav.wayspeack.ui.home.TypeExercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.eynav.wayspeack.ui.main.SectionsPagerAdapter;
import com.eynav.wayspeack.databinding.ActivityPatientOneExerciseBinding;

public class PatientOneExerciseActivity extends AppCompatActivity {
    Exercise exercise;
    Patient patient;
    TypeExercise info;
    private ActivityPatientOneExerciseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPatientOneExerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        patient = (Patient) getIntent().getParcelableExtra("Patient");
        System.out.println(patient);
        exercise = (Exercise) getIntent().getParcelableExtra("Exercise");

        info = (TypeExercise) getIntent().getParcelableExtra("info");

        System.out.println(exercise);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), patient, exercise, info);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
//        if(getIntent() != null)
//        {
//            String info = getIntent().getStringExtra("info");
//            System.out.println(info);
//        }

    }
}