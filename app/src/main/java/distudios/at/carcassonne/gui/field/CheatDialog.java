package distudios.at.carcassonne.gui.field;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.IGameController;

public class CheatDialog extends DialogFragment {
    private ImageButton currentimg, firstimg , secondimg , thirdimg;
    private Button cheater;
    private LinearLayout cheaterLayout , currentCardlayout;
    private Integer bitmapInteger;
    private DialogListener listener;

    //Sensor Variables
    private SensorManager sensorManager;
    private float accelVal;
    private float accelLast;
    private float shake;

    List<Card> drawnCards = new ArrayList<>();
    private Card card;
    final IGameController controller = CarcassonneApp.getGameController();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       drawnCards = new ArrayList<>();
       drawnCards = controller.drawCards();
       card=controller.drawCard();



        //Dialog build
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(( R.layout.cheat_function),null);

        //ImageButton and Button connection
        cheater = view.findViewById(R.id.cheaterButton);
        currentimg = view.findViewById(R.id.currentimg);
        firstimg = view.findViewById(R.id.firstChoice);
        secondimg= view.findViewById(R.id.secondChoice);
        thirdimg = view.findViewById(R.id.thirdChoice);
        //layouts
        cheaterLayout= view.findViewById(R.id.cheater_Layout);
        currentCardlayout= view.findViewById(R.id.current_Card_Layout);
        //set View
        builder.setView(view);

        //Current card


        CreateBitmap(currentimg, card.getId());

        currentimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getBitmapInteger(card);
                getDialog().dismiss();
            }
        });

        if (drawnCards.size()>2){
            CreateBitmap(thirdimg,drawnCards.get(2).getId());



            thirdimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getBitmapInteger(drawnCards.get(2));
                    getDialog().dismiss();
                }
            });
        }

        if (drawnCards.size()>1){
            CreateBitmap(secondimg,drawnCards.get(1).getId());
            secondimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getBitmapInteger(drawnCards.get(1));
                    getDialog().dismiss();
                }
            });
        }

        if (drawnCards.size()>0){
            CreateBitmap(firstimg,drawnCards.get(0).getId());
            firstimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getBitmapInteger(drawnCards.get(0));
                    getDialog().dismiss();
                }
            });

        }





        //Catch the cheater
        cheater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(controller.isCheating()){
                    Toast.makeText(getContext(),"Cheater",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"No one is cheating",Toast.LENGTH_LONG).show();
                }
            }
        });


        //Shake Sensor
        sensorManager =(SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        accelVal =SensorManager.GRAVITY_EARTH;
        accelLast=SensorManager.GRAVITY_EARTH;
        shake =0.00f;

        return builder.create();
    }


    public void CreateBitmap(ImageView view, Integer integer){
        BitmapDrawable bitmapDrawable =new BitmapDrawable(getResources(),PlayfieldView.cardIdToBitmap(integer));
        view.setImageDrawable(bitmapDrawable);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener= (DialogListener)getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogListener");
        }
    }

    public interface DialogListener{
        void getBitmapInteger( Card choosenCard);
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
                currentCardlayout.setVisibility(View.INVISIBLE);
                cheaterLayout.setVisibility(View.VISIBLE);
                controller.setCheating(true);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
