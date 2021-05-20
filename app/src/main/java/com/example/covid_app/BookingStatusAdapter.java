package com.example.covid_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class BookingStatusAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    List<StatusPojo> taskPojos;
    private FirebaseFirestore db;
    Context cnt;

    public BookingStatusAdapter(List<StatusPojo> ar, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.adapter_booking_status, null);

        TextView tvName = (TextView) obj2.findViewById(R.id.tvName);
        tvName.setText("Name :" + taskPojos.get(pos).getName());

        TextView tvMAil = (TextView) obj2.findViewById(R.id.tvMAil);
        tvMAil.setText("Mail :" + taskPojos.get(pos).getMailID());

        TextView tvPhoneNO = (TextView) obj2.findViewById(R.id.tvPhoneNO);
        tvPhoneNO.setText("Phone No :" + taskPojos.get(pos).getPhnoNo());

        TextView tvCenter = (TextView) obj2.findViewById(R.id.tvCenter);
        tvCenter.setText("Covid Center :" + taskPojos.get(pos).getCenter());

        TextView tvStatus = (TextView) obj2.findViewById(R.id.tvStatus);
        tvStatus.setText("Status :" + taskPojos.get(pos).getStatus());

        TextView tvBookingDate = (TextView) obj2.findViewById(R.id.tvBookingDate);
        tvBookingDate.setText("Booking Date :" + taskPojos.get(pos).getBooking_date());

        return obj2;
    }

}