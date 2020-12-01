/*
 * Class for the server of the chat room
 *
 * @author Jose Polanco
 * Advanced Java COMPSCI 221-02
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class ChatRoomServer extends Thread
{
    private Map<String, Socket> userList;
    private ServerSocket server;
    
    
    public ChatRoomServer(int port) throws Exception
    {
        //Creates a server socket at given port and Hashmap to store the users in the chatroom server
        server = new ServerSocket(port);
        userList = new HashMap<>();
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            try {
                //Protocol for the server and user to communicate. Listens for a user trying
                //to connect to the server Socket and accepts the connection. A Socket for the user
                //is created so that the server can communicate with the user and a Socket for the server
                //is created on the users end so the user can communicate with the server.
                Socket user = server.accept();
                System.out.println("Connected: " + user.getInetAddress());
                UserThread ut = new UserThread(user, userList); //Creates a UserThread for the newly connected user
                ut.start();
            } catch (IOException ex) {}
            
        }
    }
}
