package com.example.covid_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdminDashboardAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    List<StatusPojo> taskPojos;
    private FirebaseFirestore db;
    Context cnt;

    public AdminDashboardAdapter(List<StatusPojo> ar, Context cnt) {
        this.taskPojos = ar;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return taskPojos.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.adapter_admindashboard, null);

        TextView tvDate = (TextView) obj2.findViewById(R.id.tvDate);
        tvDate.setText("Date :" + taskPojos.get(pos).getDate());

        TextView tvName = (TextView) obj2.findViewById(R.id.tvName);
        tvName.setText("Name :" + taskPojos.get(pos).getName());

        TextView tvMail = (TextView) obj2.findViewById(R.id.tvMail);
        tvMail.setText("Mail Id :" + taskPojos.get(pos).getMailID());

        TextView tvPhno = (TextView) obj2.findViewById(R.id.tvPhno);
        tvPhno.setText("Phone No :" + taskPojos.get(pos).getPhnoNo());


        TextView tvCenter = (TextView) obj2.findViewById(R.id.tvCenter);
        tvCenter.setText("Covid Center :" + taskPojos.get(pos).getCenter());


        TextView tvStatus = (TextView) obj2.findViewById(R.id.tvStatus);
        tvStatus.setText("Status :" + taskPojos.get(pos).getStatus());

        TextView tvBookingDate = (TextView) obj2.findViewById(R.id.tvBookingDate);
        tvBookingDate.setText("Booking Date :" + taskPojos.get(pos).getBooking_date());

        Spinner spinnerStatus = (Spinner) obj2.findViewById(R.id.spinnerStatus);

        Button btnUpdate = (Button) obj2.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateTask(taskPojos.get(pos).getMailID(), taskPojos.get(pos).getName(),
                        taskPojos.get(pos).getPhnoNo(), spinnerStatus.getSelectedItem().toString()
                        , taskPojos.get(pos).getDate(), taskPojos.get(pos).getUsername(),taskPojos.get(pos).getBooking_date(),taskPojos.get(pos).getCenter());
            }
        });
        return obj2;
    }

    private void updateTask(String mailID, String name, String phnoNo, String status, String date, String username,String bdate, String center) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        StatusPojo updatedCourse = new StatusPojo(mailID, name, phnoNo, status, date, username,bdate,center);

        db.collection("Covid Details").
                document(date).
                set(updatedCourse).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(cnt, "Status has been updated..", Toast.LENGTH_SHORT).show();
                cnt.startActivity(new Intent(cnt, AdminHomeActivity.class));
                ((Activity) cnt).finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(cnt, "Fail to update the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }


}