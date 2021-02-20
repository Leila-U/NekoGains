package com.example.nekogains;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class PreworkoutFrag extends Fragment implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter mAdapter;
    View view;
    User user;
    Spinner spinner;
    String[] workoutlist;
    PriorityQueue<Exercise> currentPlan;
    private Button startbutton;
    private Button customworkoutbutton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        user = new User(DatabaseHelper.getInstance(MainActivity.getContext()), ((MainActivity)this.getActivity()).getAppUserId());
        currentPlan = new PriorityQueue<>();
        view = inflater.inflate(R.layout.preworkout_frag, container, false);
        user.loadWorkout();
        spinner = view.findViewById(R.id.spinner1);
        workoutlist = user.getWorkoutlist().toArray(new String[0]);
        System.out.println(workoutlist);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.workoutplans,android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, workoutlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        recyclerView = view.findViewById(R.id.recycleView);

        customworkoutbutton = view.findViewById(R.id.customworkoutbutton);
        customworkoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = null;
                selectedFragment = new CustomWorkoutFrag();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, selectedFragment).commit();
            }
        });
        startbutton = view.findViewById(R.id.startbutton);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWorkoutActivity();
            }
        });

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String var = parent.getItemAtPosition(position).toString();
        ArrayList<preworkout_item> list = new ArrayList<>();
        currentPlan.clear();
        if (user.getWorkoutnames().containsKey(var)) {
            for(Exercise e: user.getWorkoutnames().get(var)){
                //Toast.makeText(getContext(), var, Toast.LENGTH_SHORT).show();
                list.add(new preworkout_item(e.name().replace("_", " "), e, e.getSets(), user));
                //fill up queue with exercises for this plan
                currentPlan.add(e);
            }
        }
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new preworkoutAdapter(list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void openWorkoutActivity(){
        Intent intent = new Intent(getContext(), WorkoutActivity.class);
        intent.putExtra("PLAN", currentPlan);
        intent.putExtra("ID", ((MainActivity)this.getActivity()).getAppUserId());
        startActivity(intent);
    }


}
