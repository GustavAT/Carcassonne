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
}