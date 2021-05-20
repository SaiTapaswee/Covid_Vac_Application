package com.example.covid_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminDashBoard extends AppCompatActivity {
    ProgressDialog loadingBar;
    ListView list_view;
    List<StatusPojo> taskPojos;
    AdminDashboardAdapter bookingStatusAdapter;
    FirebaseFirestore db;
    SharedPreferences sharedPreferences;
    String session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);

        getSupportActionBar().setTitle("Admin Dashboard");
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view = (ListView) findViewById(R.id.list_viewRe);
        db = FirebaseFirestore.getInstance();
        loadingBar = new ProgressDialog(AdminDashBoard.this);
        taskPojos = new ArrayList<>();
        GetTaskDetails();
    }
    public void GetTaskDetails() {
        db.collection("Covid Details")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                StatusPojo centersPojo = document.toObject(StatusPojo.class);
                                taskPojos.add(centersPojo);
                            }
                            bookingStatusAdapter = new AdminDashboardAdapter(taskPojos, AdminDashBoard.this);
                            list_view.setAdapter(bookingStatusAdapter);


                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}