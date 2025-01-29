package com.example.gerstelalarm;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AlarmRecyclerViewInterface{

    ArrayList<AlarmModel> alarmModels = new ArrayList<>();
    Alarm_RecycleViewAdapter alarmAdapter;

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
                                alarmModels.get(position).setAlarmName(newName);
                                alarmAdapter.notifyItemChanged(position);
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
        String [] alarmNames = getResources().getStringArray(R.array.alarm_slots_txt);

        for (int i=0; i<alarmNames.length;i++){
            alarmModels.add(new AlarmModel(alarmNames[i]));
        }
    }

    @Override
    public void onAlarmClick(int position) {
        Intent createAlarm = new Intent(this, CreateAlarm.class);
        String name = alarmModels.get(position).getAlarmName();
        createAlarm.putExtra("AlarmName", name);
        createAlarm.putExtra("Position", position);
        createAlarmLauncher.launch(createAlarm);
    }
}