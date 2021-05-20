package com.example.covid_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText FirstName, LastName, email, mobile, password, address;
    Button login, signup;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    String fuser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String fname, lname, eml, mobilenumber, pswd, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("SignUp");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirstName = findViewById(R.id.firstName);
        LastName = findViewById(R.id.lastName);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if (TextUtils.isEmpty(Email)) {
                    email.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("password is required.");
                    return;
                }
                if (password.length() < 6) {
                    password.setError("Password must be>=6 Characters");
                    return;
                }
                if (mobile.length() < 10) {
                    mobile.setError("Invalid number");
                    return;
                }


                //-----------------------------------------------------------------
                fname = FirstName.getText().toString();
                lname = LastName.getText().toString();
                mobilenumber = mobile.getText().toString();
                add = address.getText().toString();
                //-----------------------------------------------------------------


                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();

                            fuser = firebaseAuth.getCurrentUser().getUid();
                            insertUserData(fname, lname, mobilenumber, add);
                            startActivity(new Intent(SignUp.this, Login.class));
                        } else {
                            Toast.makeText(SignUp.this, "Error !!.." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }

    public void insertUserData(String firstname, String lastname, String mobileNumber, String add) {
        Map<String, Object> profileData = new HashMap<>();

        profileData.put("firstname", firstname);
        profileData.put("lastname", lastname);
        profileData.put("mobilenumber", mobileNumber);
        profileData.put("address", add);


        db.collection("Profile").document(fuser).set(profileData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error adding data" + e.getMessage(), Toast.LENGTH_SHORT).show();

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