package communicator;

import communicator.requests.CreateQueueRequest;
import communicator.responses.CreateQueueResponse;
import matchmaker.queue.QueueHandler;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Robi on 2016.02.29..
 *
 * CommunicationController handles incoming requests, and responds to them.
 * 
 */

@RestController
public class CommunicationController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody CreateQueueResponse CreateQueue(@RequestBody CreateQueueRequest req) {
        long queueId = QueueHandler.getHandler().createQueue(req.clientId, req.config);
        return new CreateQueueResponse(queueId);
    }

}
