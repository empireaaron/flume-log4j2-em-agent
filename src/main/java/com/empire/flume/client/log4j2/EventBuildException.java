package com.empire.flume.client.log4j2;

/**
 * @author aaron.xu
 */
public class EventBuildException extends Exception {

    public EventBuildException() {}

    public EventBuildException(String message) {
        super(message);
    }

    public EventBuildException(String message, Throwable t) {
        super(message, t);
    }

    public EventBuildException(Throwable t) {
        super(t);
    }
}
