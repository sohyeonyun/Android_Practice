package com.example.sensorex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends Activity {

    TextView t_light, t_proximity, t_acc;  //조도, 근접센서

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 화면 세팅
        t_light = new TextView(this);
        t_proximity = new TextView(this);
        t_acc = new TextView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(t_light);
        layout.addView(t_proximity);
        layout.addView(t_acc);
        setContentView(layout);

        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.accuracy != SensorManager.SENSOR_STATUS_UNRELIABLE) {
                    float[] v = sensorEvent.values;
                    switch (sensorEvent.sensor.getType()) {
                        case Sensor.TYPE_LIGHT:
                            t_light.setText("조도 = " + v[0]);
                            break;
                        case Sensor.TYPE_PROXIMITY:
                            t_proximity.setText("근접 = " + v[0]);
                            break;
                        case Sensor.TYPE_ACCELEROMETER:
                            t_acc.setText("x == " + v[0] + '\n'
                                    + "y == " + v[1] + '\n'
                                    + "z == " + v[2]);
                            break;
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_FASTEST);
        manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


    }
}


/*
    // 핸드폰 상의 센서 목록 가져오기

        setContentView(R.layout.activity_main);

        String result = "";
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> arSensor = sm.getSensorList(Sensor.TYPE_ALL);
        result = "디바이스에존재하는센서총개수= " + arSensor.size() + '\n';
        for (Sensor s : arSensor) {
            result = result + "vender = " + s.getVendor() + '\n'
                    + "type = " + s.getType() + '\n'
                    + "name = " + s.getName() + '\n'
                    + "version = " + s.getVersion() + '\n'
                    + "power = " + s.getPower() + '\n'
                    + "res = " + s.getResolution() + '\n'
                    + "range = " + s.getMaximumRange() + '\n';
            result += '\n';
        }
        EditText et = new EditText(this);
        et.setText(result);
        setContentView(et);
 */