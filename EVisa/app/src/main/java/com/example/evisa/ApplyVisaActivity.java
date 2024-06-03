package com.example.evisa;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApplyVisaActivity extends AppCompatActivity {

    private EditText nameEditText, dateOfBirthEditText, nationalityEditText, passportNumberEditText, visaTypeEditText, purposeOfVisitEditText;
    private Button applyButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_visa);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("applications");

        nameEditText = findViewById(R.id.name);
        dateOfBirthEditText = findViewById(R.id.dateOfBirth);
        nationalityEditText = findViewById(R.id.nationality);
        passportNumberEditText = findViewById(R.id.passportNumber);
        visaTypeEditText = findViewById(R.id.visaType);
        purposeOfVisitEditText = findViewById(R.id.purposeOfVisit);
        applyButton = findViewById(R.id.applyButton);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyForVisa(currentUser.getUid());
            }
        });
    }

    private void applyForVisa(String userId) {
        String name = nameEditText.getText().toString().trim();
        String dateOfBirth = dateOfBirthEditText.getText().toString().trim();
        String nationality = nationalityEditText.getText().toString().trim();
        String passportNumber = passportNumberEditText.getText().toString().trim();
        String visaType = visaTypeEditText.getText().toString().trim();
        String purposeOfVisit = purposeOfVisitEditText.getText().toString().trim();

        // Set status as pending
        String status = "pending";

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required");
            return;
        }

        if (TextUtils.isEmpty(dateOfBirth)) {
            dateOfBirthEditText.setError("Date of Birth is required");
            return;
        }

        if (TextUtils.isEmpty(nationality)) {
            nationalityEditText.setError("Nationality is required");
            return;
        }

        if (TextUtils.isEmpty(passportNumber)) {
            passportNumberEditText.setError("Passport number is required");
            return;
        }

        if (TextUtils.isEmpty(visaType)) {
            visaTypeEditText.setError("Visa type is required");
            return;
        }

        if (TextUtils.isEmpty(purposeOfVisit)) {
            purposeOfVisitEditText.setError("Purpose of visit is required");
            return;
        }

        String applicationId = databaseReference.push().getKey();
        VisaApplication application = new VisaApplication(applicationId, userId, name, dateOfBirth, nationality, passportNumber, visaType, purposeOfVisit, status);

        assert applicationId != null;
        databaseReference.child(applicationId).setValue(application).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ApplyVisaActivity.this, "Application submitted", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ApplyVisaActivity.this, "Failed to submit application", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static class VisaApplication {
        public String applicationId;
        public String userId;
        public String name;
        public String dateOfBirth;
        public String nationality;
        public String passportNumber;
        public String visaType;
        public String purposeOfVisit;
        public String status;

        public VisaApplication() {}

        public VisaApplication(String applicationId, String userId, String name, String dateOfBirth, String nationality, String passportNumber, String visaType, String purposeOfVisit, String status) {
            this.applicationId = applicationId;
            this.userId = userId;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.nationality = nationality;
            this.passportNumber = passportNumber;
            this.visaType = visaType;
            this.purposeOfVisit = purposeOfVisit;
            this.status = status; // Initialize status field
        }
    }

}
