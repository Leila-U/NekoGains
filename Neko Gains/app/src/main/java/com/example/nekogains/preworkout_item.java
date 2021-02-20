package com.example.nekogains;

import android.util.Pair;

public class preworkout_item {
    private String name;
    private int reps;
    private int sets;


    public  preworkout_item(String name,Exercise exercise ,int sets, User user){
        this.name = name;
        this.reps = user.getExerciseReps(exercise);
        this.sets = sets;
    }

    public String getName(){
        return name;
    }

    public String getReps(){
        //System.out.println(reps);
        return Integer.toString(reps);
    }

    public String getSets(){
        return Integer.toString(sets);
    }
}
