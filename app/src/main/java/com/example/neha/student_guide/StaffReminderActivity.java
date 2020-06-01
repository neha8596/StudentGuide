package com.example.neha.student_guide;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.Context;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class StaffReminderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner sub;
    EditText dos,rem;
    String subject,subject1,content,date,reminder;
    Button b2;
    DatePickerDialog datePickerDialog;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reminder);

        b2 = (Button) findViewById(R.id.buttonUpload);
        sub = (Spinner) findViewById(R.id.editText2);
        dos = (EditText) findViewById(R.id.editText5);
        rem = (EditText) findViewById(R.id.editText3);
        // initiate the date picker and a button
        // perform click event on edit text
        dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(StaffReminderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dos.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        // Spinner click listener
        sub.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> subjects = new ArrayList<String>();
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
        sub.setAdapter(dataAdapter);

        Firebase.setAndroidContext(this);
        sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                subject = subject1;
                date = dos.getText().toString();
                reminder = rem.getText().toString();

                if (date.equals("")) {
                    dos.setError("can't be blank");
                } else if (reminder.equals("")) {
                    rem.setError("can't be blank");
                } else {
                    final ProgressDialog pd = new ProgressDialog(StaffReminderActivity.this);
                    pd.setMessage("Uploading...");
                    pd.show();


                    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REMINDERS);
                    //dismissing the progress dialog
                    pd.dismiss();

                    //displaying success toast
                    Toast.makeText(getApplicationContext(), "Reminder Uploaded ", Toast.LENGTH_LONG).show();

                    //creating the upload object to store uploaded image details
                    Remind remind = new Remind(subject1, rem.getText().toString().trim(), dos.getText().toString().trim());

                    //adding an upload to firebase database
                    String uploadId = mDatabase.push().getKey();

                    mDatabase.child(uploadId).setValue(remind);

                }








            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String subject2 = parent.getItemAtPosition(position).toString();
        //Object subject1 = parent.getItemAtPosition(position);
        subject1 = String.format(subject2); //Set it in here like this

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
