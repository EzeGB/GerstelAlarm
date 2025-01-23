package com.example.gerstelalarm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        Intent received = getIntent();
        String newName = received.getStringExtra("AlarmName");
        int buttonId = received.getIntExtra("ButtonId", 0);
        if (newName!=null) {
            ((Button) findViewById(buttonId)).setText(newName);
        }
    }

    public void goToCreate (View view){
        Intent createAlarm = new Intent(this, CreateAlarm.class);
        String name = ((Button)view).getText().toString();
        createAlarm.putExtra("AlarmName", name);
        createAlarm.putExtra("ButtonId", view.getId());
        startActivity(createAlarm);
    }
}