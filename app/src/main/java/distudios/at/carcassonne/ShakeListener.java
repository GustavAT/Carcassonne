package distudios.at.carcassonne;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ShakeListener extends AppCompatActivity {

    private SensorManager sensorManager;
    private float accelVal;
    private float accelLast;
    private float shake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_listener);

        sensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        accelVal =SensorManager.GRAVITY_EARTH;
        accelLast=SensorManager.GRAVITY_EARTH;
        shake =0.00f;

    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            accelLast = accelVal;
            accelVal =(float) Math.sqrt((double)(x*x + y*y + z*z));
            float delta= accelVal - accelLast;
            shake= shake * 0.9f + delta;

            if (shake>12){
                Toast toast = Toast.makeText(getApplicationContext(),"shake it shake it",Toast.LENGTH_LONG);
                toast.show();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
