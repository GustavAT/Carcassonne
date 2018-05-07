package distudios.at.carcassonne;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import distudios.at.carcassonne.engine.logic.GameController;
import distudios.at.carcassonne.engine.logic.IGameController;
import distudios.at.carcassonne.gui.ISoundController;
import distudios.at.carcassonne.gui.SoundController;
import distudios.at.carcassonne.networking.INetworkController;
import distudios.at.carcassonne.networking.NetworkController;

/**
 * Hold global game states here
 */
public class CarcassonneApp extends Application implements Application.ActivityLifecycleCallbacks{

    private static INetworkController networkController;
    private static IGameController gameController;
    private static ISoundController soundController;

    private static String playerName = "";

    @Override
    public void onCreate() {
        super.onCreate();
        gameController = new GameController();
        networkController = new NetworkController();
        soundController= new SoundController(this);

        //Save switch State

        SharedPreferences sharedPref = getSharedPreferences("myPreff",0 );
        soundController.setBackground_music_state(sharedPref.getBoolean("music_switch_state",false));
        soundController.setSound_state(sharedPref.getBoolean("sound_switch_state",false));
        registerActivityLifecycleCallbacks(this);


        if(!soundController.getSound_state()) {
            soundController.stopSound();
        }
    }

    public static ISoundController getSoundController(){
        return soundController;
    }

    public static INetworkController getNetworkController() {
        return networkController;
    }

    public static IGameController getGameController() {
        return gameController;
    }

    //app Lifecycle


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {}
    @Override
    public void onActivityStarted(Activity activity) {}
    @Override
    public void onActivityResumed(Activity activity) {
        count.cancel();
        if(soundController.getBackground_music_state()){
            soundController.resumeMusic();
        }
    }

    //stop music if app is paused for 0,5 s
    @Override
    public void onActivityPaused(final Activity activity) {
        count.start();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        SharedPreferences sharedPref = getSharedPreferences("myPreff",0 );

        SharedPreferences.Editor editor =sharedPref.edit();
        editor.putBoolean("music_switch_state",soundController.getBackground_music_state());
        editor.putBoolean("sound_switch_state",soundController.getSound_state());
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
            soundController.stopBackground_music();
        }
    };

    /**
     * Set player name
     */
    public static void setPlayerName(String name) {
        if (playerName.isEmpty()) {
            playerName = name;
        }
    }

    /**
     * Get player name
     */
    public static String getPlayerName() {
        return playerName;
    }
}
