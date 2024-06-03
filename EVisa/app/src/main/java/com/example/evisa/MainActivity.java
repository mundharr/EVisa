package com.example.evisa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button applyVisaButton;
    private Button viewApplicationsButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        applyVisaButton = findViewById(R.id.applyVisaButton);
        viewApplicationsButton = findViewById(R.id.viewApplicationsButton);
        settingsButton = findViewById(R.id.settingsButton);

        applyVisaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Apply Visa Activity
                Intent intent = new Intent(MainActivity.this, ApplyVisaActivity.class);
                startActivity(intent);
            }
        });

        viewApplicationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to View Applications Activity
                Intent intent = new Intent(MainActivity.this, ViewApplicationsActivity.class);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Settings Activity
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
