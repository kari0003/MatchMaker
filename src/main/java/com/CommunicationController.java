package com;

import com.requests.AddPlayerRequest;
import com.requests.CheckQueueRequest;
import com.requests.CreateQueueRequest;
import com.requests.RemovePlayerRequest;
import com.responses.CheckQueueResponse;
import com.responses.CreateQueueResponse;
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
    public @ResponseBody LinkedList<Client> getClients() {
        return ClientHandler.getClients();
    }
/*
    @RequestMapping(value="/", method = RequestMethod.POST)
    public long createClient(@RequestBody ClientConfig conf) {
        long id = -1;
        if(conf != null){
            id = ClientHandler.createClient(conf);
        }else{
            id = ClientHandler.createClient(new ClientConfig());
        }
        return id;
    }*/

    @RequestMapping(value="/", method = RequestMethod.POST)
    public ClientConfig createClient() {
        long clientId = ClientHandler.createClient(new ClientConfig());
        return ClientHandler.getClient(clientId).conf;
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
    public @ResponseBody LinkedList<Queue> getQueues(@RequestHeader(value="authentication") String auth) {
        long clientId = Long.parseLong(auth);
        return QueueHandler.getHandler().getClientQueues(clientId);
    }

    @RequestMapping(value="/queue", method = RequestMethod.POST)
    public @ResponseBody long createQueue(@RequestHeader(value="authentication") String auth,
                          @RequestBody String queueKey) {
        long clientId = Long.parseLong(auth);
        System.out.printf("Queue Key is:" + queueKey);
        queueKey = queueKey.replace("\"", "");
        return QueueHandler.getHandler().createQueue(clientId, queueKey);
    }

    @RequestMapping(value="/queue/{queueId}", method = RequestMethod.GET)
    public @ResponseBody CheckQueueResponse checkQueue(@PathVariable(value="queueId") String id,
                         @RequestHeader(value="authentication") String auth) {
        long clientId = Long.parseLong(auth);
        long queueId = Long.parseLong(id);
        //TODO Sort out id-s
        LinkedList<Match> matches = QueueHandler.getHandler().checkQueue(queueId);
        QueueStatus status = QueueHandler.getHandler().getQueue(queueId).getStatus();
        return new CheckQueueResponse(matches, status);
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


    /*
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody Client index() {
        long clientId = ClientHandler.createClient(new ClientConfig());
        return ClientHandler.getClient(clientId);
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

    @RequestMapping(value = "/queue/{queueId}/check", method = RequestMethod.POST)
    public @ResponseBody CheckQueueResponse CheckQueue(@PathVariable(value="someID") String id,
                                                       @RequestHeader(value="authentication") String clientId,
                                                       @RequestBody CheckQueueRequest req) {

        LinkedList<Match> matches = QueueHandler.getHandler().checkQueue(req.queueId);
        QueueStatus status = QueueHandler.getHandler().getQueue(req.queueId).getStatus();
        return new CheckQueueResponse(matches, status );
    }
*/
}
