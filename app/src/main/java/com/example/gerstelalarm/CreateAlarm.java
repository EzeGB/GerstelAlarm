package com.example.gerstelalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

public class CreateAlarm extends AppCompatActivity {

    EditText name, hours, minutes;
    ToggleButton monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    boolean [] weekDays;

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
        initializeToggleButtons();

        Intent createAlarm = getIntent();
        if (!createAlarm.getBooleanExtra("Create", false)){
            String currentName = createAlarm.getStringExtra("AlarmName");
            String currentHours = createAlarm.getStringExtra("Hours");
            String currentMinutes = createAlarm.getStringExtra("Minutes");
            weekDays = createAlarm.getBooleanArrayExtra("WeekDays");
            name.setText(currentName);
            hours.setText(currentHours);
            minutes.setText(currentMinutes);
        } else {
            weekDays = new boolean[7];
            Arrays.fill(weekDays, false);
        }
        configureToggleButtons(weekDays);
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

    public void initializeToggleButtons(){
        monday = findViewById(R.id.toggleButtonMonday);
        tuesday = findViewById(R.id.toggleButtonTuesday);
        wednesday = findViewById(R.id.toggleButtonWednesday);
        thursday = findViewById(R.id.toggleButtonThursday);
        friday = findViewById(R.id.toggleButtonFriday);
        saturday = findViewById(R.id.toggleButtonSaturday);
        sunday = findViewById(R.id.toggleButtonSunday);
    }

    public void configureToggleButtons(boolean[]weekDays){
        monday.setChecked(weekDays[0]);
        tuesday.setChecked(weekDays[1]);
        wednesday.setChecked(weekDays[2]);
        thursday.setChecked(weekDays[3]);
        friday.setChecked(weekDays[4]);
        saturday.setChecked(weekDays[5]);
        sunday.setChecked(weekDays[6]);
    }
}