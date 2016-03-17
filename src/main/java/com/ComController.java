package com;

import com.requests.AddPlayerRequest;
import com.requests.CheckQueueRequest;
import com.requests.CreateQueueRequest;
import com.responses.CheckQueueResponse;
import com.responses.CreateQueueResponse;
import com.responses.SuccessResponse;
import matchmaker.match.Match;
import matchmaker.queue.QueueHandler;
import matchmaker.queue.QueueStatus;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

/**
 * Created by Robi on 2016.02.29..
 *
 * ComController handles incoming requests, and responds to them.
 * 
 */

@RestController
@EnableAutoConfiguration
public class ComController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody Client index() {
        return new Client();
    }

    @RequestMapping(value = "/queue/create", method = RequestMethod.POST)
    public @ResponseBody CreateQueueResponse createQueue(@RequestBody CreateQueueRequest req) {
        long queueId = QueueHandler.getHandler().createQueue(req.getClientId(), req.getConfig());
        return new CreateQueueResponse(queueId);
    }

    @RequestMapping(value = "/queue/addPlayer", method = RequestMethod.POST)
    public @ResponseBody SuccessResponse addPlayerQueue(@RequestBody AddPlayerRequest req) {
        QueueHandler.getHandler().getQueue(req.queueId).addPlayer(req.player);
        return new SuccessResponse();
    }

    @RequestMapping(value = "/queue/check", method = RequestMethod.POST)
    public @ResponseBody CheckQueueResponse checkQueue(@RequestBody CheckQueueRequest req) {
        LinkedList<Match> matches = QueueHandler.getHandler().checkQueue(req.queueId);
        QueueStatus status = QueueHandler.getHandler().getQueue(req.queueId).getStatus();
        System.out.println( "Check Request - " + matches);
        return new CheckQueueResponse(matches, status );
    }

    @RequestMapping(value = "/queue/setConfig", method = RequestMethod.POST)
    public @ResponseBody SuccessResponse setConfigQueue(@RequestBody CreateQueueRequest req){
        return new SuccessResponse();
    }

    @RequestMapping(value = "/queue/removePlayer", method = RequestMethod.POST)
    public @ResponseBody SuccessResponse removePlayerQueue(@RequestBody CreateQueueRequest req){
        return new SuccessResponse();
    }

}
