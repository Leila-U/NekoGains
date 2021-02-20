package com.example.nekogains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class Questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        Button button = findViewById(R.id.button_send);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                get_results();
            }
        });
    }

    public void get_results(){

        String q_name = ((EditText)findViewById(R.id.q_name)).getText().toString();

        String q_age = ((EditText)findViewById(R.id.q_age)).getText().toString();

        RadioGroup q_sex_group = findViewById(R.id.q_sex);
        RadioButton q_sex_button = findViewById(q_sex_group.getCheckedRadioButtonId());
        String q_sex = Integer.toString(q_sex_group.indexOfChild(q_sex_button));

        RadioGroup q_habit_group = findViewById(R.id.q_habit);
        RadioButton q_habit_button = findViewById(q_habit_group.getCheckedRadioButtonId());
        String q_habit = Integer.toString(q_habit_group.indexOfChild(q_habit_button));

        String q_weight = ((EditText)findViewById(R.id.q_weight)).getText().toString();
        String q_height = ((EditText)findViewById(R.id.q_height)).getText().toString();

        RadioGroup q_int_group = findViewById(R.id.q_intensity);
        RadioButton q_int_button = findViewById(q_int_group.getCheckedRadioButtonId());
        String q_intensity = Integer.toString(q_int_group.indexOfChild(q_int_button));

        String q_petname = ((EditText)findViewById(R.id.q_petname)).getText().toString();


        HashMap<String, String> results = new HashMap<>();
        results.put("NAME", q_name);
        results.put("SEX", q_sex);
        results.put("AGE", q_age);
        results.put("HABITS", q_habit);
        results.put("WEIGHT", q_weight);
        results.put("HEIGHT", q_height);
        results.put("INTENSITY", q_intensity);
        results.put("PETNAME", q_petname);

        for (String entry : results.values()) {
            if (entry.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Intent intent;
        //User user = new User(dbh, q1_name, q2_age, q3_habit, q4_weight, q4_height, q5_goal);

        intent = new Intent(this, MainActivity.class);
        intent.putExtra("RESULTS", results);
        setResult(MainActivity.REQUEST_CODE, intent);
        finish();
    }
}