package com.example.nekogains;

import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class preworkoutAdapter extends RecyclerView.Adapter<preworkoutAdapter.preworkoutViewHolder> {
    private ArrayList<preworkout_item> preworkoutItemArrayList;

    public static class preworkoutViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView reps;
        public TextView sets;

        public preworkoutViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.textname);
            reps = view.findViewById(R.id.textreps);
            sets = view.findViewById(R.id.textsets);
        }
    }

    public preworkoutAdapter(ArrayList<preworkout_item> preworkout_list){
        preworkoutItemArrayList = preworkout_list;
    }
    @Override
    public preworkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preworkout_item, parent, false);
        preworkoutViewHolder evh = new preworkoutViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(preworkoutViewHolder holder, int position) {
        preworkout_item currentItem = preworkoutItemArrayList.get(position);

        holder.name.setText(currentItem.getName());
        holder.reps.setText(currentItem.getReps());
        holder.sets.setText(currentItem.getSets());
    }

    @Override
    public int getItemCount() {
        return preworkoutItemArrayList.size();
    }
}
