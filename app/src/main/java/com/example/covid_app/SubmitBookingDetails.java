package com.example.covid_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SubmitBookingDetails extends AppCompatActivity {
    EditText name, email, mobile, date;
    Button btnsubmit;
    ProgressDialog loadingBar;
    FirebaseFirestore db;
    String fuser;
    SharedPreferences sharedPreferences;
    FirebaseAuth firebaseAuth;
    String session;
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;
    Spinner sp_center;

    ArrayList<String> ar=new ArrayList<String>();
    ListView list_view;
    List<CentersPojo> taskPojos;
    GetCovidCentersAdapter getCovidCentersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_booking_details);

        getSupportActionBar().setTitle("Booking Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

        sp_center=(Spinner) findViewById(R.id.sp_center);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText) findViewById(R.id.mobile);
        date = (EditText) findViewById(R.id.date);
        date.setFocusable(false);

        firebaseAuth = FirebaseAuth.getInstance();
        fuser = firebaseAuth.getCurrentUser().getUid();

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");


        loadingBar = new ProgressDialog(SubmitBookingDetails.this);

        getCenters();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datepicker();
            }
        });
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recoardDetails();
            }
        });
    }


    private  void getCenters()
    {

        db.collection("CovidCenters")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CentersPojo centersPojo = document.toObject(CentersPojo.class);
                                ar.add(centersPojo.getName());
                            }
//                            getCovidCentersAdapter = new GetCovidCentersAdapter(taskPojos, CovidCentersActivity.this);
//                            list_view.setAdapter(getCovidCentersAdapter);

                            //Creating the ArrayAdapter instance having the country list
                            ArrayAdapter aa = new ArrayAdapter(SubmitBookingDetails.this,android.R.layout.simple_spinner_item,ar);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            sp_center.setAdapter(aa);

                           // sp_center.setAdapter();



                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void recoardDetails() {
        String pname = name.getText().toString();
        String mailID = email.getText().toString();
        String phno = mobile.getText().toString();
        String bookingDate = date.getText().toString();
        String center = sp_center.getSelectedItem().toString();

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        //String date=date1+session;

        if (TextUtils.isEmpty(pname)) {
            Toast.makeText(this, "Please Enter NAme...", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(mailID)) {
            Toast.makeText(this, "Please Enter MailID...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(phno)) {
            Toast.makeText(this, "Please Enter PhoneNo...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(bookingDate)) {
            Toast.makeText(this, "Please Select Bookingdate...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Please Wait");
            loadingBar.setMessage("Please wait,  while we are adding Details..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            addDetails(pname, mailID, phno, date,bookingDate,center);
        }
    }

    private void addDetails(final String pname, String mailID, String phno, String date,String booking_date, String center) {

        HashMap<String, Object> note = new HashMap<>();
        note.put("name", pname);
        note.put("mailID", mailID);
        note.put("phnoNo", phno);
        note.put("status", "Pending");
        note.put("date", date);
        note.put("username", session);
        note.put("booking_date", booking_date);
        note.put("center", center);

        db.collection("Covid Details").document(date).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SubmitBookingDetails.this, "Your Data Added Succussfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SubmitBookingDetails.this, MainActivity.class));
                loadingBar.dismiss();
                finish();
            }
        });
    }

    public void datepicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + "";
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";

                        date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
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