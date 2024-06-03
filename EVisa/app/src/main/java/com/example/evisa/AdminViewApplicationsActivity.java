package com.example.evisa;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminViewApplicationsActivity extends AppCompatActivity {

    private ListView adminApplicationsListView;
    private DatabaseReference databaseReference;
    private ArrayList<String> applicationsList;
    private ArrayAdapter<String> adapter;
    private ArrayList<VisaApplication> visaApplications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_applications);

        adminApplicationsListView = findViewById(R.id.adminApplicationsListView);
        databaseReference = FirebaseDatabase.getInstance().getReference("applications");

        applicationsList = new ArrayList<>();
        visaApplications = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, applicationsList);
        adminApplicationsListView.setAdapter(adapter);

        loadApplications();
        adminApplicationsListView.setOnItemClickListener((parent, view, position, id) -> {
            VisaApplication application = visaApplications.get(position);
            showApplicationDetails(application);
        });
    }

    private void loadApplications() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                applicationsList.clear();
                visaApplications.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    VisaApplication application = snapshot.getValue(VisaApplication.class);
                    if (application != null) {
                        String applicationDetails = "Name: " + application.name + "\nPassport: " + application.passportNumber;
                        applicationsList.add(applicationDetails);
                        visaApplications.add(application);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminViewApplicationsActivity.this, "Failed to load applications", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showApplicationDetails(VisaApplication application) {
        Intent intent = new Intent(AdminViewApplicationsActivity.this, ApplicationDetailsActivity.class);
        intent.putExtra("application", application);
        startActivity(intent);
    }

    private void editApplication(VisaApplication application) {
        Intent intent = new Intent(AdminViewApplicationsActivity.this, EditApplicationActivity.class);
        intent.putExtra("application", application);
        startActivity(intent);
    }

    public static class VisaApplication implements Serializable {
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

        public VisaApplication(String applicationId, String userId, String name, String dateOfBirth, String nationality, String passportNumber, String visaType, String purposeOfVisit,String status) {
            this.applicationId = applicationId;
            this.userId = userId;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.nationality = nationality;
            this.passportNumber = passportNumber;
            this.visaType = visaType;
            this.purposeOfVisit = purposeOfVisit;
            this.status=status;
        }
    }
}
