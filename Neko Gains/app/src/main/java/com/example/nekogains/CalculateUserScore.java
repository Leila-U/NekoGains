package com.example.nekogains;

import org.apache.commons.math3.distribution.*;

import java.util.ArrayList;
import java.util.List;

public class CalculateUserScore {

    ChiSquaredDistribution chi = new ChiSquaredDistribution(6);
    NormalDistribution norm = new NormalDistribution(22, 3);

    public float calculate(User user) {
        float result = 0;
        List<Float> scores = new ArrayList<>();
        float age = user.getAge();
        scores.add(Float.parseFloat(Double.toString((chi.density(age/5))/chi.density(4))));

        float sex = user.getSex();
        if (sex == 0) {
            sex = 0.6f;
        } else {
            sex = 0.4f;
        }
        scores.add(sex);

        float bmi = user.getBMI();
        scores.add(Float.parseFloat(Double.toString(norm.density(bmi)/norm.density(norm.getMean()))));

        float habits = user.getHabits()/3;
        scores.add(habits);

        float intensity = user.getIntensity()/2;
        scores.add(intensity);

        for (int i = 0; i < scores.size(); i++) {
            //System.out.println(scores.get(i));
            result += scores.get(i);
        }
        result /= scores.size();

        System.out.println("USER SCORE:");
        System.out.println(result);
        return result;
    }
}
