package com.o.alarm;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity implements MainActivityInt, SensorEventListener {
    private static final String TAG = "MAQJKA5255555";
    TimePicker timeStart, timeEnd;
    int hour, minutes;
    @BindView(R.id.button1)
    Button snimi;
    private static Context CONTEXT;

    public static float timeDiff;

    // Start with some variables
    private SensorManager sensorMan;
    private Sensor accelerometer;
    //    SensorEventListener listener;
    private boolean sensorRegistered = false;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        timeStart = (TimePicker) findViewById(R.id.timePicker);
        timeEnd = (TimePicker) findViewById(R.id.timePicker2);
        timeStart.setIs24HourView(true);
        timeEnd.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timeEnd.setHour(16);
        }
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
//        sensorMan.registerListener(this, accelerometer,
//                SensorManager.SENSOR_DELAY_NORMAL);
//        sensorRegistered = true;

    }

    public static Context getContext() {
        return CONTEXT;
    }

    public void calcDifTime() {
        int difHour = timeEnd.getCurrentHour() - timeStart.getCurrentHour();
        int difMin = timeEnd.getCurrentMinute() - timeStart.getCurrentMinute();
        if (difHour < 0) {

            difHour = difHour + 24;

        }
        int diff = difHour * 60 + difMin;
        Toast.makeText(this, "Vreme" + diff, Toast.LENGTH_SHORT).show();


    }

    @OnClick(R.id.button1)
    public void snimi(View v) {
        calcDifTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMan.unregisterListener(this);
    }

    private int hitCount = 0;
    private double hitSum = 0;
    private double hitResult = 0;

    private final int SAMPLE_SIZE = 50; // change this sample size as you want, higher is more precise but slow measure.
    private final double THRESHOLD = 0.2;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            long SegasnoVreme = System.currentTimeMillis();

            long SenzorVreme = event.timestamp;

//            long timeInMillis = (new Date()).getTime()
//                    + (event.timestamp - System.nanoTime()) / 1000000L;

            long VremeMinuti = TimeUnit.MINUTES.convert(SegasnoVreme, TimeUnit.MILLISECONDS);

            long SenzorMinuti = TimeUnit.MINUTES.convert(SenzorVreme, TimeUnit.MILLISECONDS);

            // Make this higher or lower according to how much
            // motion you want to detect
            if (mAccel > 0.5) {
              //  if (VremeMinuti - SenzorMinuti > 1000000000) {


                    Intent intent = new Intent(this, Main2Activity.class);
                    startActivity(intent);

              //  }


            }

//            if (hitCount <= SAMPLE_SIZE) {
//                hitCount++;
//                hitSum += Math.abs(mAccel);
//            } else {
//                hitResult = hitSum / SAMPLE_SIZE;
//
//                Log.d(TAG, String.valueOf(hitResult));
//
//                if (hitResult > THRESHOLD) {
//                    Intent intent = new Intent(this, Main2Activity.class);
//                startActivity(intent);
//                    Log.d(TAG, "Walking");
//                } else {
//                    Log.d(TAG, "Stop Walking");
//                }
//
//                hitCount = 0;
//                hitSum = 0;
//                hitResult = 0;
//            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }


}
