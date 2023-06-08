package com.eynav.wayspeack.ui.home.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.databinding.FragmentOneExerciseBinding;
import com.eynav.wayspeack.ui.home.Exercise;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    int index = 1;

    private PageViewModel pageViewModel;
    private FragmentOneExerciseBinding binding;

    public static Fragment newInstance(int index, Exercise exercise, Patient patient) {
        if (index == 1){
            System.out.println("vvvvv 1");

            ProgramOneExercise fragment = new ProgramOneExercise(exercise);
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_SECTION_NUMBER, index);
            fragment.setArguments(bundle);
            return fragment;

        }
        else {
            System.out.println("vvvvv 2");
            PatientProgress fragment = new PatientProgress(exercise,patient);
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_SECTION_NUMBER, index);
            fragment.setArguments(bundle);
            return fragment;
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            System.out.println("index "+index);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentOneExerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if (index == 1){
            System.out.println("cccc");
        }
        if (index == 2){
            System.out.println("33322");

        }
        final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}