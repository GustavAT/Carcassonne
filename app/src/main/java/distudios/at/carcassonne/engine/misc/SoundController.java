package distudios.at.carcassonne.engine.misc;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;

import java.io.IOException;

import distudios.at.carcassonne.R;

public class SoundController implements ISoundController {
    private MediaPlayer background_music;
    private Boolean backgroundMusicState;
    private Context context;

    private SoundPool soundPool;
    private SoundPool.Builder soundPoolBuilder;
    private AudioAttributes attributes;
    private AudioAttributes.Builder attributesBuilder;
    private int soundID;
    private Boolean soundState;




    public SoundController(Context context) {
        background_music=new MediaPlayer();
        this.context=context;
        buildSound();
        loadSound();
    }

    public Context getContext(){
        return context;
    }

    @Override
    public void setBackgroundMusicState(boolean backgroundMusicState) {
        this.backgroundMusicState = backgroundMusicState;
    }

    @Override
    public Boolean getBackgroundMusicState() {
        return backgroundMusicState;
    }

    @Override
    public void startBackground_music() {
        background_music.reset();
        background_music.setLooping(true);

        try{
            background_music.setDataSource(context, Uri.parse("android.resource://distudios.at.carcassonne/" + R.raw.backgroundloop));
            background_music.prepare();
            background_music.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void stopBackground_music() {
        background_music.stop();
    }

    @Override
    public void resumeMusic() {
        if(!background_music.isPlaying())
        {
            startBackground_music();
        }
    }

    @Override
    public void loadSound() {
        soundID=soundPool.load(context, R.raw.click,1);
    }

    @Override
    public void buildSound() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            attributesBuilder = new AudioAttributes.Builder();
            attributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
            attributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
            attributes=attributesBuilder.build();

            soundPoolBuilder = new SoundPool.Builder();
            soundPoolBuilder.setAudioAttributes(attributes);
            soundPool= soundPoolBuilder.build();
        }else{
            soundPool= new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }
    }

    @Override
    public Boolean getSoundState() {
        return soundState;
    }

    @Override
    public void setSoundState(Boolean soundState) {
        this.soundState = soundState;
    }

    public void playSound(){
        soundPool.play(1,1,1,0,0,1);
    }

    public void stopSound(){
        soundPool.release();
    }
}
