package com.example.flash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private boolean torchon = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onoff(View view){
        // Todo: 1 connect camera manager (which manages camera flashlight)
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            for(String id : cameraManager.getCameraIdList()){
                CameraCharacteristics cameraChar = cameraManager.getCameraCharacteristics(id);
                if(cameraChar.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)){
                    //Todo: 2 Ask if theere is a camera which has a flashlight
                    if(!torchon) {
                        //todo: 3 set the flashlight of that camera  on ()torch modetrue or not
                        cameraManager.setTorchMode(id, true);
                        Toast.makeText(MainActivity.this, "Flashlight is on", Toast.LENGTH_SHORT).show();
                    }else{
                        cameraManager.setTorchMode(id, false);
                        Toast.makeText(MainActivity.this, "Flash is off", Toast.LENGTH_SHORT).show();
                    }torchon = !torchon;
                }

            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    public void startSensors(View view) {
        //todo : start listening to acceleration sensor event (x,y,z position of the device)
        //todo 1 : Connect sensor manager
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        LocationManager map = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Lists the number of sensor types available in the device using TOAST
//        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
//        for(Sensor s: sensorList){
//            Toast.makeText(this, s.getName(), Toast.LENGTH_SHORT).show();
//        }
        //todo 2 : register listen to acclerometer sensor events from teh HW
        Sensor acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
    //getting sensor events
        // Reaad xyz values and shwo in textview
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        TextView sensorTextView = (TextView) findViewById(R.id.SensorTextView);
        sensorTextView.setText("X: "+x+ "Y: "+y+ "Z: "+z);
        ConstraintLayout backgg = (ConstraintLayout) findViewById(R.id.backg);
        if (z>-0.3 && z<0.3){
//            TextView background = (TextView) findViewById(R.id.backg);

            backgg.setBackgroundColor(Color.GREEN);
        }else{
            backgg.setBackgroundColor(Color.BLUE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}