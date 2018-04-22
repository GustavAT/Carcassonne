package distudios.at.carcassonne.networking.connection;

import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.SalutDevice;

public class DeviceCallback implements SalutDeviceCallback {

    @Override
    public void call(SalutDevice salutDevice) {
        if (callback != null) {
            callback.call(salutDevice);
        }
    }

    public static IDeviceCallback callback;

    public interface IDeviceCallback {
        void call(SalutDevice salutDevice);
    }
}
