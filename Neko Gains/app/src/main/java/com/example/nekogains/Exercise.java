package com.example.nekogains;

import android.util.Pair;

public enum Exercise {
    LUNGES (0.9f, 3, new Pair(9, 15), ("While standing," +
            " step forward with one leg and bend both knees to 90 degrees." +
            " Unbend your knees until straight and return to standing position. Repeat the process with your other leg.")),
    SQUATS (0.3f, 3, new Pair(10, 20),("While standing, bend both knees 90 degrees while shooting your hips back." +
            " Then unbend both knees and return to standing position and repeat the process.") ),
    RUN (15f, 1, new Pair(30, 60),("With adamant space, proceed to jog at a consistent pace.") ),
    BURPEES (13f, 3, new Pair(3, 10),("While standing, jump up." +
            " When landing, land in a squatting position with your hands to the ground." +
            " With your hands on the floor, kick your legs backwards into a push up position." +
            " Reverse the process by going back into squatting position and repeat by jumping up.") ),
    JACKS (9f, 3, new Pair(10, 30),("While standing, jump into the air and bring both hands together above our head" +
            " while spreading both legs 45 degrees apart simultaneously. After landing in that position," +
            " jump back into the air and return to standing position.") ),
    PUSH_UPS (1, 3, new Pair(10, 20),("While placing your hands and feet on the ground," +
            " rest your weight on both hands and feet." +
            " Place your arms 45 degrees from your body. Proceed to bend your elbows and lower your body until your chest touches the floor." +
            " Then begin to unbend your elbows and lift yourself into starting postion. Repeat the process.") ),
    CHIN_UPS (1, 3, new Pair(5, 15), ("While standing, grab the bar with your palms facing towards you. Continue by lifting your feet off the floor." +
            "In this position, lift your body with your arms until your chin in leveled with the bar." +
            " Straighten your arms and brings your body back down without touching the floor. Repeat the process.") ),
    BENCH_DIPS (3, 3, new Pair(10, 20), ("While suspending yourself off a bench with your feet on the ground and your hands on the bench," +
            "proceed to lower yourself and bend your elbows to 90 degrees." +
            " Afterwards, unbend your self and lift youself into the starting position. Repeat this process.") ),
    SIT_UPS (0.3f, 3, new Pair(10, 25), ("While laying on your back, bend your knees 45 degrees with your feet on the ground." +
            " With your arms across your chest a holding your shoulders pull yourself up and closer to your knees." +
            " Slowly lower yourself back to the ground and repeat the process. ") ),
    PLANKS (3.5f, 3, new Pair(2, 5), ("While laying face down, suspend yourself on your elbows and feet." +
            " While keeping your back straight, proceed to hold this position for a set amount of time. ") ),
    LEG_RAISES (0.7f, 3, new Pair(10, 20), ("While laying on your back, place your arms 45 degrees away from your body while keeping your legs straight." +
            "Begin to lift your legs no longer possible and then slowly lower them to the ground. Repeat the process.") )
    ;

    private final float calories;
    private final int sets;
    private final Pair<Integer, Integer> reps;
    private final String description;

    Exercise(float calories, int sets, Pair<Integer, Integer> reps, String description) {
        this.calories = calories;
        this.sets = sets;
        this.reps = reps;
        this.description = description;
    }

    public String getName() {
        return this.name();
    }

    public float getCalories() {
        return calories;
    }

    public int getSets() {
        return sets;
    }

    public String getDescription(){return description;}

    public Pair<Integer, Integer> getReps() {
        return reps;
    }

    public int getUserReps(float score) {
        int min = reps.first;
        int max = reps.second;
        return Math.round(score*(max - min) + min);
    }


}
