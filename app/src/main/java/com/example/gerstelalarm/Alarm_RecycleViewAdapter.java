package com.example.gerstelalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Alarm_RecycleViewAdapter extends RecyclerView.Adapter<Alarm_RecycleViewAdapter.MyViewHolder>{
    private final AlarmRecyclerViewInterface alarmRecyclerViewInterface;

    Context ctx;
    ArrayList<AlarmModel> alarmModels;
    public Alarm_RecycleViewAdapter(AlarmRecyclerViewInterface alarmRecyclerViewInterface, Context ctx, ArrayList<AlarmModel> alarmModels){
        this.alarmRecyclerViewInterface = alarmRecyclerViewInterface;
        this.ctx=ctx;
        this.alarmModels=alarmModels;
    }

    @NonNull
    @Override
    public Alarm_RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate layout (give look to rows?)
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.alarm_recycler, parent, false);
        return new Alarm_RecycleViewAdapter.MyViewHolder(view, alarmRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Alarm_RecycleViewAdapter.MyViewHolder holder, int position) {
        //assign values to views
        //based on position of recycler
        AlarmModel alarmModel = alarmModels.get(position);
        holder.textViewName.setText(alarmModel.getAlarmName());
        holder.textViewHours.setText(alarmModel.getAlarmHours());
        holder.textViewMinutes.setText(alarmModel.getAlarmMinutes());
    }

    @Override
    public int getItemCount() {
        //number of items displayed
        return alarmModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grabbing views from layout file

        TextView textViewName, textViewHours, textViewMinutes;
        public MyViewHolder(@NonNull View itemView, AlarmRecyclerViewInterface alarmRecyclerViewInterface) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewHours = itemView.findViewById(R.id.textViewHours);
            textViewMinutes = itemView.findViewById(R.id.textViewMinutes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alarmRecyclerViewInterface!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            alarmRecyclerViewInterface.onAlarmClick(position, false);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (alarmRecyclerViewInterface!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            alarmRecyclerViewInterface.onAlarmLongClick(position);
                        }
                    }
                    return true;
                }
            });
        }
    }
}
