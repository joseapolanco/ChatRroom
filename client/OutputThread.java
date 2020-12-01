/*
 * Thread class that handles getting the message from the user and sends it to server.
 *
 * @author Jose Polanco
 * Advanced Java COMPSCI 221-02
 */
package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class OutputThread extends Thread
{
    private Scanner keyboard;
    private String message;
    private PrintWriter output;
    private Socket server;
    private Scanner input;
    
    public OutputThread(Scanner keyboard, PrintWriter output, Socket server, Scanner input)
    {
        this.keyboard = keyboard;
        this.output = output;
        this.server = server;
        this.input = input;
        message = null;
    }
    
    /**Overrides the Thread's run method. Listens for a message from the user and handles it.
     *
     */
    @Override
    public void run()
    {
        while(true)
        {
        message = keyboard.nextLine(); //Get users input
        output.println(message); //sends message to server
        if(message.equals("/quit")) //If user inputs command to quit, breaks loop and closes all connections
            break;
        }
        input.close();
        output.close();
        try {
            server.close();
        } catch (IOException ex) {}
    }
}
