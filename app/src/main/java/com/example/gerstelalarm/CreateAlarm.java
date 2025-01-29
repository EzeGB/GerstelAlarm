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

        Intent createAlarm = getIntent();
        String name = createAlarm.getStringExtra("AlarmName");
        ((EditText)findViewById(R.id.editTextText)).setText(name);
    }


    public void changeToMain(View view){
        Intent goBack = new Intent();
        String newName = ((EditText)findViewById(R.id.editTextText)).getText().toString();
        goBack.putExtra("AlarmName", newName);
        Intent createAlarm = getIntent();
        goBack.putExtra("Position", createAlarm.getIntExtra("Position", 0));
        setResult(1, goBack);
        finish();
    }
    public void cancel (View view){
        Intent goBack = new Intent();
        setResult(0, goBack);
        finish();
    }
}