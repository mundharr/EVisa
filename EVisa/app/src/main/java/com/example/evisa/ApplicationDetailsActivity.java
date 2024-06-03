package com.example.evisa;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ApplicationDetailsActivity extends AppCompatActivity {

    private TextView applicationName, applicationDateOfBirth, applicationNationality, applicationPassportNumber, applicationVisaType, applicationPurposeOfVisit,applicationStatus;
    private Button editApplicationButton;
    private AdminViewApplicationsActivity.VisaApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_details);

        applicationName = findViewById(R.id.applicationName);
        applicationDateOfBirth = findViewById(R.id.applicationDateOfBirth);
        applicationNationality = findViewById(R.id.applicationNationality);
        applicationPassportNumber = findViewById(R.id.applicationPassportNumber);
        applicationVisaType = findViewById(R.id.applicationVisaType);
        applicationPurposeOfVisit = findViewById(R.id.applicationPurposeOfVisit);
        applicationStatus = findViewById(R.id.applicationStatus);
        editApplicationButton = findViewById(R.id.editApplicationButton);

        Intent intent = getIntent();
        application = (AdminViewApplicationsActivity.VisaApplication) intent.getSerializableExtra("application");

        if (application != null) {
            applicationName.setText("Name: " + application.name);
            applicationDateOfBirth.setText("DOB: " + application.dateOfBirth);
            applicationNationality.setText("Nationality: " + application.nationality);
            applicationPassportNumber.setText("Passport Number: " + application.passportNumber);
            applicationVisaType.setText("Visa Type: " + application.visaType);
            applicationPurposeOfVisit.setText("Purpose of Visit: " + application.purposeOfVisit);
            applicationStatus.setText("Status: " + application.status);
            editApplicationButton.setOnClickListener(v -> editApplication(application));
        }

    }
        private void editApplication(AdminViewApplicationsActivity.VisaApplication application) {
        Intent intent = new Intent(ApplicationDetailsActivity.this, EditApplicationActivity.class);
        intent.putExtra("application", application);
        startActivity(intent);
    }
}

