package com.example.nekogains;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomWorkoutFrag extends Fragment implements View.OnClickListener {
    private View view;
    private User user;
    private EditText workoutName;
    private Button save;
    private Button back;
    private Button lunges;
    private Button squats;
    private Button run;
    private Button burpees;
    private Button jacks;
    private Button pushups;
    private Button chinups;
    private Button benchdips;
    private Button situps;
    private Button planks;
    private Button legraises;
    private ArrayList<Exercise> newcustomworkouts = new ArrayList<>();






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        user = new User(DatabaseHelper.getInstance(MainActivity.getContext()), ((MainActivity)this.getActivity()).getAppUserId());
        view = inflater.inflate(R.layout.customworkout_frag, container, false);
        save = view.findViewById(R.id.customworkoutSave);
        back = view.findViewById(R.id.customworkoutBack);
        workoutName = view.findViewById(R.id.customworkoutName);
        lunges = view.findViewById(R.id.addLunges);
        squats = view.findViewById(R.id.addSquats);
        run = view.findViewById(R.id.addRun);
        burpees = view.findViewById(R.id.addBurpees);
        jacks = view.findViewById(R.id.addJacks);
        pushups = view.findViewById(R.id.addPushups);
        chinups = view.findViewById(R.id.addChinups);
        benchdips = view.findViewById(R.id.addBenchdips);
        situps = view.findViewById(R.id.addSitups);
        planks = view.findViewById(R.id.addPlanks);
        legraises = view.findViewById(R.id.addLegraises);

        save.setOnClickListener(this);
        back.setOnClickListener(this);
        lunges.setOnClickListener(this);
        squats.setOnClickListener(this);
        run.setOnClickListener(this);
        burpees.setOnClickListener(this);
        jacks.setOnClickListener(this);
        pushups.setOnClickListener(this);
        chinups.setOnClickListener(this);
        benchdips.setOnClickListener(this);
        situps.setOnClickListener(this);
        planks.setOnClickListener(this);
        legraises.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addLunges:
                if (newcustomworkouts.contains(Exercise.LUNGES)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Lunges removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.LUNGES);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.LUNGES);
                }
                break;

            case R.id.addSquats:
                if (newcustomworkouts.contains(Exercise.SQUATS)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Squats removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.SQUATS);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.SQUATS);
                }
                break;
            case R.id.addRun:
                if (newcustomworkouts.contains(Exercise.RUN)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Running removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.RUN);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.RUN);
                }
                break;
            case R.id.addBurpees:
                if (newcustomworkouts.contains(Exercise.BURPEES)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Burpees removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.BURPEES);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.BURPEES);
                }
                break;
            case R.id.addJacks:
                if (newcustomworkouts.contains(Exercise.JACKS)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Jacks removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.JACKS);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.JACKS);
                }
                break;
            case R.id.addPushups:
                if (newcustomworkouts.contains(Exercise.PUSH_UPS)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Push ups removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.PUSH_UPS);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.PUSH_UPS);
                }
                break;
            case R.id.addChinups:
                if (newcustomworkouts.contains(Exercise.CHIN_UPS)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Chin ups removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.CHIN_UPS);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.CHIN_UPS);
                }
                break;
            case R.id.addBenchdips:
                if (newcustomworkouts.contains(Exercise.BENCH_DIPS)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Bench dips removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.BENCH_DIPS);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.BENCH_DIPS);
                }
                break;
            case R.id.addSitups:
                if (newcustomworkouts.contains(Exercise.SIT_UPS)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Sit ups removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.SIT_UPS);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.SIT_UPS);
                }
                break;
            case R.id.addPlanks:
                if (newcustomworkouts.contains(Exercise.PLANKS)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Planks removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.PLANKS);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.PLANKS);
                }
                break;
            case R.id.addLegraises:
                if (newcustomworkouts.contains(Exercise.LEG_RAISES)){
                    ((Button)v).setText("Add");
                    Toast.makeText(getContext(), "Leg raises removed from workout plan", Toast.LENGTH_SHORT).show();
                    newcustomworkouts.remove(Exercise.LEG_RAISES);
                }else{
                    ((Button)v).setText("Remove");
                    newcustomworkouts.add(Exercise.LEG_RAISES);
                }
                break;

            case R.id.customworkoutSave:
                if(newcustomworkouts.isEmpty()){
                    Toast.makeText(getContext(), "You didn't add any exercises!", Toast.LENGTH_SHORT).show();
                }
                else if (user.getWorkoutnames().containsKey(workoutName.getText().toString())){
                    Toast.makeText(getContext(), "This name already exists! Change the name to something else", Toast.LENGTH_SHORT).show();
                }else{
                    user.addWorkouts(workoutName.getText().toString(), newcustomworkouts);
                    user.addtoWorkoutList(workoutName.getText().toString());
                    user.saveWorkout();
                    Toast.makeText(getContext(), "You saved your workout! You named it: "+ workoutName.getText().toString() , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.customworkoutBack:
                openPreworkoutFrag();
                break;
        }

    }

    public void openPreworkoutFrag(){
        Fragment selectedFragment = new PreworkoutFrag();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, selectedFragment).commit();
    }

}
