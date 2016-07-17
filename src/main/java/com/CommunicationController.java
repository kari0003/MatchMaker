package com;

import com.requests.AddPlayerRequest;
import com.requests.CheckQueueRequest;
import com.requests.CreateQueueRequest;
import com.requests.RemovePlayerRequest;
import com.responses.CheckQueueResponse;
import com.responses.CreateQueueResponse;
import com.responses.GeneralResponse;
import config.ClientConfig;
import matchmaker.match.Match;
import matchmaker.queue.Queue;
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

    @RequestMapping(value="/", method = RequestMethod.GET)
    public @ResponseBody GeneralResponse getClients() {
        return new GeneralResponse(ClientHandler.getClients());
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public GeneralResponse createClient(@RequestBody ClientConfig conf) {
        if (conf != null) {
           return new GeneralResponse(ClientHandler.createClient(conf));
        }
        long clientId = ClientHandler.createClient(new ClientConfig());
        return new GeneralResponse((Long) clientId);
    }

    @RequestMapping(value="/", method = RequestMethod.DELETE)
    public void deleteClient(@RequestHeader(value="authentication") String auth) {
        long clientId = Long.parseLong(auth);
        ClientHandler.deleteClient(clientId);
    }

    @RequestMapping(value="/", method = RequestMethod.PUT)
    public void updateClient(@RequestHeader(value="authentication") String auth,
            @RequestBody ClientConfig conf) {
        long clientId = Long.parseLong(auth);
        Client client = ClientHandler.getClient(clientId);
        if(client != null){
            client.updateConfig(conf);
        }
    }

    @RequestMapping(value="/queue", method = RequestMethod.GET)
    public @ResponseBody GeneralResponse getQueues(@RequestHeader(value="authentication") String auth) {
        long clientId = Long.parseLong(auth);
        return new GeneralResponse(QueueHandler.getHandler().getClientQueues(clientId));
    }

    @RequestMapping(value="/queue", method = RequestMethod.POST)
    public @ResponseBody GeneralResponse createQueue(@RequestHeader(value="authentication") String auth,
                          @RequestBody String queueKey) {
        System.out.println("auth = [" + auth + "], queueKey = [" + queueKey + "]");
        long clientId = Long.parseLong(auth);
        System.out.printf("Queue Key is:" + queueKey);
        queueKey = queueKey.replace("\"", "");
        return new GeneralResponse(QueueHandler.getHandler().createQueue(clientId, queueKey));
    }

    @RequestMapping(value="/queue/{queueId}", method = RequestMethod.GET)
    public @ResponseBody GeneralResponse checkQueue(@PathVariable(value="queueId") String id,
                         @RequestHeader(value="authentication") String auth) {
        long clientId = Long.parseLong(auth);
        long queueId = Long.parseLong(id);
        //TODO Sort out id-s
        LinkedList<Match> matches = QueueHandler.getHandler().checkQueue(queueId);
        QueueStatus status = QueueHandler.getHandler().getQueue(queueId).getStatus();
        return new GeneralResponse(new CheckQueueResponse(matches, status));
    }

    //TODO updateQueue

    @RequestMapping(value="/queue/{queueId}", method = RequestMethod.DELETE)
    public void deleteQueue(@PathVariable(value="queueId") String id,
                            @RequestHeader(value="authentication") String auth) {
        long clientId = Long.parseLong(auth);
        long queueId = Long.parseLong(id);
        QueueHandler.getHandler().deleteQueue(queueId);
    }

    @RequestMapping(value="/queue/{queueId}/players", method = RequestMethod.POST)
    public void addPlayerToQueue(@PathVariable(value="queueId") String id,
                            @RequestHeader(value="authentication") String auth,
                            @RequestBody AddPlayerRequest req) {
        long clientId = Long.parseLong(auth);
        long queueId = Long.parseLong(id);
        QueueHandler.getHandler().getQueue(queueId).addPlayer(req.player);
    }

    @RequestMapping(value="/queue/{queueId}/players", method = RequestMethod.DELETE)
    public void removePlayerFromQueue(@PathVariable(value="queueId") String id,
                                 @RequestHeader(value="authentication") String auth,
                                 @RequestBody RemovePlayerRequest req) {
        System.out.println("id = [" + id + "], auth = [" + auth + "], req = [" + req + "]");
        long clientId = Long.parseLong(auth);
        long queueId = Long.parseLong(id);
        QueueHandler.getHandler().getQueue(queueId).addPlayer(req.player);
    }


    @RequestMapping(value="/queue/{queueId}/start", method = RequestMethod.POST)
    public void startQueue(@PathVariable(value="queueId") String id,
                                      @RequestHeader(value="authentication") String auth) {
        long clientId = Long.parseLong(auth);
        long queueId = Long.parseLong(id);
        QueueHandler.getHandler().startQueue(queueId);
    }
}
