package distudios.at.carcassonne.networking.connection;

import com.bluelinelabs.logansquare.LoganSquare;
import com.peak.salut.Callbacks.SalutDataCallback;

public class DataCallback implements SalutDataCallback {

    public static IDataCallback callback;

    @Override
    public void onDataReceived(Object o) {
        if (callback != null) {
            try {
                CarcassonneMessage message = LoganSquare.parse(String.valueOf(o), CarcassonneMessage.class);
                callback.onDataReceived(message);
            } catch(Exception e) {

            }


        }
    }

    public interface IDataCallback{
        void onDataReceived(CarcassonneMessage message);
    }
}
