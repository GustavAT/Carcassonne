package distudios.at.carcassonne.engine.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Container for the game-state
 */
public enum CState {
    WAITING(0), DRAW_CARD(1), PLACE_CARD(2), PLACE_FIGURE(3), END_TURN(4);

    private int value;
    private static Map map = new HashMap<>();

    CState(int value) {
        this.value = value;
    }

    static {
        for (CState cState : CState.values()) {
            map.put(cState.value, cState);
        }
    }

    public static CState valueOf(int oType) {
        return (CState) map.get(oType);
    }

    public int getValue() {
        return value;
    }
}
