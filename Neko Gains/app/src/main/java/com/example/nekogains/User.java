package com.example.nekogains;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;


import static com.example.nekogains.Exercise.*;

public class User implements Serializable {

    DatabaseHelper dbh;
    int id;

    private String name;

    private Exercise[] workoutPlan1 = {LUNGES, JACKS, PUSH_UPS, SIT_UPS, LEG_RAISES};
    private Exercise[] workoutPlan2 = {SQUATS, BURPEES, BENCH_DIPS, PLANKS, LEG_RAISES};
    private Exercise[] workoutPlan3 = {LUNGES, RUN, CHIN_UPS, SIT_UPS, LEG_RAISES};

    private static int daily;
    private static Pet pet;
    private static UserInventory userInventory = new UserInventory();
    private static Hashtable<String, ArrayList<Exercise>> workoutplans;// = new Hashtable<>();
    private static ArrayList<String> workoutlist; //= new ArrayList<>();

    public User(DatabaseHelper dbh, int id) {
        this.dbh = dbh;
        this.id = id;
        this.pet  = new Cat(dbh, id);
    }

    //Preworkout&CustomWorkout Code

    public void createDefaultWorkouts(){
        ArrayList<Exercise> workoutPlan1 = new ArrayList<>();
        ArrayList<Exercise> workoutPlan2 = new ArrayList<>();
        ArrayList<Exercise> workoutPlan3 = new ArrayList<>();
        for (Exercise e: getWorkoutPlan1()){
            workoutPlan1.add(e);
        }
        for (Exercise e: getWorkoutPlan2()){
            workoutPlan2.add(e);
        }
        for (Exercise e: getWorkoutPlan3()){
            workoutPlan3.add(e);
        }
        addWorkouts("workoutPlan1", workoutPlan1);
        addtoWorkoutList("workoutPlan1");
        addWorkouts("workoutPlan2", workoutPlan2);
        addtoWorkoutList("workoutPlan2");
        addWorkouts("workoutPlan3", workoutPlan3);
        addtoWorkoutList("workoutPlan3");
    }

    public void setWorkoutplans(Hashtable <String, ArrayList<Exercise>> table){
        workoutplans = table;
    }

    public void setWorkoutlist(ArrayList<String> names){
        workoutlist = names;
    }

    public void addExercise(ArrayList<Exercise> exercises,Exercise exercise){
        exercises.add(exercise);
    }

    public ArrayList<Exercise> getWorkouts(String name){return workoutplans.get(name); }

    public Hashtable<String, ArrayList<Exercise>> getWorkoutnames(){return workoutplans;}

    public void addWorkouts(String name, ArrayList<Exercise> exercises){workoutplans.put(name,exercises);}

    public void addtoWorkoutList(String name){
        workoutlist.add(name);
    }

    public ArrayList<String> getWorkoutlist(){ return workoutlist;}

    public void saveWorkout(){
        SharedPreferences.Editor editor = MainActivity.getSettings().edit();
        Gson gson = new Gson();
        Gson gson2 = new Gson();
        String json = gson.toJson(workoutlist);
        String json2 = gson2.toJson(workoutplans);
        editor.putString("workoutlist", json);
        editor.putString("workoutplans", json2);
        editor.apply();
    }

