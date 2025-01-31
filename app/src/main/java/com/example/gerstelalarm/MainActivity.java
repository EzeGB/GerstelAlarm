package com.example.gerstelalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

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
                                writeAlarmsToFile();
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
                                writeAlarmsToFile();
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
        File path = getApplicationContext().getFilesDir();
        File readNames = new File(path, "alarm_names");
        byte[] content = new byte[(int)readNames.length()];

        try {
            FileInputStream streamNames = new FileInputStream(readNames);
            streamNames.read(content);
            String [] alarmNames = new String[] {Arrays.toString(content)};

            for (int i=0; i<alarmNames.length;i++){
                alarmModels.add(new AlarmModel(alarmNames[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void writeAlarmsToFile(){
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writerNames = new FileOutputStream(new File(path, "alarm_names"));
            for (int i=0; i<alarmModels.size();i++){
                writerNames.write(alarmModels.get(i).getAlarmName().getBytes());

            }
            writerNames.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    public void readFromFile(View view){
//        File path = getApplicationContext().getFilesDir();
//        File read = new File(path, "prueba");
//        byte[] content = new byte[(int)read.length()];
//
//        try {
//            FileInputStream stream = new FileInputStream(read);
//            stream.read(content);
//            ((TextView)findViewById(R.id.textView)).setText(new String(content));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}