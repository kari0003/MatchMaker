package java.matchmaker.queue;

/**
 * Created by Robi on 2016.02.26..
 * Describes the current state of a Queue. Active means it is working properly, and is searching for matches.
 * Inactive means something went wrong, or the queue has been stopped on purpose. Match found means there are one or
 * more matches waiting to be returned to the requester.
 */
public enum QueueStatus {
    ACTIVE,
    INACTIVE,
    MATCH_FOUND
}
