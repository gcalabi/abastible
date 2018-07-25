package telesar.abastible.socket;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer  implements Runnable{

    private Socket socketServer;


    public SocketServer() {

    }

    @Override
    public void run() {
        try {
            System.out.println("Listening for connection on port 8010 ....");
            final ServerSocket server = new ServerSocket(8010);
            // spin forever

            Socket connectionSocket = null;

            while (true) {
                Socket clientSocket = null;
                System.out.println("Esperando mensaje");
                clientSocket = server.accept();
                Thread request = new Thread(new threadRequest(clientSocket));
                request.start();
                System.out.println("Sali de el Thread");
            } // end of while
        } catch (Exception v) {
            System.out.println(v);
        }
    }
}
