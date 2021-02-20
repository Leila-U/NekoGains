package com.example.nekogains;

        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;

public class EndWorkoutFrag extends Fragment {

    WorkoutActivity activity;
    View view;
    User user;
    private TextView xpText;
    private TextView moneyText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.endworkout_frag, container, false);
        xpText = view.findViewById(R.id.xp);
        moneyText = view.findViewById(R.id.money);
        Button button = view.findViewById(R.id.finishButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toMain();
            }
        });
        activity = ((WorkoutActivity)this.getActivity());
        user = activity.getUser();

        user.addDaily();

        if(user.checkerDaily()){
            user.addMoney(200);
            user.increaseXp(200);
            user.resetDaily();
            setXpText(200);
            setMoneyText(200);
            user.saveDaily();
            user.updateDaily();
        }
        else{
            user.addMoney(100);
            user.increaseXp(100);
            setXpText(100);
            setMoneyText(100);
            user.saveDaily();
            user.updateDaily();
        }




        return view;
    }

    public void setXpText(int amount){xpText.setText("XP Gained: "+amount);}
    public void setMoneyText(int amount){moneyText.setText("Money Gained: " +amount);}
    private void toMain() {
        activity.finish();
    }
}
