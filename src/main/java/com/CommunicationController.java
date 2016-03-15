package com;

import com.requests.AddPlayerRequest;
import com.requests.CheckQueueRequest;
import com.requests.CreateQueueRequest;
import com.responses.CheckQueueResponse;
import com.responses.CreateQueueResponse;
import matchmaker.match.Match;
import matchmaker.queue.QueueHandler;
import matchmaker.queue.QueueStatus;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

/**
 * Created by Robi on 2016.02.29..
 *
 * CommunicationController handles incoming requests, and responds to them.
 * 
 */

@RestController
@EnableAutoConfiguration
public class CommunicationController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody Client index() {
        return new Client();
    }

    @RequestMapping(value = "/queue/create", method = RequestMethod.POST)
    public @ResponseBody CreateQueueResponse CreateQueue(@RequestBody CreateQueueRequest req) {
        long queueId = QueueHandler.getHandler().createQueue(req.getClientId(), req.getConfig());
        return new CreateQueueResponse(queueId);
    }


    @RequestMapping(value = "/queue/addPlayer", method = RequestMethod.POST)
    public @ResponseBody String CreateQueue(@RequestBody AddPlayerRequest req) {
        QueueHandler.getHandler().getQueue(req.queueId).addPlayer(req.player);
        return "Done.";
    }

    @RequestMapping(value = "/queue/check", method = RequestMethod.POST)
    public @ResponseBody CheckQueueResponse CheckQueue(@RequestBody CheckQueueRequest req) {
        LinkedList<Match> matches = QueueHandler.getHandler().checkQueue(req.queueId);
        QueueStatus status = QueueHandler.getHandler().getQueue(req.queueId).getStatus();
        return new CheckQueueResponse(matches, status );
    }

}
