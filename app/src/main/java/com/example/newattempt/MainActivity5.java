package com.example.newattempt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
//import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class MainActivity5 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
private long lastPause;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ImageView nImageView;
    private Button removebutton;

    private TextView timerText;
    private Button stopStartButton;
    private Chronometer mChronometer;
    private Timer timer;
    private TimerTask timerTask;

    private Uri mImageUri;
private Button showTime;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
private TextView back;
    public MainActivity5() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
this.setTitle("Set Profile Photo");
        Button mButtonChooseImage = findViewById(R.id.button_choose_image);
        Button mButtonUpload = findViewById(R.id.button_upload);

        mImageView = findViewById(R.id.image_view);
        back = findViewById(R.id.textView2);
        removebutton = findViewById(R.id.button2);
showTime = findViewById(R.id.showtime);
        mChronometer = (Chronometer) findViewById(R.id.chronometers);

            mChronometer.setBase(SystemClock.elapsedRealtime());
            showElapsedTime();




        mChronometer.start();

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = getInstance().getReference("uploads");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentfunction();
            }
        });
        mChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.setVisibility(View.GONE);
                showTime.setVisibility(View.VISIBLE);
            }
        });

        DatabaseReference getImage = FirebaseDatabase.getInstance().getReference("uploads/Username/imageUrl");

        // Adding listener for a single change
        // in the data at this location.
        // this listener will triggered once
        // with the value of the data at the location
        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot daataSnapshot) {
                // getting a DataSnapshot for the location at the specified
                // relative path and getting in the link variable
                String link = daataSnapshot.getValue(String.class);


                Picasso.get().load(link).into(mImageView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MainActivity5.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
            }});
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonUpload.setVisibility(View.VISIBLE);
                openFileChooser();

            }
        });
        removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removephoto();
            }
        });
        showTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.setVisibility(View.VISIBLE);
                showTime.setVisibility(View.GONE);
            }
        });

        mButtonUpload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mUploadTask != null && mUploadTask.isInProgress()) {
                            Toast.makeText(MainActivity5.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                        } else {
                            uploadFile();
                        }
                    }
        });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
           Uri mImageUri = data.getData();


            SharedPreferences sharedPref = getSharedPreferences("MyData",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("name",mImageUri.toString());

            editor.apply();

            Picasso.get().load(mImageUri).into(mImageView);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        SharedPreferences sharedPref = getSharedPreferences("MyData", MODE_PRIVATE);
        String mImageUrishared = sharedPref.getString("name","No Name");
        Uri mImageUri = Uri.parse(mImageUrishared);



        if (mImageUri != null) {
            DatabaseReference ref = getInstance().getReference();
            Query applesQuery = ref.child("uploads").orderByChild("name").equalTo("Username");

            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            StorageReference fileReference = mStorageRef.child("Username"
                    + "." + getFileExtension( mImageUri));
            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
            {
                @Override
                public void onSuccess(Uri downloadUrl)
                {
                    String hi = downloadUrl.toString();
                    SharedPreferences sharedPreff = getSharedPreferences("MyDataa",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreff.edit();
                    editor.putString("namee",hi);

                    editor.apply();


                }
            });


            mUploadTask = fileReference.putFile(mImageUri)
                   .addOnSuccessListener(this::onSuccess)
                   .addOnFailureListener(e -> Toast.makeText(MainActivity5.this, e.getMessage(), Toast.LENGTH_SHORT).show());

       } else {
           Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

        Toast.makeText(MainActivity5.this, "Upload successful", Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreff = getSharedPreferences("MyDataa", MODE_PRIVATE);
        String shared = sharedPreff.getString("namee","No Name");


        Upload upload = new Upload("Username".trim(),
                shared);

String uploadId = "Username";

        assert uploadId != null;
        mDatabaseRef.child(uploadId).setValue(upload);
    }
    private void intentfunction(){
        lastPause = SystemClock.elapsedRealtime();
        SharedPreferences sharedp = getSharedPreferences("MyTimee",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedp.edit();
        editor.putFloat("timee",lastPause);

        editor.apply();
        mChronometer.stop();

        showElapsedTime();
        Intent toSecond = new Intent();
        toSecond.setClass(this, MainActivity6.class);
        startActivity(toSecond);
    }
    private void removephoto(){
DatabaseReference remove = FirebaseDatabase.getInstance().getReference("Default");
remove.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String link = snapshot.getValue(String.class);

        Picasso.get().load(link).into(mImageView);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(MainActivity5.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
    }
});

    }
    private void onRemoval(UploadTask.TaskSnapshot taskSnapshot) {

        Toast.makeText(MainActivity5.this, "Profile photo removed successfully", Toast.LENGTH_LONG).show();


    }

    private void showElapsedTime() {
        long elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();


        long two = mChronometer.getBase();

        SharedPreferences shared = getSharedPreferences("MyTime",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putFloat("time",elapsedMillis);

        editor.apply();
    }
}


