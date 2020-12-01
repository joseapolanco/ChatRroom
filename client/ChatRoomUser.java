/*
 * Front end class for User to connect to chat room. 
 *
 * @author Jose Polanco
 * Advanced Java COMPSCI 221-02
 */
package client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatRoomUser 
{
    public static void main(String[] a) throws Exception
    {
        //connects to appropriate server
        //input and ouput stream to server is created
        //scanner is created to recieve input from user
        Socket server = new Socket("localhost", 1234);
        PrintWriter output = new PrintWriter(server.getOutputStream(), true);
        Scanner input = new Scanner(server.getInputStream());
        Scanner keyboard = new Scanner(System.in);
        
        OutputThread ot = new OutputThread(keyboard, output, server, input);
        InputThread it = new InputThread(input);
        
        //Get instructions from server when user connects
        System.out.println(input.nextLine());
        String s = keyboard.nextLine();
        output.println(s);
        System.out.println(input.nextLine());
        System.out.println(input.nextLine());
        System.out.println(input.nextLine());
        
        
        ot.start(); //Start thread that handles the users message and sends it to the server
        it.start(); //Start thread that listens to the server
                
    }
}
