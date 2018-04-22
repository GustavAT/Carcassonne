package distudios.at.carcassonne.networking.connection;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CarcassonneMessage {

    @JsonField
    public int type;

    @JsonField
    public String content;
}
