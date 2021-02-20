package com.example.nekogains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class SettingFrag extends Fragment {
    private View view;
    private User user;

    private Spinner habitSpinner;
    private ArrayAdapter<CharSequence> habitAdapter;
    private Spinner goalSpinner;
    private ArrayAdapter<CharSequence> goalAdapter;
    private Button saveButton1;

    //Text boxes that will be pre-filled with user info
    private EditText editName;
    private EditText editHeight;
    private EditText editWeight;
    private EditText editPetName;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting_frag, container, false);

        habitSpinner = view.findViewById(R.id.spinnerHabit);
        habitAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.habit_array, android.R.layout.simple_spinner_item);
        habitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        habitSpinner.setAdapter(habitAdapter);

        goalSpinner = view.findViewById(R.id.spinnerGoal);
        goalAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.goal_array, android.R.layout.simple_spinner_item);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalSpinner.setAdapter(goalAdapter);

        saveButton1 = view.findViewById(R.id.buttonSaveSettings1);
        //saveButton2 = view.findViewById(R.id.buttonSaveSettings2);
        saveButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveSettings();
            }
        });

        editName = view.findViewById(R.id.editName);
        editHeight = view.findViewById(R.id.editHeight);
        editWeight = view.findViewById(R.id.editWeight);
        editPetName = view.findViewById(R.id.editPetName);

        //Prefill the text boxes with user data for user convenience
        user = new User(DatabaseHelper.getInstance(MainActivity.getContext()), ((MainActivity)getActivity()).getAppUserId());
        editName.setText(user.getName());
        System.out.println(user.getHeight());
        editHeight.setText(Float.toString(user.getHeight()));
        editWeight.setText(Float.toString(user.getWeight()));
        editPetName.setText(user.getPet().getName());
        habitSpinner.setSelection(user.getHabits());
        goalSpinner.setSelection(user.getIntensity());

        return view;
    }

    public void saveSettings() {
        user.setName(editName.getText().toString());
        user.setHeight(editHeight.getText().toString());
        user.setWeight(editWeight.getText().toString());
        //NOTICE: Currently the pet name resets with every new User
        user.getPet().setName(editPetName.getText().toString());
        user.setHabits(String.valueOf(habitSpinner.getSelectedItemPosition()));
        user.setIntensity(String.valueOf(goalSpinner.getSelectedItemPosition()));

        //Show a pop up message to indicate that it saved successfully
        Toast.makeText(view.getContext(),"Settings saved",Toast.LENGTH_SHORT).show();
    }



}
