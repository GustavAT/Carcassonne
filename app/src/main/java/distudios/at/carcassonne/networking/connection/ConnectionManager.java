package distudios.at.carcassonne.networking.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Handling sockets and connections
 * Sending and receiving data.
 */
public class ConnectionManager {


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
}
