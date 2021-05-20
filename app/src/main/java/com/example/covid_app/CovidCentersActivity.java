package com.example.covid_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CovidCentersActivity extends AppCompatActivity {

    ProgressDialog loadingBar;
    ListView list_view;
    List<CentersPojo> taskPojos;
    GetCovidCentersAdapter getCovidCentersAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_centers);

        getSupportActionBar().setTitle("Covid Centers");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view = (ListView) findViewById(R.id.list_view);
        db = FirebaseFirestore.getInstance();
        loadingBar = new ProgressDialog(CovidCentersActivity.this);
        taskPojos=new ArrayList<>();
        GetTaskDetails();

    }
    public void GetTaskDetails() {
        db.collection("CovidCenters")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CentersPojo centersPojo = document.toObject(CentersPojo.class);
                                taskPojos.add(centersPojo);
                            }
                            getCovidCentersAdapter = new GetCovidCentersAdapter(taskPojos, CovidCentersActivity.this);
                            list_view.setAdapter(getCovidCentersAdapter);


                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}