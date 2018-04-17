package distudios.at.carcassonne.networking.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * Handling sockets and connections
 * Sending and receiving data.
 */
public class ConnectionManager {



    /**
     * Map of all client channels
     */
    private Map<String, SocketChannel> clientChannels = new HashMap<>();

    // global selectors and properties
    private Selector clientSelector = null;
    private Selector serverSelector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private SocketChannel clientSocketChannel = null;
    String serverAddress = null;
    String clientAddress = null;

    public void configIPV4() {
        java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
    }

    /**
     * Create a non-blocking server socket channel.
     * Socket listens to given port for incoming connections.
     * @param port
     * @return
     */
    public static ServerSocketChannel createServerSocketChannel(int port) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        ServerSocket socket = channel.socket();
        socket.bind(new InetSocketAddress(port));
        return channel;
    }

    /**
     * Create a non-blocking channel.
     * Socket connects to given host-address and port.
     * @param hostName
     * @param port
     * @return
     * @throws IOException
     */
    public static SocketChannel createSocketChannel(String hostName, int port) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        channel.connect(new InetSocketAddress(hostName, port));
        return channel;
    }

    /**
     * Create a non-blocking channel.
     * Socket connects to given host-address and port.
     * It is guaranteed that the channel is connected.
     * @param hostName
     * @param port
     * @return
     * @throws IOException
     */
    public SocketChannel connectTo(String hostName, int port) throws IOException {
        SocketChannel channel = createSocketChannel(hostName, port);

        // wait until channel has finished connecting and is usable.
        while(!channel.finishConnect());

        return channel;
    }

    /**
     * Called by client.
     * Connects to the group owner.
     * @param host
     * @return
     */
    public int startClientSelector(String host) {
        // closeServer();
        return -1;
    }

    /**
     * Called by group owner.
     * @return
     */
    public int startServerSelector() {
        closeClient();



        return -1;
    }

    /**
     * Close the connection to the server
     */
    public void closeServer() {
        if (serverSocketChannel != null) {
            try {
                serverSocketChannel.close();
                serverSelector.close();
            } catch(Exception e) {

            } finally {
                serverSocketChannel = null;
                serverSelector = null;
                serverAddress = null;
                clientChannels.clear();
            }
        }
    }

    /**
     * Close the connection to clients
     */
    public void closeClient() {
        if (clientSocketChannel != null) {
            try {
                clientSocketChannel.close();
                clientSelector.close();
            } catch(Exception e) {

            } finally {
                clientSocketChannel = null;
                clientSelector = null;
                clientAddress = null;
            }
        }
    }
}
