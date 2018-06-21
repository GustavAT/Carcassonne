package distudios.at.carcassonne.engine.misc;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

import distudios.at.carcassonne.R;

public class SoundController implements ISoundController {
    private MediaPlayer background_music;
    private Boolean Background_music_state;
    private Context context;

    private SoundPool soundPool;
    private SoundPool.Builder soundPoolBilder;
    private AudioAttributes attributes;
    private AudioAttributes.Builder attributesBuilder;
    private int soundID;
    private Boolean sound_state;



    public SoundController(Context context) {
        background_music=new MediaPlayer();
        this.context=context;
        bildSound();
        loadSound();
    }

    public Context getContext(){
        return context;
    }

    @Override
    public void setBackground_music_state(boolean background_music_state) {
        Background_music_state = background_music_state;
    }

    @Override
    public Boolean getBackground_music_state() {
        return Background_music_state;
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
            Log.e("SOUNDCONTROLLER", e.getMessage());
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
    public void bildSound() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            attributesBuilder = new AudioAttributes.Builder();
            attributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
            attributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
            attributes=attributesBuilder.build();

            soundPoolBilder = new SoundPool.Builder();
            soundPoolBilder.setAudioAttributes(attributes);
            soundPool=soundPoolBilder.build();
        }else{
            soundPool= new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }
    }

    @Override
    public Boolean getSound_state() {
        return sound_state;
    }

    @Override
    public void setSound_state(Boolean sound_state) {
        this.sound_state = sound_state;
    }

    public void playSound(){
        soundPool.play(1,1,1,0,0,1);
    }

    public void stopSound(){
        soundPool.release();
    }
}
