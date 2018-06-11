package distudios.at.carcassonne.networking.connection;

import com.bluelinelabs.logansquare.typeconverters.IntBasedTypeConverter;
import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;

import distudios.at.carcassonne.engine.logic.Orientation;
import distudios.at.carcassonne.engine.logic.PeepPosition;

public class PeepTypeConverter extends IntBasedTypeConverter<PeepPosition> {

    @Override
    public PeepPosition getFromInt(int i) {
        return PeepPosition.fromInt(i);
    }

    @Override
    public int convertToInt(PeepPosition object) {
        return PeepPosition.fromPosition(object);
    }
}
