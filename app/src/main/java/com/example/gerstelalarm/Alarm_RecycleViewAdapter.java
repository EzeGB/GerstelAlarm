package com.example.gerstelalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Alarm_RecycleViewAdapter extends RecyclerView.Adapter<Alarm_RecycleViewAdapter.MyViewHolder>{

    Context ctx;
    ArrayList<AlarmModel> alarmModels;
    public Alarm_RecycleViewAdapter(Context ctx, ArrayList<AlarmModel> alarmModels){
        this.ctx=ctx;
        this.alarmModels=alarmModels;
    }

    @NonNull
    @Override
    public Alarm_RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate layout (give look to rows?)
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.alarm_recycler, parent, false);
        return new Alarm_RecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Alarm_RecycleViewAdapter.MyViewHolder holder, int position) {
        //assign values to views
        //based on position of recycler
        holder.button.setText(alarmModels.get(position).getAlarmName());
    }

    @Override
    public int getItemCount() {
        //number of items displayed
        return alarmModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grabbing views from layout file

        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            button = itemView.findViewById(R.id.button3);
        }
    }
}
