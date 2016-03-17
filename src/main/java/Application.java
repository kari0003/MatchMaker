import com.ComController;
import matchmaker.queue.QueueEvents;
import matchmaker.queue.QueueHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * Created by Robi on 2016.03.10..
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args){
        System.out.println("Hello Dude!");
        QueueHandler.initialize();
        ApplicationContext ctx = SpringApplication.run(ComController.class, args);

        Thread t = new Thread(new QueueEvents());
        t.start();


    }
}
