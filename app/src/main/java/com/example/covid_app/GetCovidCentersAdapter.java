package com.example.covid_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class GetCovidCentersAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    List<CentersPojo> taskPojos;
    private FirebaseFirestore db;

    Context cnt;

    public GetCovidCentersAdapter(List<CentersPojo> ar, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.adapter_covidcenters, null);

        TextView tvCentername = (TextView) obj2.findViewById(R.id.tvCentername);
        tvCentername.setText("Covid Center Name :"+taskPojos.get(pos).getName());

        TextView tvAddress = (TextView) obj2.findViewById(R.id.tvAddress);
        tvAddress.setText("Address :"+taskPojos.get(pos).getAddress());

        TextView tvArea = (TextView) obj2.findViewById(R.id.tvArea);
        tvArea.setText("City :"+taskPojos.get(pos).getCity());


        return obj2;
    }

}