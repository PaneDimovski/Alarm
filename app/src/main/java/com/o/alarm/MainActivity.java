package com.o.alarm;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    TimePicker timeStart, timeEnd;
    int hour, minutes;
    @BindView(R.id.button1)
    Button snimi;

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


    }

    public void calcDifTime() {
        int difHour = timeEnd.getCurrentHour() - timeStart.getCurrentHour();
        int difMin = timeEnd.getCurrentMinute() - timeStart.getCurrentMinute();
        int diff = difHour * 60 + difMin;
        Toast.makeText(this, "Vreme" + diff, Toast.LENGTH_SHORT).show();



    }

    @OnClick (R.id.button1)
    public  void snimi (View v)
    {
        calcDifTime();
    }

}
