package com.eynav.wayspeack.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eynav.wayspeack.Patient;
import com.eynav.wayspeack.R;
import com.eynav.wayspeack.ui.home.Exercise;
import com.eynav.wayspeack.ui.home.TypeExercise;

public class ExerciseInstructions  extends Fragment {
    Patient patient;
    Exercise exercise;
    TypeExercise info;
    TextView tvExerciseInstruction, tvExerciseInstructionInfo;
    Context context;
    public ExerciseInstructions(Patient patient, Exercise exercise, TypeExercise info, Context context) {
        this.patient = patient;
        this.exercise = exercise;
        this.info = info;
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.exercise_instructions, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        tvExerciseInstruction = root.findViewById(R.id.tvExerciseInstruction);
        tvExerciseInstructionInfo = root.findViewById(R.id.tvExerciseInstructionInfo);
        tvExerciseInstruction.setText(info.getType());

    }
}
