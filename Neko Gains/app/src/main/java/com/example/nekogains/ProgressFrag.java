package com.example.nekogains;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class ProgressFrag extends Fragment implements View.OnClickListener {
    private LineGraphSeries<DataPoint> series;
    private ImageButton lungesButton;
    private LineGraphSeries<DataPoint> lunges_series = new LineGraphSeries<>();

    private ImageButton squatsButton;
    private LineGraphSeries<DataPoint> squats_series = new LineGraphSeries<>();

    private ImageButton runButton;
    private LineGraphSeries<DataPoint> run_series = new LineGraphSeries<>();


    private ImageButton burpeesButton;
    private LineGraphSeries<DataPoint> burpees_series = new LineGraphSeries<>();

    private ImageButton jacksButton;
    private LineGraphSeries<DataPoint> jacks_series = new LineGraphSeries<>();

    private ImageButton pushUpsButton;
    private LineGraphSeries<DataPoint> pushups_series = new LineGraphSeries<>();


    private ImageButton chinUpsButton;
    private LineGraphSeries<DataPoint> chin_ups_series = new LineGraphSeries<>();

    private ImageButton benchDipsButton;
    private LineGraphSeries<DataPoint> bench_dips_series = new LineGraphSeries<>();

    private ImageButton sitUpsButton;
    private LineGraphSeries<DataPoint> sit_ups_series = new LineGraphSeries<>();


    private ImageButton planksButton;
    private LineGraphSeries<DataPoint> planks_series = new LineGraphSeries<>();

    private ImageButton legRaisesButton;
    private LineGraphSeries<DataPoint> leg_raises_series = new LineGraphSeries<>();

    private ImageButton weightLossButton;
    private LineGraphSeries<DataPoint> weight_loss_series = new LineGraphSeries<>();


    private View view;
    private User user;
    private PopupWindow lungesPopup;
    private PopupWindow squatsPopup;
    private PopupWindow runPopup;

    private PopupWindow burpeesPopup;
    private PopupWindow jacksPopup;
    private PopupWindow pushUpsPopup;

    private PopupWindow chinUpsPopup;
    private PopupWindow benchDipsPopUp;
    private PopupWindow sitUpsPopup;

    private PopupWindow planksPopup;
    private PopupWindow legRaisesPopup;
    private PopupWindow weightLossPopup;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        user = new User(DatabaseHelper.getInstance(MainActivity.getContext()), ((MainActivity)this.getActivity()).getAppUserId());
        System.out.println("Created view + user for progress Frag!");
        view = inflater.inflate(R.layout.progress_frag, container, false);

        lungesButton = view.findViewById(R.id.lungesButton);
        addPoint(lunges_series, 0, 0);
        lunges_series.setColor(Color.BLACK);
        lunges_series.setThickness(5);

        squatsButton = view.findViewById(R.id.squatsButton);
        addPoint(squats_series, 0, 0);
        runButton = view.findViewById(R.id.runButton);
        addPoint(run_series, 0, 0);
        run_series.setColor(Color.BLACK);
        run_series.setThickness(5);

        burpeesButton = view.findViewById(R.id.burpeesButton);
        addPoint(burpees_series, 0, 0);
        burpees_series.setColor(Color.BLACK);
        burpees_series.setThickness(5);

        jacksButton = view.findViewById(R.id.jacksButton);
        addPoint(jacks_series, 0, 0);
        jacks_series.setColor(Color.BLACK);
        jacks_series.setThickness(5);

        pushUpsButton = view.findViewById(R.id.pushUpsButton);
        addPoint(pushups_series, 0, 0);
        pushups_series.setColor(Color.BLACK);
        pushups_series.setThickness(5);

        chinUpsButton = view.findViewById(R.id.chinUpsButton);
        addPoint(chin_ups_series, 0, 0);
        chin_ups_series.setColor(Color.BLACK);
        chin_ups_series.setThickness(5);

        benchDipsButton = view.findViewById(R.id.benchDipsButton);
        addPoint(bench_dips_series, 0, 0);
        bench_dips_series.setColor(Color.BLACK);
        bench_dips_series.setThickness(5);

        sitUpsButton = view.findViewById(R.id.sitUpsButton);
        addPoint(sit_ups_series, 0,0);
        sit_ups_series.setColor(Color.BLACK);
        sit_ups_series.setThickness(5);

        planksButton = view.findViewById(R.id.planksButton);
        addPoint(planks_series, 0,0);
        planks_series.setColor(Color.BLACK);
        planks_series.setThickness(5);

        legRaisesButton = view.findViewById(R.id.legRaisesButton);
        addPoint(leg_raises_series, 0,0);
        leg_raises_series.setColor(Color.BLACK);
        leg_raises_series.setThickness(5);

        weightLossButton = view.findViewById(R.id.weightLossButton);
        weight_loss_series.setColor(Color.BLACK);
        weight_loss_series.setThickness(5);
        
        getLastWeek();

        lungesButton.setOnClickListener(this);
        squatsButton.setOnClickListener(this);
        runButton.setOnClickListener(this);

        burpeesButton.setOnClickListener(this);
        jacksButton.setOnClickListener(this);
        pushUpsButton.setOnClickListener(this);

        chinUpsButton.setOnClickListener(this);
        benchDipsButton.setOnClickListener(this);
        sitUpsButton.setOnClickListener(this);

        planksButton.setOnClickListener(this);
        legRaisesButton.setOnClickListener(this);
        weightLossButton.setOnClickListener(this);


        return view;
    }

    public void addPoint (LineGraphSeries<DataPoint> line, int x, double y ){
            line.appendData(new DataPoint(x,y), true, 752);
            System.out.println("adding... x:" + x +"y:"+ y +"to" + line.toString());
    }

    public void getLastWeek(){
        int lastday = user.getLastDay();
        //System.out.println("lastday: "+ lastday);
        for (int i = 1; i <= lastday; i++){
            String warmup = user.getUserCalendarData(i, "WARMUP");
            System.out.println(warmup);
            if (warmup!= null) {
                if (warmup.equals("LUNGES")) {
                    addPoint(lunges_series, i, Integer.parseInt(user.getUserCalendarData(i, user.getUserCalendarData(i, "WREPS"))));
                    addPoint(squats_series, i, 0);

                } else {
                    addPoint(squats_series, i, Integer.parseInt(user.getUserCalendarData(i, user.getUserCalendarData(i, "WREPS"))));
                    addPoint(lunges_series, i, 0);
                }
            }
            else{
                addPoint(squats_series, i, 0);
                addPoint(lunges_series, i, 0);
            }
            String cardio = user.getUserCalendarData(i, "CARDIO");
            if (cardio!= null) {
                if (cardio.equals("RUN")) {
                    addPoint(run_series, i, Integer.parseInt(user.getUserCalendarData(i, user.getUserCalendarData(i, "CREPS"))));
                    addPoint(jacks_series, i, 0);
                    addPoint(burpees_series, i, 0);
                } else if (cardio.equals("JACKS")) {
                    addPoint(run_series, i, 0);
                    addPoint(jacks_series, i, Integer.parseInt(user.getUserCalendarData(i, user.getUserCalendarData(i, "CREPS"))));
                    addPoint(burpees_series, i, 0);
                } else {
                    addPoint(run_series, i, 0);
                    addPoint(jacks_series, i, 0);
                    addPoint(burpees_series, i, Integer.parseInt(user.getUserCalendarData(i, user.getUserCalendarData(i, "CREPS"))));
                }
            }
            else{
                addPoint(run_series, i, 0);
                addPoint(jacks_series, i, 0);
                addPoint(burpees_series, i, 0);
            }
            String arms = user.getUserCalendarData(i, "ARMS");
            if (arms != null) {
                if (arms.equals("PUSH_UPS")) {
                    addPoint(pushups_series, i, Integer.parseInt(user.getUserCalendarData(i, user.getUserCalendarData(i, "AREPS"))));
                    addPoint(chin_ups_series, i, 0);
                    addPoint(bench_dips_series, i, 0);
                } else if (arms.equals("CHIN_UPS")) {
                    addPoint(pushups_series, i, 0);
                    addPoint(chin_ups_series, i, Integer.parseInt(user.getUserCalendarData(i, user.getUserCalendarData(i, "AREPS"))));
                    addPoint(bench_dips_series, i, 0);
                } else {
                    addPoint(pushups_series, i, 0);
                    addPoint(chin_ups_series, i, 0);
                    addPoint(bench_dips_series, i, Integer.parseInt(user.getUserCalendarData(i, user.getUserCalendarData(i, "AREPS"))));

                }
            }
            else{
                addPoint(pushups_series, i, 0);
                addPoint(chin_ups_series, i, 0);
                addPoint(bench_dips_series, i, 0);
            }
            String core = user.getUserCalendarData(i, "CORE");
            if (core != null) {
                if (core.equals("SIT_UPS")) {
                    addPoint(sit_ups_series, i, Integer.parseInt(user.getUserCalendarData(i, user.getUserCalendarData(i, "COREPS"))));
                    addPoint(planks_series, i, 0);
                } else {
                    addPoint(sit_ups_series, i, 0);
                    addPoint(planks_series, i, Integer.parseInt(user.getUserCalendarData(i, user.getUserCalendarData(i, "COREPS"))));
                }
            }
            else{
                addPoint(planks_series, i, 0);
                addPoint(sit_ups_series, i, 0);
            }
            if (user.getUserCalendarData(i, "LREPS") != null) {
                addPoint(leg_raises_series, i, Integer.parseInt(user.getUserCalendarData(i, "LREPS")));
            }else{
                addPoint(leg_raises_series, i, 0);
            }
            if(user.getUserCalendarData(i, "WEIGHT")!=null) {
                addPoint(weight_loss_series, i, (Double.parseDouble(user.getUserCalendarData(i, "WEIGHT"))));
            }
            else{
                addPoint(weight_loss_series, i, 0);
            }
        }
    }
    @Override
    public void onClick(View v) {
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //System.out.println("Something was pressed owo");
        View popupView;
        Button closeButton;

        switch (v.getId()) {
            case R.id.lungesButton:
                //System.out.println("The lunges button was pressed!");
                popupView = inflater.inflate(R.layout.lunges_popup, null);
                GraphView lungeGV = popupView.findViewById(R.id.lungesGraph);

                lungeGV.addSeries(lunges_series);
                lungesPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lungesPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lungesPopup.dismiss();
                    }
                });

                break;
            case R.id.squatsButton:
                popupView = inflater.inflate(R.layout.squats_popup, null);
                GraphView squatsGV = popupView.findViewById(R.id.squatsGraph);
                squatsGV.addSeries(squats_series);

                squatsPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                squatsPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        squatsPopup.dismiss();
                    }
                });
                break;
            case R.id.runButton:
                popupView = inflater.inflate(R.layout.run_popup, null);
                GraphView runGV = popupView.findViewById(R.id.runGraph);
                runGV.addSeries(run_series);

                runPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                runPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runPopup.dismiss();
                    }
                });
                break;
            case R.id.burpeesButton:
                popupView = inflater.inflate(R.layout.burpees_popup, null);
                GraphView burpeesGV = popupView.findViewById(R.id.burpeesGraph);
                burpeesGV.addSeries(burpees_series);

                burpeesPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                burpeesPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        burpeesPopup.dismiss();
                    }
                });
                break;
            case R.id.jacksButton:
                popupView = inflater.inflate(R.layout.jacks_popup, null);
                GraphView jacksGV = popupView.findViewById(R.id.jacksGraph);
                jacksGV.addSeries(jacks_series);

                jacksPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                jacksPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        jacksPopup.dismiss();
                    }
                });
                break;
            case R.id.pushUpsButton:
                popupView = inflater.inflate(R.layout.pushups_popup, null);
                GraphView pushGV = popupView.findViewById(R.id.pushUpsGraph);
                pushGV.addSeries(pushups_series);

                pushUpsPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                pushUpsPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pushUpsPopup.dismiss();
                    }
                });
                break;
            case R.id.chinUpsButton:
                popupView = inflater.inflate(R.layout.chinups_popup, null);
                GraphView chinGV = popupView.findViewById(R.id.chinUpsGraph);
                chinGV.addSeries(chin_ups_series);

                chinUpsPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                chinUpsPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chinUpsPopup.dismiss();
                    }
                });
                break;
            case R.id.benchDipsButton:
                popupView = inflater.inflate(R.layout.benchdips_popup, null);
                GraphView benchGV = popupView.findViewById(R.id.benchDipsGraph);
                benchGV.addSeries(bench_dips_series);

                benchDipsPopUp = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                benchDipsPopUp.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        benchDipsPopUp.dismiss();
                    }
                });
                break;
            case R.id.sitUpsButton:
                popupView = inflater.inflate(R.layout.situps_popup, null);
                GraphView sitGV = popupView.findViewById(R.id.sitUpsGraph);
                sitGV.addSeries(sit_ups_series);

                sitUpsPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                sitUpsPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sitUpsPopup.dismiss();
                    }
                });
                break;
            case R.id.planksButton:
                popupView = inflater.inflate(R.layout.planks_popup, null);
                GraphView planksGV = popupView.findViewById(R.id.planksGraph);
                planksGV.addSeries(planks_series);

                planksPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                planksPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        planksPopup.dismiss();
                    }
                });
                break;
            case R.id.legRaisesButton:
                popupView = inflater.inflate(R.layout.legraises_popup, null);
                GraphView legGV = popupView.findViewById(R.id.legRaisesGraph);
                legGV.addSeries(leg_raises_series);

                legRaisesPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                legRaisesPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        legRaisesPopup.dismiss();
                    }
                });
                break;
            case R.id.weightLossButton:
                popupView = inflater.inflate(R.layout.weightloss_popup, null);
                GraphView weightGV = popupView.findViewById(R.id.weightLossGraph);
                weightGV.addSeries(weight_loss_series);

                weightLossPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                weightLossPopup.showAtLocation(view.findViewById(R.id.ProgressScreen), Gravity.CENTER, 0, 0);
                closeButton = popupView.findViewById(R.id.button9);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        weightLossPopup.dismiss();
                    }
                });
                break;
        }
    }
}

