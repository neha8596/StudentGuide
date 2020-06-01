package com.example.neha.student_guide;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.app.ProgressDialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static android.R.attr.data;
import static com.example.neha.student_guide.R.id.imageView;
import static com.example.neha.student_guide.R.id.up;

public class StudentReminder1 extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {


    Spinner showSpinner;
    String subject1,dateof,remname;
    FirebaseStorage storageRef = FirebaseStorage.getInstance();
    //recyclerview object
    private RecyclerView recyclerView;
    private Button spinButton;
    //adapter object
    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference mDatabaseRef;
//    StorageReference imagesRef = storageRef.child("uploads");
    // Create a storage reference from our app

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<Remind> rems;
    //firebase objects


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reminder);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showSpinner = (Spinner)findViewById(R.id.showSpinner);
        spinButton = (Button)findViewById(R.id.spinnerButton);



        // Spinner click listener
        showSpinner.setOnItemSelectedListener(this);
        spinButton.setOnClickListener(this);


        // Spinner Drop down elements
        final List<String> subjects = new ArrayList<String>();
        subjects.add("DDB");
        subjects.add("SPCC");
        subjects.add("SE");
        subjects.add("MCC");
        subjects.add("NPL");
        subjects.add("FRENCH");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjects);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        showSpinner.setAdapter(dataAdapter);

        Firebase.setAndroidContext(this);
        showSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override

            public void  onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String subject2 = parent.getItemAtPosition(position).toString();
                //Object subject1 = parent.getItemAtPosition(position);
                subject1 = String.format(subject2); //Set it in here like this
                //resultado5.setText(msg1);

                // Showing selected spinner item

            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



    }

    //@Override
    public void onClick(View view) {
        if (view == spinButton) {
            showList();
        }
    }

    private void showList() {


        progressDialog = new ProgressDialog(this);

        rems = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String subject = subject1;
        final String FB_DATABASE_PATH = "reminders";
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);
        mDatabaseRef.orderByChild("subject").equalTo(subject).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor

                    Remind rem = snapshot.getValue(Remind.class);

                    dateof=rem.dos;
                    remname=rem.rem;
                    rems.add(rem);

                }

                //creating adapter
                adapter = new RemAdapter(getApplicationContext(), rems);
                //Init adapter

                //adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });


    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String subject2 = parent.getItemAtPosition(position).toString();
        //Object subject1 = parent.getItemAtPosition(position);
        subject1 = String.format(subject2); //Set it in here like this
        //resultado5.setText(msg1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}