package com.example.neha.student_guide;



import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


public class RemAdapter extends RecyclerView.Adapter<RemAdapter.ViewHolder> {

    private Context context,cont;

    private List<Remind> rems;
    public String dateof,remname;

    public RemAdapter(Context context,List<Remind> uploads) {

        this.context = context;
        this.cont = cont;
        this.rems = uploads;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        this.context = context;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_reminder, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.remButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               // String parts[] = dateof.split("/");
               // int day = Integer.parseInt(parts[0]);
               // int month = Integer.parseInt(parts[1]);

                //int year = Integer.parseInt(parts[2]);

                //reminder();
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setData(CalendarContract.Events.CONTENT_URI);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra(CalendarContract.Events.TITLE, remname);
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "SFIT");
                calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "REMINDER");

                GregorianCalendar calDate = new GregorianCalendar(2017, 3, 20);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        calDate.getTimeInMillis());
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        calDate.getTimeInMillis());

                context.startActivity(calIntent);


            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Remind rem = rems.get(position);

        holder.textViewName.setText(rem.getRem());
        holder.textViewDate.setText(rem.getDos());





    }




    @Override
    public int getItemCount() {
        return rems.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewDate;
        public Button remButton;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            remButton = (Button) itemView.findViewById(R.id.set_reminder);
        }
    }






}