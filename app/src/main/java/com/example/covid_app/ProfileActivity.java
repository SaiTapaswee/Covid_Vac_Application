package com.example.covid_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String fuser = firebaseAuth.getCurrentUser().getUid();
    DocumentReference documentReference;

    EditText firstname, lastname, address, mobilenumber;
    Button btn_updateData;

    String fname, lname, mnum,add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastName);
        mobilenumber = findViewById(R.id.mobile);
        address=findViewById(R.id.address);
        btn_updateData = findViewById(R.id.updateDetails);



        documentReference = db.collection("Profile").document(fuser);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                firstname.setText(value.getString("firstname"));
                lastname.setText(value.getString("lastname"));
                mobilenumber.setText(value.getString("mobilenumber"));
                address.setText(value.getString("address"));

            }
        });






        btn_updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = firstname.getText().toString();
                lname = lastname.getText().toString();
                mnum = mobilenumber.getText().toString();
                add=address.getText().toString();

                Map<String, Object> profileData = new HashMap<>();

                profileData.put("firstname", fname);
                profileData.put("lastname", lname);
                profileData.put("mobilenumber", mnum);
                profileData.put("address",add);


                db.collection("Profile").document(fuser).set(profileData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Profile data updated", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error adding data" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
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