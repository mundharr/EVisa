package com.example.evisa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText adminUsername;
    private EditText adminPassword;
    private Button adminLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminUsername = findViewById(R.id.adminUsername);
        adminPassword = findViewById(R.id.adminPassword);
        adminLoginButton = findViewById(R.id.adminLoginButton);

        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = adminUsername.getText().toString().trim();
                String password = adminPassword.getText().toString().trim();

                if (username.equals("admin") && password.equals("admin")) {
                    // Successful login, navigate to admin view applications page
                    Intent intent = new Intent(AdminLoginActivity.this, AdminViewApplicationsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Unsuccessful login, show a toast
                    Toast.makeText(AdminLoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
