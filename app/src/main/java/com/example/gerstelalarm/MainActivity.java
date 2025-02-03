package com.example.gerstelalarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

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

public class MainActivity extends AppCompatActivity implements AlarmRecyclerViewInterface{

    ArrayList<AlarmModel> alarmModels = new ArrayList<>();
    Alarm_RecycleViewAdapter alarmAdapter;

    ActivityResultLauncher<Intent> editAlarmLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 1){
                        Intent info = result.getData();
                        if (info != null){
                            String newName = info.getStringExtra("AlarmName");
                            int position = info.getIntExtra("Position", 0);
                            if (newName!=null) {
                                alarmModels.get(position).setAlarmName(newName);
                                alarmAdapter.notifyItemChanged(position);
                                updateAlarmsInformation();
                            }
                        }
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> createAlarmLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 1){
                        Intent info = result.getData();
                        if (info != null){
                            String newName = info.getStringExtra("AlarmName");
                            int position = info.getIntExtra("Position", 0);
                            if (newName!=null) {
                                alarmModels.add(new AlarmModel(newName));
                                alarmAdapter.notifyItemInserted(position);
                                updateAlarmsInformation();
                            }
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

        RecyclerView recyclerView = findViewById(R.id.alarmRecyclerView);
        setUpAlarmModels();
        alarmAdapter = new Alarm_RecycleViewAdapter(this, this, alarmModels);
        recyclerView.setAdapter(alarmAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpAlarmModels (){
        SharedPreferences sharedAlarmPreferences = getApplicationContext().getSharedPreferences("alarmsPreferences", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedAlarmPreferences.getString("alarmData", null);
        Type type = new TypeToken<ArrayList<AlarmModel>>(){
        }.getType();
        alarmModels=gson.fromJson(json,type);
        if (alarmModels==null){
            alarmModels=new ArrayList<AlarmModel>();
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
        if (!create){
            String name = alarmModels.get(position).getAlarmName();
            createAlarm.putExtra("AlarmName", name);
            editAlarmLauncher.launch(createAlarm);
        } else {
            createAlarmLauncher.launch(createAlarm);
        }
    }

    public void createAlarmClick (View view){
        if (alarmModels.isEmpty()){
            onAlarmClick(0, true);
        } else {
            onAlarmClick(alarmModels.size(), true);
        }
    }
}