package distudios.at.carcassonne.engine.misc;

import android.os.CountDownTimer;

public interface ISoundController {
    void setBackgroundMusicState(boolean backgroundMusicState);
    Boolean getBackgroundMusicState();
    void startBackground_music();
    void stopBackground_music();
    void resumeMusic();

    void buildSound();
    Boolean getSoundState();
    void setSoundState(Boolean soundState);
    void loadSound();
    void playSound();
    void stopSound();
}
