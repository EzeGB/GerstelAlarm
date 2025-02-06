package com.example.gerstelalarm;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AlarmRecyclerViewInterface{

    ArrayList<AlarmModel> alarmModels = new ArrayList<>();
    Alarm_RecycleViewAdapter alarmAdapter;
    Dialog eliminateAlarmDialog;

    ActivityResultLauncher<Intent> editAlarmLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 1){
                        Intent info = result.getData();
                        if (info != null){
                            int position = info.getIntExtra("Position", 0);
                            boolean create = info.getBooleanExtra("Create", false);
                            String newName = info.getStringExtra("AlarmName");
                            String newHours = info.getStringExtra("Hours");
                            String newMinutes = info.getStringExtra("Minutes");
                            if (create) {
                                alarmModels.add(new AlarmModel(newName, newHours, newMinutes));
                                alarmAdapter.notifyItemInserted(position);
                            } else {
                                alarmModels.get(position).setAll(newName,newHours,newMinutes);
                                alarmAdapter.notifyItemChanged(position);
                            }
                            updateAlarmsInformation();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpAlarmRecyclerView();
        setUpEliminateAlarmDialog();
    }

    private void setUpAlarmModels (){
        SharedPreferences sharedAlarmPreferences = getApplicationContext().getSharedPreferences("alarmsPreferences", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedAlarmPreferences.getString("alarmData", null);
        Type type = new TypeToken<ArrayList<AlarmModel>>(){
        }.getType();
        Log.d("Looking",json);
        alarmModels=gson.fromJson(json,type);
        if (alarmModels==null){
            alarmModels=new ArrayList<>();
        }
    }

    public void updateAlarmsInformation(){
        SharedPreferences sharedAlarmPreferences = getApplicationContext().getSharedPreferences("alarmsPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedAlarmPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alarmModels);
        editor.putString("alarmData", json);
        editor.apply();
    }

    @Override
    public void onAlarmClick(int position, boolean create) {
        Intent createAlarm = new Intent(this, CreateAlarm.class);
        createAlarm.putExtra("Position", position);
        createAlarm.putExtra("Create", create);
        if (!create){
            String name = alarmModels.get(position).getAlarmName();
            String hours = alarmModels.get(position).getAlarmHours();
            String minutes = alarmModels.get(position).getAlarmMinutes();
            boolean [] weekDays = new boolean[7];
            Arrays.fill(weekDays,false);
            createAlarm.putExtra("AlarmName", name);
            createAlarm.putExtra("Hours", hours);
            createAlarm.putExtra("Minutes", minutes);
            createAlarm.putExtra("WeekDays",weekDays);
        }
        editAlarmLauncher.launch(createAlarm);
    }

    @Override
    public void onAlarmLongClick(int position) {
        eliminateAlarmDialog.show();
        Button cancel = eliminateAlarmDialog.findViewById(R.id.buttonCancelEliminate);
        Button eliminate = eliminateAlarmDialog.findViewById(R.id.buttonEliminate);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminateAlarmDialog.dismiss();
            }
        });

        eliminate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmModels.remove(position);
                alarmAdapter.notifyItemRemoved(position);
                updateAlarmsInformation();
                eliminateAlarmDialog.dismiss();
            }
        });
    }

    public void createAlarmClick (View view){
        if (alarmModels.isEmpty()){
            onAlarmClick(0, true);
        } else {
            onAlarmClick(alarmModels.size(), true);
        }
    }

    public void setUpAlarmRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.alarmRecyclerView);
        setUpAlarmModels();
        alarmAdapter = new Alarm_RecycleViewAdapter(this, this, alarmModels);
        recyclerView.setAdapter(alarmAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setUpEliminateAlarmDialog(){
        eliminateAlarmDialog = new Dialog(MainActivity.this);
        eliminateAlarmDialog.setContentView(R.layout.dialog_box_eliminate_alarm);
        eliminateAlarmDialog.setCancelable(false);
    }
}