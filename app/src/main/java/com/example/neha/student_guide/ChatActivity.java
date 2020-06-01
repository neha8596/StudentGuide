package com.example.neha.student_guide;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.app.NotificationManager;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.Context;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    LinearLayout layout;
   // ImageView sendButton;
    Button sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    int notifID = 33;
    NotificationManager notificationManager;
    boolean notifActive = false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout)findViewById(R.id.layout1);
       // sendButton = (ImageView)findViewById(R.id.sendButton);
        sendButton = (Button)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://student-guide-2339e.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://student-guide-2339e.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String messageText = messageArea.getText().toString();

                        EditText et=(EditText) findViewById(R.id.messageArea);
                        et.setText("");


                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);




                }


            }

       //  showNotification.performClick();


        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(UserDetails.username)){
                    addMessageBox("Me:\n" + message, 1);
                }
                else{
                    addMessageBox(UserDetails.chatWith + ":\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, int type){

        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);


        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);

        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    public void showNotification(View view){
        NotificationCompat.Builder notificBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle("Student guide")
                .setContentText("Hello")
                .setTicker("Alert New Message")
                .setSmallIcon(R.drawable.ic_notif);

        //Intent moreinfo = new Intent(this, ChatActivity.class);

        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(this);
        tStackBuilder.addParentStack(ChatActivity.class);
       // tStackBuilder.addNextIntent(moreinfo);
        PendingIntent pendingIntent = tStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        notificBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager  = (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.notify(notifID,notificBuilder.build());
        notifActive=true;
    }

    public void stopNotification(View view){

        if(notifActive)
        {
            notificationManager.cancel(notifID);
        }
    }



    /*public  void setAlarm(View view)
    {
        Long aletTime= new GregorianCalendar().getTimeInMillis()+5*1000;
        Intent alertIntent = new Intent(this,AlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(this, 1 , alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }*/

}