package com.example.hw1_netanelhabas;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorDetector {
    private Context context;
    private GameManager gameManager;
    private SensorManager mSensorManager;
    private Sensor sensor;
    // TODO: 12/9/2022 callback to MainActivity to update the visible of the miner
    long timeStamp = 0;
    public interface CallBack_MinerView {
        void moveMinerBySensor(int index);
    }
    private CallBack_MinerView callBack_minerView;
    public SensorDetector(Context context,CallBack_MinerView callBack_minerView,GameManager gameManager) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBack_minerView = callBack_minerView;
        this.gameManager=gameManager;
    }



    /**
     * register to the sensors
     */
    public void start() {
        mSensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * unregister to the sensors
     */
    public void stop() {
        mSensorManager.unregisterListener(sensorEventListener);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];

            calculateStep(x, y);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void calculateStep(float x, float y) {
        if (x > 3.0) {//left
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = System.currentTimeMillis();
                callBack_minerView.moveMinerBySensor(-1);



            }
        }
        if (x < -3.0) {//right
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = System.currentTimeMillis();
                callBack_minerView.moveMinerBySensor(1);



            }
        }

//        if (y > 6.0) {
//            if (System.currentTimeMillis() - timeStamp > 500) {
//                timeStamp = System.currentTimeMillis();
//
//            }
//        }
    }
}