package distudios.at.carcassonne;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import java.io.IOException;

public class CarcassonneAppClass extends Application implements Application.ActivityLifecycleCallbacks {
    Boolean Background_music_state;
    MediaPlayer background_music;




    @Override
    public void onCreate() {
        super.onCreate();
        background_music=new MediaPlayer();
        registerActivityLifecycleCallbacks(this);

        SharedPreferences sharedPref = getSharedPreferences("myPreff",0 );
        setBackground_music_state(sharedPref.getBoolean("music_switch_state",true));

    }

    public Boolean getBackground_music_state() {
        return Background_music_state;
    }

    public void setBackground_music_state(boolean background_music_state) {
        Background_music_state = background_music_state;
    }
    protected void stopBackground_music(){

        background_music.stop();
    }

    protected void startBackground_music(){

        background_music.reset();
        background_music.setLooping(true);

        try{
            background_music.setDataSource(getApplicationContext(), Uri.parse("android.resource://distudios.at.carcassonne/" +R.raw.backgroundloop));
            background_music.prepare();
            background_music.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {}
    @Override
    public void onActivityStarted(Activity activity) {}
    @Override
    public void onActivityResumed(Activity activity) {
        count.cancel();
        if(Background_music_state){
            resumeMusic();
        }
    }

    @Override
    public void onActivityPaused(final Activity activity) {
        count.start();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        SharedPreferences sharedPref = getSharedPreferences("myPreff",0 );
        SharedPreferences.Editor editor =sharedPref.edit();
        editor.putBoolean("music_switch_state",getBackground_music_state());
        editor.commit();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}

    CountDownTimer count = new CountDownTimer(500,1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            stopBackground_music();
        }
    };

    public void resumeMusic(){
        if(background_music.isPlaying()==false)
        {
            startBackground_music();
        }
    }
}
