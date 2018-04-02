package distudios.at.carcassonne.networking;

public class NetworkController implements INetworkController {

    private boolean isGroupOwner;

    @Override
    public void createConnection(boolean isGroupOwner) {

    }

    @Override
    public void sendDate(Object data, int type) {

    }

    @Override
    public Object receiveData(int type) {
        return null;
    }

    @Override
    public boolean isGroupOwner() {
        return isGroupOwner;
    }
}
