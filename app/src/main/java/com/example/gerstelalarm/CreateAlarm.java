package com.example.gerstelalarm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAlarm extends AppCompatActivity {

    EditText name, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_alarm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.editTextAlarmName);
        time = findViewById(R.id.editTextAlarmTime);

        Intent createAlarm = getIntent();
        boolean create = createAlarm.getBooleanExtra("Create", false);
        if (!create){
            String currentName = createAlarm.getStringExtra("AlarmName");
            name.setText(currentName);
            String currentTime = createAlarm.getStringExtra("Time");
            time.setText(currentTime);
        }
    }


    public void changeToMain(View view){
        Intent goBack = new Intent();
        Intent createAlarm = getIntent();
        goBack.putExtra("Position", createAlarm.getIntExtra("Position", 0));
        goBack.putExtra("Create", createAlarm.getBooleanExtra("Create", false));
        String newName = name.getText().toString();
        goBack.putExtra("AlarmName", newName);
        String newTime = time.getText().toString();
        goBack.putExtra("Time",newTime);
        setResult(1, goBack);
        finish();
    }
    public void cancel (View view){
        Intent goBack = new Intent();
        setResult(0, goBack);
        finish();
    }
}