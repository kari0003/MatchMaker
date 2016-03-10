package communicator.requests;

import configs.QueueConfig;

/**
 * Created by Robi on 2016.03.10..
 */
public class CreateQueueRequest {
    public final long clientId;
    public final QueueConfig config;

    public CreateQueueRequest(long id, QueueConfig config){
        this.clientId = id;
        this.config = config;
    }

    public CreateQueueRequest(long id, int updateMillis, int teamSize, int teamCount){
        this.clientId = id;
        this.config = new QueueConfig(updateMillis, teamSize,teamCount);
    }

    public CreateQueueRequest(long id){
        this.clientId = id;
        this.config = new QueueConfig(100, 3, 2);
    }
}
