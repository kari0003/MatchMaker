import com.ClientHandler;
import com.CommunicationController;
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
        ClientHandler.initialize();
        QueueHandler.initialize();
        ApplicationContext ctx = SpringApplication.run(CommunicationController.class, args);

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

    }
}
