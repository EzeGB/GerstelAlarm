package com.example.gerstelalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAlarm extends AppCompatActivity {

    EditText name, hours, minutes;

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
        hours = findViewById(R.id.editTextHours);
        minutes = findViewById(R.id.editTextMinutes);

        Intent createAlarm = getIntent();
        boolean create = createAlarm.getBooleanExtra("Create", false);
        if (!create){
            String currentName = createAlarm.getStringExtra("AlarmName");
            String currentHours = createAlarm.getStringExtra("Hours");
            String currentMinutes = createAlarm.getStringExtra("Minutes");
            name.setText(currentName);
            hours.setText(currentHours);
            minutes.setText(currentMinutes);
        }
    }


    public void changeToMain(View view){
        Intent goBack = new Intent();
        Intent createAlarm = getIntent();
        goBack.putExtra("Position", createAlarm.getIntExtra("Position", 0));
        goBack.putExtra("Create", createAlarm.getBooleanExtra("Create", false));
        String newName = name.getText().toString();
        String newHours = hours.getText().toString();
        String newMinutes = minutes.getText().toString();
        goBack.putExtra("AlarmName", newName);
        goBack.putExtra("Hours", newHours);
        goBack.putExtra("Minutes", newMinutes);
        setResult(1, goBack);
        finish();
    }
    public void cancel (View view){
        Intent goBack = new Intent();
        setResult(0, goBack);
        finish();
    }
}