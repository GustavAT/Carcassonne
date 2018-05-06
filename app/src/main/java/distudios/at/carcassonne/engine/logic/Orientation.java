package distudios.at.carcassonne.engine.logic;

import java.util.HashMap;
import java.util.Map;

public enum Orientation {
    NORTH(0), EAST(1), SOUTH(2), WEST(3);

    private int value;
    private static Map map = new HashMap<>();

    Orientation(int value) {
        this.value = value;
    }

    static {
        for (Orientation pageType : Orientation.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static Orientation valueOf(int oType) {
        return (Orientation) map.get(oType);
    }

    public int getValue() {
        return value;
    }
}