package com.example.neha.student_guide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TDashboardActivity extends AppCompatActivity {
    SharedPreferences sp;


    TextView welcome;
    Button chatButton;
    Button tlogout;
    Button upload;
    Button reminders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tdashboard);
        ActionBar actionBar=getSupportActionBar();actionBar.hide();
        sp=getSharedPreferences("login",MODE_PRIVATE);
        String channel = (sp.getString("username", ""));



        chatButton = (Button)findViewById(R.id.TchatButton);
        welcome = (TextView) findViewById(R.id.TwelcomeText);
        tlogout = (Button)findViewById(R.id.TlogoutButton);
        upload = (Button)findViewById(R.id.TdownloadButton);
        reminders = (Button)findViewById(R.id.TremButton);
        welcome.setText("Welcome "+channel);


        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TDashboardActivity.this, Users.class));
            }
        });
        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TDashboardActivity.this, StaffReminderActivity.class));
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TDashboardActivity.this, UploadActivity.class));
            }
        });
        tlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences tsp=getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor e=sp.edit();
                e.clear();
                e.commit();

                startActivity(new Intent(TDashboardActivity.this,Page1.class));
                finish();   //finish current activity
            }
        });


    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 2000);
    }


}



