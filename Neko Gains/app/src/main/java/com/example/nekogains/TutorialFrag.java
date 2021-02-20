package com.example.nekogains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TutorialFrag extends Fragment {

    private TextView workoutDescription;
    private TextView workoutName;
    private View view;

    WorkoutActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tutorial_frag, container, false);

        workoutDescription = view.findViewById(R.id.workoutDescription);
        workoutName = view.findViewById(R.id.workoutName);

        activity = ((WorkoutActivity)this.getActivity());

        setWorkoutName(activity.getExerciseName().replace("_", " "));
        setWorkoutDescription(activity.getExerciseDescription());

        Button button = view.findViewById(R.id.finishButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toTimer();
            }
        });

        return view;
    }

    public void toTimer() {
        ((WorkoutActivity) getActivity()).replaceFragments(new TimerFrag());
    }

    public void setWorkoutName(String text) {workoutName.setText(text);}

    public void setWorkoutDescription(String text){workoutDescription.setText(text);}
}
