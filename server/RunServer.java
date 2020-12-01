/*
 * Front end class for ChatRoomServer to run the server at the given port.
 *
 * @author Jose Polanco
 * Advanced Java COMPSCI 221-02
 */
package server;

public class RunServer 
{
    public static void main(String[] args) throws Exception
    {
        ChatRoomServer server = new ChatRoomServer(1234);
        server.start();
    }
}
