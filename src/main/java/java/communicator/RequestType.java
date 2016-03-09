package java.communicator;

/**
 * Created by Robi on 2016.02.29..
 */
public enum RequestType {
    CREATE_QUEUE,   //Creates a new queue, with an unique id.
    REFRESH_QUEUE,  //Updates a queue based on a configuration object
    CHECK_QUEUE,    //Check the state of the queue. If a match was found, it responds accordingly
    ADD_PLAYER,     //Adds a player to a queue or queues
    REMOVE_PLAYER,  //Removes player from a queue or queues

}
