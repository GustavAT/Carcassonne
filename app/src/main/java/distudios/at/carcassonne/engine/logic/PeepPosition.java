package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;

/**
 * Created by Simon on 26.04.2018.
 */

public enum PeepPosition {
    TopLeft, Top, TopRight,
    Left,
    Right,
    Center,
    BottomLeft, Bottom, BottomRight;


    public static ArrayList<PeepPosition> getPeepPositions(){
        ArrayList<PeepPosition> peepPositions = new ArrayList<PeepPosition>();
        peepPositions.add(TopLeft);
        peepPositions.add(Top);
        peepPositions.add(TopRight);
        peepPositions.add(Left);
        peepPositions.add(Right);
        peepPositions.add(Center);
        peepPositions.add(BottomLeft);
        peepPositions.add(Bottom);
        peepPositions.add(BottomRight);

        return peepPositions;
    }

    public static int fromPosition(PeepPosition p) {
        if (p == TopLeft) {
            return 0;
        } else if (p == Top) {
            return 1;
        } else if (p == TopRight) {
            return 2;
        } else if (p == Left) {
            return 3;
        } else if (p == Right) {
            return 4;
        } else if (p == Center) {
            return 5;
        } else if (p == BottomLeft) {
            return 6;
        } else if (p == Bottom) {
            return 7;
        }
        return 8;
    }

    public static PeepPosition fromInt(int p) {
        if (p == 0) {
            return TopLeft;
        } else if (p == 1) {
            return Top;
        } else if (p == 2) {
            return TopRight;
        } else if (p == 3) {
            return Left;
        } else if (p == 4) {
            return Right;
        } else if (p == 5) {
            return Center;
        } else if (p == 6) {
            return BottomLeft;
        } else if (p == 7) {
            return Bottom;
        }
        return BottomRight;
    }
}