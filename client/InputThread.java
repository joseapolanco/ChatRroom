/*
 * Thread class that listens to the server for any messages.
 *
 * @author Jose Polanco
 * Advanced Java COMPSCI 221-02
 */
package client;

import java.util.Scanner;

public class InputThread extends Thread
{
    Scanner input;
    
    public InputThread(Scanner input)
    {
        this.input = input;
    }
    
    /**Override the Thread's run method. Listens for input from the server and prints the message
     * to the user.
     * 
     */
    @Override
    public void run()
    {
        while(input.hasNextLine())
        {
            System.out.println(input.nextLine());
        }
        System.out.println("You have left the server, hope to see you again.");
    }
}
