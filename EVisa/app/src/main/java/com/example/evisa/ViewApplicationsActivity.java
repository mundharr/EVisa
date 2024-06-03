package com.example.evisa;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewApplicationsActivity extends AppCompatActivity {

    private ListView applicationsListView;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ArrayList<String> applicationsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applications);

        applicationsListView = findViewById(R.id.applicationsListView);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("applications");

        applicationsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, applicationsList);
        applicationsListView.setAdapter(adapter);

        loadApplications(currentUser.getUid());
    }

    private void loadApplications(String userId) {
        databaseReference.orderByChild("userId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                applicationsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    VisaApplication application = snapshot.getValue(VisaApplication.class);
                    if (application != null) {
                        String applicationDetails = "Name: " + application.name + "\nDOB: " + application.dateOfBirth +
                                "\nNationality: " + application.nationality + "\nPassport: " + application.passportNumber +
                                "\nVisa: " + application.visaType + "\nPurpose: " + application.purposeOfVisit +
                                "\nStatus: " + application.status; // Display status
                        applicationsList.add(applicationDetails);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewApplicationsActivity.this, "Failed to load applications", Toast.LENGTH_SHORT).show();
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
            this.status = status;
        }
    }
}
