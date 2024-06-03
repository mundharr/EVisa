package com.example.evisa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditApplicationActivity extends AppCompatActivity {

    private EditText editName, editDateOfBirth, editNationality, editPassportNumber, editVisaType, editPurposeOfVisit;
    private Button saveApplicationButton;
    private AdminViewApplicationsActivity.VisaApplication application;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_application);

        editName = findViewById(R.id.editName);
        editDateOfBirth = findViewById(R.id.editDateOfBirth);
        editNationality = findViewById(R.id.editNationality);
        editPassportNumber = findViewById(R.id.editPassportNumber);
        editVisaType = findViewById(R.id.editVisaType);
        editPurposeOfVisit = findViewById(R.id.editPurposeOfVisit);
        saveApplicationButton = findViewById(R.id.saveApplicationButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("applications");

        application = (AdminViewApplicationsActivity.VisaApplication) getIntent().getSerializableExtra("application");

        if (application != null) {
            editName.setText(application.name);
            editDateOfBirth.setText(application.dateOfBirth);
            editNationality.setText(application.nationality);
            editPassportNumber.setText(application.passportNumber);
            editVisaType.setText(application.visaType);
            editPurposeOfVisit.setText(application.purposeOfVisit);

            saveApplicationButton.setOnClickListener(v -> saveApplication());
        }
    }

    private void saveApplication() {
        String name = editName.getText().toString().trim();
        String dateOfBirth = editDateOfBirth.getText().toString().trim();
        String nationality = editNationality.getText().toString().trim();
        String passportNumber = editPassportNumber.getText().toString().trim();
        String visaType = editVisaType.getText().toString().trim();
        String purposeOfVisit = editPurposeOfVisit.getText().toString().trim();

        if (name.isEmpty() || dateOfBirth.isEmpty() || nationality.isEmpty() || passportNumber.isEmpty() || visaType.isEmpty() || purposeOfVisit.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the application object with new values
        application.name = name;
        application.dateOfBirth = dateOfBirth;
        application.nationality = nationality;
        application.passportNumber = passportNumber;
        application.visaType = visaType;
        application.purposeOfVisit = purposeOfVisit;

        // Update the data in Firebase Realtime Database
        databaseReference.child(application.applicationId).setValue(application).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditApplicationActivity.this, "Application updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(EditApplicationActivity.this, "Failed to update application", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
