package com.example.gerstelalarm;

import android.content.Intent;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<AlarmModel> alarmModels = new ArrayList<>();

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
        Alarm_RecycleViewAdapter alarmAdapter = new Alarm_RecycleViewAdapter(this, alarmModels);
        recyclerView.setAdapter(alarmAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void goToCreate (View view){
        Intent createAlarm = new Intent(this, CreateAlarm.class);
        String name = ((Button)view).getText().toString();
        createAlarm.putExtra("AlarmName", name);
        createAlarm.putExtra("ButtonId", view.getId());
        createAlarmLauncher.launch(createAlarm);
    }

    ActivityResultLauncher<Intent> createAlarmLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 1){
                        Intent info = result.getData();

                        if (info != null){
                            String newName = info.getStringExtra("AlarmName");
                            int buttonId = info.getIntExtra("ButtonId", 0);
                            if (newName!=null) {
                                ((Button) findViewById(buttonId)).setText(newName);
                            }
                        }
                    }
                }
            }
    );

    private void setUpAlarmModels (){
        String [] alarmNames = getResources().getStringArray(R.array.alarm_slots_txt);

        for (int i=0; i<alarmNames.length;i++){
            alarmModels.add(new AlarmModel(alarmNames[i]));
        }
    }
}