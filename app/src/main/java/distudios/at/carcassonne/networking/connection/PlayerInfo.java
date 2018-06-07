package distudios.at.carcassonne.networking.connection;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 *
 */
@JsonObject
public class PlayerInfo {
    @JsonField
    public String instanceName;
    @JsonField
    public int playerNumber;
    @JsonField
    public int color;
    @JsonField
    public String deviceName;

    public PlayerInfo() {
    }
}
