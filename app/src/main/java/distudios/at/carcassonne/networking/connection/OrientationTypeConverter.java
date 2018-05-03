package distudios.at.carcassonne.networking.connection;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;

import distudios.at.carcassonne.engine.logic.Orientation;

public class OrientationTypeConverter extends StringBasedTypeConverter<Orientation> {

    @Override
    public Orientation getFromString(String string) {
        return Orientation.valueOf(string);
    }

    @Override
    public String convertToString(Orientation object) {
        return object.toString();
    }
}
