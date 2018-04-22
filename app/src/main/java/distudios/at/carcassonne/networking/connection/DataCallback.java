package distudios.at.carcassonne.networking.connection;

import com.peak.salut.Callbacks.SalutDataCallback;

public class DataCallback implements SalutDataCallback {

    public static IDataCallback callback;

    @Override
    public void onDataReceived(Object o) {
        if (callback != null) {
            callback.onDataReceived(o);
        }
    }

    public interface IDataCallback{
        void onDataReceived(Object data);
    }
}