    public void loadWorkout(){
        Gson gson = new Gson();
        Gson gson2 = new Gson();
        String json = MainActivity.getSettings().getString("workoutlist", null);
        String json2 = MainActivity.getSettings().getString("workoutplans", null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        Type type2 = new TypeToken<Hashtable<String, ArrayList<Exercise>>>(){}.getType();
        workoutlist = gson.fromJson(json,type);
        workoutplans = gson2.fromJson(json2,type2);
        if (workoutlist == null){
            workoutlist = new ArrayList<>();
        }if(workoutplans == null){
            workoutplans = new Hashtable<>();
            createDefaultWorkouts();
        }
    }

    public void saveInventory(){
        SharedPreferences.Editor editor = MainActivity.getSettings().edit();
        Gson gson = new Gson();
        String json = gson.toJson(userInventory);
        editor.putString("userInventory", json);
        editor.apply();
    }

    public void loadInventory(){
        Gson gson = new Gson();
        String json = MainActivity.getSettings().getString("userInventory", null);
        Type type = new TypeToken<UserInventory>(){}.getType();
        userInventory = gson.fromJson(json,type);
        if (userInventory == null){
            userInventory = new UserInventory();
        }
    }

    public void saveDaily(){
        SharedPreferences.Editor editor = MainActivity.getSettings().edit();
        editor.putInt("daily", this.getDaily());
        editor.apply();
    }

    public void updateDaily(){
        this.daily = MainActivity.getSettings().getInt("daily", 0);
    }


    //GETTING ATTRIBUTES
    public String getName() { return dbh.getUserData(id, "NAME"); }

    public Pet getPet() { return this.pet; }

    public float getWeight() {
        return Float.parseFloat((dbh.getUserData(id, "WEIGHT")));
    }

    public float getHeight() {
        return Float.parseFloat((dbh.getUserData(id, "HEIGHT")));
    }

    public int getMoneyAmount() {
        return Integer.parseInt((dbh.getGameData(id, "MONEY")));
    }

    public int getXp() {
        return Integer.parseInt((dbh.getGameData(id, "EXPERIENCE")));
    }

    public UserInventory getUserInventory() { return this.userInventory; }

    public int getAge() {
        return Integer.parseInt((dbh.getUserData(id, "AGE")));
    }

    public int getSex() {
        return Integer.parseInt((dbh.getUserData(id, "SEX")));
    }

    public int getHabits() {
        return Integer.parseInt((dbh.getUserData(id, "HABITS")));
    }

    public int getIntensity() {
        return Integer.parseInt((dbh.getUserData(id, "INTENSITY")));
    }

    public int getDaily() {return this.daily;}

    public Exercise[] getWorkoutPlan1() {
        return workoutPlan1;
    }

    public Exercise[] getWorkoutPlan2() {
        return workoutPlan2;
    }

    public Exercise[] getWorkoutPlan3() {
        return workoutPlan3;
    }

    public int getExerciseReps(Exercise exercise) {
        CalculateUserScore calc = new CalculateUserScore();
        float score = calc.calculate(this);
        return exercise.getUserReps(score);
    }

    //CHANGING ATTRIBUTES
    public void setName(String name) {
        dbh.updateUserData(id, "NAME", name);
    }

    public void setWeight(String weight) {
        dbh.updateUserData(id, "WEIGHT", weight);
    }

    public void setHeight(String height) {
        dbh.updateUserData(id, "HEIGHT", height);
    }

    public void setHabits(String habit) { dbh.updateUserData(id, "HABITS", habit);}

    public void setIntensity(String intensity) { dbh.updateUserData(id, "INTENSITY", intensity);}

    public void addMoney(int amount) {
        int currentMoney = getMoneyAmount();
        dbh.updateGame(id, "MONEY", currentMoney+amount);
    }

    public void removeMoney(int amount){
        int balance = getMoneyAmount();
        dbh.updateGame(id, "MONEY", balance-amount);
    }

    public void increaseXp(int amount) {
        int xp = getXp();
        dbh.updateGame(id, "EXPERIENCE", xp+amount);
        this.pet.setLevel((int)Math.floor(xp/1000));
    }

    public void setDaily(int daily) {this.daily = daily;}

    //Calendar functions
    public int getLastDay() {
        return Integer.parseInt(dbh.getLastDay(id));
    }

    //Enters a new into workout calendar for current user
    public void newDay() {
        int lastday = this.getLastDay();
        dbh.insertNewDay(lastday+1, id);
        System.out.println(lastday);
    }

    //update workout or reps done for a given user on a given day
    public void updateDay(int day, String row, String contents) {
        dbh.updateDay(day, id, row, contents);
    }

    //get workout or reps done for a given user on a given day
    public String getUserCalendarData(int day, String row) {
        return dbh.getUserCalendarData(day, id, row);
    }


    //OTHER
    public float getBMI() {
        float height = this.getHeight()/100;
        return this.getWeight()/(height*height);
    }

    public boolean checkerDaily() {return this.daily == 5;}

    public void resetDaily() {this.daily = 0;}

    public void addDaily() {this.daily+=1;}

}