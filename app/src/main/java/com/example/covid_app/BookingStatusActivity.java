package com.example.covid_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookingStatusActivity extends AppCompatActivity {
    ProgressDialog loadingBar;
    ListView list_view;
    List<StatusPojo> taskPojos;
    BookingStatusAdapter bookingStatusAdapter;
    FirebaseFirestore db;
    SharedPreferences sharedPreferences;
    String session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_status);

        getSupportActionBar().setTitle("Booking Status");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        list_view = (ListView) findViewById(R.id.list_viewstatus);
        db = FirebaseFirestore.getInstance();
        loadingBar = new ProgressDialog(BookingStatusActivity.this);
        taskPojos = new ArrayList<>();
        GetTaskDetails();
    }

    public void GetTaskDetails() {
        db.collection("Covid Details").whereEqualTo("username", session)
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
                            bookingStatusAdapter = new BookingStatusAdapter(taskPojos, BookingStatusActivity.this);
                            list_view.setAdapter(bookingStatusAdapter);


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