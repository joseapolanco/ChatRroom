/*
 * A threaded user class so user's can talk simultaneously on the server.
 *
 * @author Jose Polanco
 * Advanced Java COMPSCI 221-02
 */
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class UserThread extends Thread
{
    private Socket user;
    private PrintWriter userOutput; //send data to the user
    private Scanner userInput; //recieve input from the user
    private String username;
    private Map<String, Socket> userList;
    
    public UserThread(Socket userSocket, Map userList) throws IOException
    {
        user = userSocket;
        userOutput = new PrintWriter(user.getOutputStream(), true);
        userInput = new Scanner(user.getInputStream());
        username = null;
        this.userList = userList;
    }
    
    /**Method that handles the message sent from the user to the server
     * 
     * @param message: Message the user wants to send to another user
     * @param user: Socket of the user sending the message
     */
    private void handleMessage(String message, Socket user) 
    {
        String rUsername;    //recipient's username
        String rMessage;     //recipient's message
        String sUsername = null;    //Sender's username
        PrintWriter rOutput;
        
        //Tokenize the message of the user to get recipient and message
        rUsername = message.substring(1, message.indexOf(" "));
        rMessage = message.substring(message.indexOf(" ") + 1);
        
        //Get the Sender's username
        for(Map.Entry<String, Socket> entry : userList.entrySet())
        {
            if(entry.getValue().equals(user))
                sUsername = entry.getKey();    
        }
        //Print in the server who the message is to, what the message is and who sent the message
        System.out.println("\nMessage sent to: " + rUsername);
        System.out.println("The message is: " + rMessage);
        System.out.println("Message from: " + sUsername);
        
        //Creates an ouput stream to the user and sends the message.
        //If user is not in the chat room the sender is notified
        try {
            if(userList.get(rUsername) != null)
            {
            rOutput = new PrintWriter(userList.get(rUsername).getOutputStream(), true); //OutputStream for recipient
            rOutput.println(sUsername + ": " + rMessage);
            }
            else
                userOutput.println("User is not in the chat room");
        } catch (IOException ex) {};
    }
    
    /**Overrides the Thread's run method. 
     * Handles the user when they first connect and listens for and handles messages sent
     * from user to user.
     */
    @Override
    public void run()
    {
        //Instructions for when user first connects to server
        userOutput.println("Enter a username: ");
        username = userInput.nextLine();
        userList.put(username, user);
        userOutput.println("Users connected: " + userList.keySet());
        userOutput.println("Type /<recipient’s user name> <message> to send message");
        userOutput.println("Type /quit to leave chatroom chat\n");
        
        
        String message = "";
        int numUsers  = userList.size();
        //Creates and starts thread to listen for when a new user enter or leaves the server
        NewUserThread newUser = new NewUserThread(numUsers, userList, userOutput);
        newUser.start();
        
        //Listens for the user's message and handles it.
        while(userInput.hasNextLine())
        {
                message = userInput.nextLine();
                if(message.equals("/quit")) //If user wants to leave, breaks listening loop and closes all streams and connection
                    break;
                handleMessage(message, user); //sends the message to handleMessage to send to appropriate user
        }
        userOutput.close();
        userInput.close();
        try {
            //Closes users connection to server and removes them from chat room list
            user.close();
            System.out.println("\n" + username + " has disconnected");
            userList.remove(username);
        } catch (IOException ex) {}
    }
}
