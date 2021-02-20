package com.example.nekogains;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.PriorityQueue;

public class WorkoutActivity extends AppCompatActivity {

    PriorityQueue<Exercise> currentPlan;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //for now, clears database on start until login system is uninitialized
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        currentPlan = (PriorityQueue<Exercise>)getIntent().getExtras().getSerializable("PLAN");
        int userID = (int)getIntent().getExtras().getSerializable("ID");
        user = new User(DatabaseHelper.getInstance(MainActivity.getContext()), userID);
        user.newDay();
        user.updateDay(user.getLastDay(), "WEIGHT", Float.toString(user.getWeight()));
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new TutorialFrag()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void replaceFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frag_container, fragment)
                .commit();
    }

    public User getUser() {
        return user;
    }

    public int getExerciseReps() {
        return user.getExerciseReps(currentPlan.peek());
    }

    public int getExerciseSets() {
        return currentPlan.peek().getSets();
    }

    public String getExerciseName() {
        return currentPlan.peek().getName();
    }

    public String getExerciseDescription() {return currentPlan.peek().getDescription();}

    public Exercise getExercise() { return currentPlan.peek(); }

    public void finishExercise() {
        currentPlan.remove(currentPlan.peek());
    }

    public boolean isFinished() {
        return currentPlan.isEmpty();
    }


}
