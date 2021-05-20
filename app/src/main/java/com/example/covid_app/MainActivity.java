package com.example.covid_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btnProfile, btnCovidCenters, btnBookingapt, btnBookingstatus, btnLogout,btnAdminConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Dashboard");




        btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        btnCovidCenters = findViewById(R.id.btnCovidCenters);
        btnCovidCenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CovidCentersActivity.class));

            }
        });

        btnBookingapt = findViewById(R.id.btnBookingapt);
        btnBookingapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Bookapp.class));
            }
        });


        btnBookingstatus = findViewById(R.id.btnBookingstatus);
        btnBookingstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookingStatusActivity.class));
            }
        });

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(intent);
            }
        });


        btnAdminConfirm = findViewById(R.id.btnAdminConfirm);
        btnAdminConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdminDashBoard.class);
               // FirebaseAuth.getInstance().signOut();
                startActivity(intent);
            }
        });

    }
}
