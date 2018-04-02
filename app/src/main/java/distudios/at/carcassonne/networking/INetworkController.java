package distudios.at.carcassonne.networking;

public interface INetworkController {

    void createConnection(boolean isGroupOwner);
    void sendDate(Object data, int type);
    Object receiveData(int type);

    boolean isGroupOwner();

}