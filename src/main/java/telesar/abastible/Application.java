package telesar.abastible;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import telesar.abastible.socket.SocketServer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        startSocketServer();


    }

    private static void startSocketServer() {

        Thread socketServerThreadThread = new Thread(new SocketServer());
        socketServerThreadThread.start();

    }


}
