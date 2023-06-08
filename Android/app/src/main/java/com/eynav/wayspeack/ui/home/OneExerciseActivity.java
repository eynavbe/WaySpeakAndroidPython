package com.eynav.wayspeack.ui.home;

import android.os.Bundle;

import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.databinding.ActivityOneExerciseBinding;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.eynav.wayspeack.ui.home.ui.main.ProgramPagerAdapter;

public class OneExerciseActivity extends AppCompatActivity {

    private ActivityOneExerciseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOneExerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Exercise exercise;
        exercise = (Exercise) getIntent().getParcelableExtra("Exercise");
        Patient patient = (Patient) getIntent().getParcelableExtra("Patient");


        ProgramPagerAdapter sectionsPagerAdapter = new ProgramPagerAdapter(this, getSupportFragmentManager(), exercise, patient);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
}