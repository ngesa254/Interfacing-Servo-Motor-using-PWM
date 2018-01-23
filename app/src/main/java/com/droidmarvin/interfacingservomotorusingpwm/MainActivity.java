package com.droidmarvin.interfacingservomotorusingpwm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.Pwm;

import java.io.IOException;
import java.util.Timer;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private static final String PWM_SERVO_BUS = "PWM1";

    int i;

    private Pwm mServoPwm;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
