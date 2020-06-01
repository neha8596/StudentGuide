package com.example.neha.student_guide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

public class TRegisterActivity extends AppCompatActivity {
    EditText tusername,tsid, tpassword, tToken;
    Button registerButton;
    String user,Pid, pass,token;
    String tokenId= String.valueOf(1997);
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tregister);

        tusername = (EditText)findViewById(R.id.tusername);
        tsid = (EditText)findViewById(R.id.sid);
        tpassword = (EditText)findViewById(R.id.tpassword);
        tToken = (EditText)findViewById(R.id.tToken);
        registerButton = (Button)findViewById(R.id.tregisterButton);
        login = (TextView)findViewById(R.id.tlogin);

        Firebase.setAndroidContext(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TRegisterActivity.this, TLoginActivity.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = tusername.getText().toString();
                Pid = tsid.getText().toString();
                pass = tpassword.getText().toString();
                token = tToken.getText().toString();


                if(user.equals("")){
                    tusername.setError("can't be blank");
                }
                else if(Pid.equals("")){
                    tsid.setError("can't be blank");
                }
                else if(pass.equals("")){
                    tpassword.setError("can't be blank");
                }
                else if(token.equals("")){
                    tToken.setError("can't be blank");
                }
                else if(!user.matches("[A-Za-z0-9]+")){
                    tusername.setError("only alphabet or number allowed");
                }
                else if(user.length()<5){
                    tusername.setError("at least 5 characters long");
                }
                else if(pass.length()<5){
                    tpassword.setError("at least 5 characters long");
                }
                else if(!token.equals(tokenId)){
                    tToken.setError("Wrong Key");
                }
                else {
                    final ProgressDialog pd = new ProgressDialog(TRegisterActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    String url = "https://student-guide-2339e.firebaseio.com/teachers.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            Firebase reference = new Firebase("https://student-guide-2339e.firebaseio.com/teachers");

                            if(s.equals("null")) {
                                reference.child(user).child("sid").setValue(Pid);
                                reference.child(user).child("password").setValue(pass);
                                Toast.makeText(TRegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(TRegisterActivity.this, TLoginActivity.class));
                            }
                            else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(user)) {
                                        reference.child(user).child("pid").setValue(Pid);
                                        reference.child(user).child("password").setValue(pass);
                                        Toast.makeText(TRegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(TRegisterActivity.this, TLoginActivity.class));
                                    } else {
                                        Toast.makeText(TRegisterActivity.this, "username already exists", Toast.LENGTH_LONG).show();
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
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(TRegisterActivity.this);
                    rQueue.add(request);
                }
            }
        });
    }
}
