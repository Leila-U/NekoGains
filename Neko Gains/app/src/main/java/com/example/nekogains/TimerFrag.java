package com.example.nekogains;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TimerFrag extends Fragment {
    User user;
    WorkoutActivity activity;
    View view;
    TextView titleView;
    TextView setView;
    TextView countView;
    TextView cheerView;
    ProgressBar progressView;
    int set_time = 0;
    int reps = 0;
    int sets = 0;
    int rep_time = 1;
    String[] cheers = {"Meow can do this!", "Neko believes in you!", "Hang in there Meow!", "Stay pawsitive, you got this!",
    "Looking feline good~ Keep it up!", "You're so Catheltic, you.", "Purr-fect, that's what I'm meowing about!", "Pawdon me, that was furristic!",
            "You're PAWESOME!"};
    CountDownTimer timer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.timer_frag, container, false);
        Button button = view.findViewById(R.id.finishButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDay();
                toTutorial();
            }
        });
        activity = ((WorkoutActivity)this.getActivity());

        user = activity.getUser();
        titleView = view.findViewById(R.id.title);
        countView = view.findViewById(R.id.rep_count);
        setView = view.findViewById(R.id.set_count);
        cheerView = view.findViewById(R.id.cheer_msg);
        progressView = view.findViewById(R.id.progress);
        sets = activity.getExerciseSets();

        // set title card to current exercise being done
        titleView.setText(activity.getExerciseName().replace("_", " "));
        setView.setText("Sets left: " + Integer.toString(sets));
        int y = (int) (Math.random()*cheers.length);
        cheerView.setText(cheers[y]);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // initialize view to required reps
        reps = activity.getExerciseReps();
        countView.setText(Integer.toString(reps));
        progressView.setProgress(100);
        set_time = activity.getExerciseReps()*rep_time*1000; //currently 3 seconds for each rep

        // begin timer
        timer = new CountDownTimer(set_time, rep_time*1000) {

            public void onTick(long millisUntilFinished) {
                int percentFinished = (int)(set_time-millisUntilFinished)*100/set_time;
                progressView.setProgress(percentFinished);
                countView.setText(Integer.toString(reps));
                if (reps % 3 == 0) {
                    int y = (int) (Math.random() * cheers.length);
                    cheerView.setText(cheers[y]);
                }
                reps--;
            }

            public void onFinish() {
                reps =0;
                countView.setText(Integer.toString(reps));
                progressView.setProgress(100);
                // restart counter if there are remaining sets left
                if (sets > 0) {
                    sets--;
                    setView.setText("Sets left: " + Integer.toString(sets));
                    onStart();
                }
            }
        }.start();
    }

    public void updateDay() {
        String exerciseType = "";
        String repType = "";
        switch (activity.getExercise()) {
            case LUNGES:
            case SQUATS:
                exerciseType = "WARMUP";
                repType = "WREPS";
                break;
            case RUN:
            case JACKS:
            case BURPEES:
                exerciseType = "CARDIO";
                repType = "CREPS";
                break;
            case PUSH_UPS:
            case CHIN_UPS:
            case BENCH_DIPS:
                exerciseType = "ARMS";
                repType = "AREPS";
                break;
            case SIT_UPS:
            case PLANKS:
                exerciseType = "CORE";
                repType = "COREPS";
                break;
            case LEG_RAISES:
                exerciseType = "LEGS";
                repType = "LREPS";
                break;
        }
        user.updateDay(user.getLastDay(), exerciseType, activity.getExerciseName());
        user.updateDay(user.getLastDay(), repType, Integer.toString(activity.getExerciseReps() - reps + activity.getExerciseReps()*(activity.getExerciseSets() - sets)));
    }

    public void toTutorial() {
        timer.cancel();
        activity.getSupportFragmentManager().popBackStack();
        activity.finishExercise();
        if (activity.isFinished()) {
            activity.replaceFragments(new EndWorkoutFrag());
        } else {
            activity.replaceFragments(new TutorialFrag());
        }
    }

}
