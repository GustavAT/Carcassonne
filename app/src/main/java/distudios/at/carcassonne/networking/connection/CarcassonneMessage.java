package distudios.at.carcassonne.networking.connection;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.HashMap;
import java.util.Map;

import distudios.at.carcassonne.engine.logic.GameState;

/**
 * This was coded under heavy LSD influence jk
 * Container class for all messages send between devices
 */
@JsonObject
public class CarcassonneMessage {

    @JsonField
    public int type;

    @JsonField
    public GameState state;

    @JsonField
    public Map<String, Integer> playerMappings;

    @JsonField
    public PlayerInfo playerInfo;

    @JsonField
    public String other;

    public CarcassonneMessage()
    {
        type = UNSPECIFIED;
        state = null;
        playerMappings = new HashMap<>();
        other = null;
        playerInfo = new PlayerInfo();
    }

    public final static int UNSPECIFIED = -1;
    public final static int DUMMY_MESSAGE = 0;
    public final static int GAME_STATE_UPDATE = 1;
    public final static int END_TURN = 2;
    public final static int PLAYER_CHEAT = 3;

    // some random number lol
    public final static int HOST_START_GAME = 12;
}
