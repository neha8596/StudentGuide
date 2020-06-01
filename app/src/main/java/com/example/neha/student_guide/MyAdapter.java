package com.example.neha.student_guide;



        import android.app.Activity;
        import android.app.DownloadManager;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Environment;
        import android.support.annotation.NonNull;
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
        import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;

    private List<Upload> uploads;
    public String filename,fileurl;
    DownloadManager downloadManager;
    public MyAdapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_images, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.dlButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //downloadFile();

                File dir=Environment.getExternalStoragePublicDirectory(Environment.getExternalStorageDirectory() + "/Student");

                dir.mkdirs();

              //  Uri downloadLocation=Uri.fromFile(new File(dir, filename);
              //  File SDCardRoot = Environment.getDownloadCacheDirectory();
               // File file = new File(SDCardRoot,"suraj.ppt");
                downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                Uri uri = Uri.parse(fileurl+".ppt");
                DownloadManager.Request request = new DownloadManager.Request(uri);


                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename+".ppt");
                request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE)
                        .setDescription("Very Useful PPT.");


                Long reference = downloadManager.enqueue(request);
               // downloadManager.enqueue(request);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Upload upload = uploads.get(position);

        holder.textViewName.setText(upload.getName());
        filename = upload.getName();
        fileurl = upload.getUrl();

        Glide.with(context).load(upload.getUrl()).into(holder.imageView);

    }

    private void downloadFile() {


            FirebaseStorage storage = FirebaseStorage.getInstance();
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            StorageReference storageRef = storage.getReferenceFromUrl("gs://student-guide-2339e.appspot.com/uploads").child(filename);

            //get download file url
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.i("Main", "File uri: " + uri.toString());

                }

            });



        }



    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageView;
        public Button dlButton;
        DownloadManager downloadManager;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            dlButton = (Button) itemView.findViewById(R.id.dlButton);
        }
    }


}