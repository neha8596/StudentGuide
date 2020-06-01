package com.example.neha.student_guide;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;


public class Page1 extends AppCompatActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
      
        /* for no action bar */
        ActionBar actionBar=getSupportActionBar();actionBar.hide();

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent in=new Intent(Page1.this,LoginActivity.class);
                startActivity(in);

            }

        });
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent in=new Intent(Page1.this,TLoginActivity.class);
                startActivity(in);

            }

        });




    }


    public void onBackPressed() {

            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }




}
