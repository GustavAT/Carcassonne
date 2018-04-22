package distudios.at.carcassonne.networking.connection;

import com.peak.salut.Callbacks.SalutCallback;

public class DiscoveryCallback implements SalutCallback {

    public static IDiscoveryCallback callback;

    @Override
    public void call() {
        if (callback != null) {
            callback.call();
        }
    }

    public interface IDiscoveryCallback {
        void call();
    }
}
