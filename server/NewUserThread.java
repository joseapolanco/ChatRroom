/*
 * A threaded class that checks for when users connect or leave the server
 *
 * @author Jose Polanco
 * Advanced Java COMPSCI 221-02
 */
package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;


public class NewUserThread extends Thread
{
    private int usersConnected;
    private Map<String, Socket> userList;
    private PrintWriter output;
    
    public NewUserThread(int usersConnected, Map userList, PrintWriter output) 
    {
        this.usersConnected = usersConnected;
        this.userList = userList;
        this.output = output;
    }
    
    @Override
    public void run() 
    {
        while(true)
        {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {}
            if(usersConnected < userList.size())
            {
                output.println("New user connected");
                output.println("Users connected: " + userList.keySet());
                usersConnected = userList.size();
            }
            if(userList.size() < usersConnected)
            {
                output.println("User has left chat");
                output.println("Users connected: " + userList.keySet());
                --usersConnected;
            }
        }
    }
}
