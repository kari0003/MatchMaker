import communicator.CommunicationController;
import matchmaker.queue.QueueHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Robi on 2016.03.10..
 */

@SpringBootApplication
public class Service {

    public static void main(String[] args){
        System.out.println("Hello Dude!");
        QueueHandler.initialize();
        SpringApplication.run(CommunicationController.class, args);
    }
}
