package com.droidmarvin.interfacingservomotorusingpwm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.Pwm;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private static final String PWM_SERVO_BUS = "PWM1";

    int i;

    private Pwm mServoPwm;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupServo();
        setTimer();
    }

    // close the servo when finished to release resources
    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyServo();
    }

    // create timer method
    private void setTimer (){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                nextMove();
            }
        }, 0, 3000);
    }

    // access the servo
    private void setupServo (){
        try {
            PeripheralManagerService service = new PeripheralManagerService();
            mServoPwm = service.openPwm(PWM_SERVO_BUS);

            mServoPwm.setPwmFrequencyHz(50);
        }catch (IOException e){
            Log.e(TAG, "error creating Servo", e);
        }
    }

    private void nextMove() {
        switch (i % 3) {
            case 0:
                turn0Degree();
                break;
            case 1:
                turn90Degree();
                break;
            case 2:
                turn180Degree();
                break;
        }
        i++;
    }

    // turn servo motors to it's new positions
    private void turn0Degree() {
        try {
            mServoPwm.setPwmDutyCycle(2.5);
            mServoPwm.setEnabled(true);
            Log.e(TAG,"Swing0");
        } catch (IOException e) {
            // error setting servo
        }
    }
    private void turn90Degree() {
        try {
            mServoPwm.setPwmDutyCycle(7.5);
            Log.e(TAG,"Swing90");
        } catch (IOException e) {
            // error setting servo
        }
    }
    private void turn180Degree() {
        try {
            mServoPwm.setPwmDutyCycle(12.5);
            Log.e(TAG,"Swing180");
        } catch (IOException e) {
            // error setting servo
        }
    }

    // close servo method
    private void destroyServo() {
        if (mServoPwm != null) {
            try {
                mServoPwm.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing Servo");
            } finally {
                mServoPwm = null;
            }
        }
    }
}
