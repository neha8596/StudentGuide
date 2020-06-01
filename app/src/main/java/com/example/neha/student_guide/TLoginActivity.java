package com.example.neha.student_guide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

public class TLoginActivity extends AppCompatActivity {

    TextView tregister;
    EditText tusername, tpassword;
    Button tloginButton;
    String user, pass;
    SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlogin);

        tregister = (TextView)findViewById(R.id.tregister);
        tusername = (EditText)findViewById(R.id.tusername);
        tpassword = (EditText)findViewById(R.id.tpassword);
        tloginButton = (Button)findViewById(R.id.tloginButton);
        sp=getSharedPreferences("login",MODE_PRIVATE);

        //if SharedPreferences contains username and password then directly redirect to Home activity
        if(sp.contains("username") && sp.contains("password")){
            startActivity(new Intent(TLoginActivity.this,TDashboardActivity.class));
            finish();   //finish current activity
        }

        tregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TLoginActivity.this, TRegisterActivity.class));
            }
        });

        tloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = tusername.getText().toString();
                pass = tpassword.getText().toString();

                if(user.equals("")){
                    tusername.setError("can't be blank");
                }
                else if(pass.equals("")){
                    tpassword.setError("can't be blank");
                }
                else{
                    String url = "https://student-guide-2339e.firebaseio.com/teachers.json";
                    final ProgressDialog pd = new ProgressDialog(TLoginActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            if(s.equals("null")){
                                Toast.makeText(TLoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if(!obj.has(user)){
                                        Toast.makeText(TLoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
                                    }
                                    else if(obj.getJSONObject(user).getString("password").equals(pass)){
                                        UserDetails.username = user;
                                        UserDetails.password = pass;
                                        SharedPreferences.Editor e=sp.edit();
                                        e.putString("username",user);
                                        e.putString("password",pass);

                                        e.commit();
                                        startActivity(new Intent(TLoginActivity.this,TDashboardActivity.class));
                                    }
                                    else {
                                        Toast.makeText(TLoginActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }



                            pd.dismiss();
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(TLoginActivity.this);
                    rQueue.add(request);
                }

            }
        });

    }
}