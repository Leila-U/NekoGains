package com.example.nekogains;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StoreFrag extends Fragment implements View.OnClickListener {
    private User user;
    private UserInventory userInventory;
    private View view;
    private TextView textView;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;

    private Integer bbcost = 4; //restores 10 hunger
    private Integer catfoodcost = 12; //restores 50 hunger
    private Integer fishcost = 7; //restores 30 hunger
    private Integer milkcost = 2; //restores 5 hunger
    private Integer clothingcost = 200;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        user = new User(DatabaseHelper.getInstance(MainActivity.getContext()), ((MainActivity)this.getActivity()).getAppUserId());
        user.loadInventory();
        userInventory = user.getUserInventory();

        view = inflater.inflate(R.layout.store_frag, container, false);
        textView = view.findViewById(R.id.money);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);

        setMoney(user.getMoneyAmount());
        //textView.setText("Money: "+ user.getMoneyAmount());
        System.out.println(user.getUserInventory().numofFood("blueberries"));
        return view;
    }

    public void setMoney(int money){
        textView.setText("Money: " + money);
    }

    public boolean checkMoney(int amount){
        return user.getMoneyAmount() > amount;
    }

    @Override
    public void onClick(View v){
        System.out.println(user.getUserInventory().numofFood("blueberries"));
        switch(v.getId()){
            case R.id.button1:
                if (user.getMoneyAmount() >= bbcost){
                    if (userInventory.hasFood("blueberries")){
                        userInventory.addFood("blueberries");
                    }else{
                        userInventory.createFood("blueberries");
                    }
                    user.removeMoney(bbcost);
                    setMoney(user.getMoneyAmount());
                    Toast.makeText(getContext(), userInventory.showFood(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.button2:
                if (user.getMoneyAmount() >= catfoodcost){
                    if (userInventory.hasFood("catfood")){
                        userInventory.addFood("catfood");
                    }else{
                        userInventory.createFood("catfood");
                    }
                    user.removeMoney(catfoodcost);
                    setMoney(user.getMoneyAmount());
                    Toast.makeText(getContext(), userInventory.showFood(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.button3:
                if (user.getMoneyAmount() >= fishcost){
                    if (userInventory.hasFood("fish")){
                        userInventory.addFood("fish");
                    }else{
                        userInventory.createFood("fish");
                    }
                    user.removeMoney(fishcost);
                    setMoney(user.getMoneyAmount());
                    Toast.makeText(getContext(), userInventory.showFood(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.button4:
                if (user.getMoneyAmount() >= milkcost){
                    if (userInventory.hasFood("milk")){
                        userInventory.addFood("milk");
                    }else{
                        userInventory.createFood("milk");
                    }
                    user.removeMoney(milkcost);
                    setMoney(user.getMoneyAmount());
                    Toast.makeText(getContext(), userInventory.showFood(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();}
                break;

            case R.id.button5:
                if (user.getMoneyAmount() >= clothingcost){
                    if (userInventory.hasShirt("yellowshirt")){
                        Toast.makeText(getContext(), "You already own this item!", Toast.LENGTH_SHORT).show();
                    }else{
                        userInventory.addShirt("yellowshirt");
                        ((Button)v).setText("Own");
                        user.removeMoney(clothingcost);
                        setMoney(user.getMoneyAmount());
                        Toast.makeText(getContext(), "You now own a yellow shirt!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.button6:
                if (user.getMoneyAmount() >= clothingcost){
                    if (userInventory.hasShirt("blueshirt")){
                        Toast.makeText(getContext(), "You already own this item!", Toast.LENGTH_SHORT).show();
                    }else{
                        userInventory.addShirt("blueshirt");
                        ((Button)v).setText("Own");
                        user.removeMoney(clothingcost);
                        setMoney(user.getMoneyAmount());
                        Toast.makeText(getContext(), "You now own a blue shirt!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.button7:
                if (user.getMoneyAmount() >= clothingcost){
                    if (userInventory.hasPant("orangepants")){
                        Toast.makeText(getContext(), "You already own this item!", Toast.LENGTH_SHORT).show();
                    }else{
                        userInventory.addPant("orangepants");
                        ((Button)v).setText("Own");
                        user.removeMoney(clothingcost);
                        setMoney(user.getMoneyAmount());
                        Toast.makeText(getContext(), "You now own some orange pants!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.button8:
                if (user.getMoneyAmount() >= clothingcost){
                    if (userInventory.hasPant("redpants")){
                        Toast.makeText(getContext(), "You already own this item!", Toast.LENGTH_SHORT).show();
                    }else{
                        userInventory.addPant("redpants");
                        ((Button)v).setText("Own");
                        user.removeMoney(clothingcost);
                        setMoney(user.getMoneyAmount());
                        Toast.makeText(getContext(), "You now own some blue pants!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();}
                break;

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        user.saveInventory();
    }
}
