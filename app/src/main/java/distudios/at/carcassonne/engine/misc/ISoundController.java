package distudios.at.carcassonne.engine.misc;

import android.os.CountDownTimer;

public interface ISoundController {
    void setBackground_music_state(boolean background_music_state);
    Boolean getBackground_music_state();
    void startBackground_music();
    void stopBackground_music();
    void resumeMusic();

    void buildSound();
    Boolean getSound_state();
    void setSound_state(Boolean sound_state);
    void loadSound();
    void playSound();
    void stopSound();
}
