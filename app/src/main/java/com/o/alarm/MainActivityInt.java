package com.o.alarm;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

interface MainActivityInt {
    void onSensorChanged(SensorEvent event);

    void onAccuracyChanged(Sensor sensor, int accuracy);
}
