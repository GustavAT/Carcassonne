package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;

/**
 * Created by Simon on 26.04.2018.
 */

public enum PeepPosition {
    Top,
    Left,
    Right,
    Center,
    Bottom;


    public static ArrayList<PeepPosition> getPeepPositions(){
        ArrayList<PeepPosition> peepPositions = new ArrayList<PeepPosition>();
        peepPositions.add(Top);
        peepPositions.add(Left);
        peepPositions.add(Right);
        peepPositions.add(Center);
        peepPositions.add(Bottom);

        return peepPositions;
    }

    public static int fromPosition(PeepPosition p) {
        if (p == Top) {
            return 0;
        } else if (p == Left) {
            return 1;
        } else if (p == Right) {
            return 2;
        } else if (p == Center) {
            return 3;
        } else if (p == Bottom) {
            return 4;
        }
        return 8;
    }

    public static PeepPosition fromInt(int p) {
        if (p == 0) {
            return Top;
        } else if (p == 1) {
            return Left;
        } else if (p == 2) {
            return Right;
        } else if (p == 3) {
            return Center;
        } else if (p == 4) {
            return Bottom;
        }
        return Top;
    }
}