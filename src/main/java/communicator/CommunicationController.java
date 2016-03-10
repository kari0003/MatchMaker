package communicator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Robi on 2016.02.29..
 *
 * CommunicationController handles incoming requests, and responds to them.
 * 
 */

@RestController
public class CommunicationController {

    @RequestMapping
    public void onGet( CommObject req){
        //Handles the incoming object, redirect to the proper
    }

    @ResponseBody
    public void send( CommObject res){
        //Sends back the information to the proper client.
    }

}
