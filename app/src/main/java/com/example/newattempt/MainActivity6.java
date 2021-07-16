package com.example.newattempt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity6 extends AppCompatActivity {




    private Chronometer mChronometer;
private Button goback;
    private long lastPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
goback = (Button) findViewById(R.id.goback);
        SharedPreferences shared = getSharedPreferences("MyTime", MODE_PRIVATE);
        float timesy = shared.getFloat("time", 0);
        mChronometer = (Chronometer) findViewById(R.id.chronometer);

        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        showElapsedTime();
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gobacky();
            }
        });
    }


private void gobacky(){
    Intent toSecondy = new Intent();
    SharedPreferences sharedp = getSharedPreferences("MyTimee", MODE_PRIVATE);
    float timesyy= sharedp.getFloat("timee", 0);
    toSecondy.setClass(this, MainActivity5.class);
    startActivity(toSecondy);
}
    private void showElapsedTime() {
        long elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
        long two = mChronometer.getBase();

    }
}
