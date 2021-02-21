package com.empire.flume.client.log4j2;

import java.util.HashMap;

import org.apache.flume.agent.embedded.EmbeddedAgent;
import org.apache.flume.event.SimpleEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;

/**
 * @author aaron.xu
 * @date 2021/2/21
 */
public class LogEventHandler implements EventHandler<LogEventWrapper> {
    private static final Logger logger = LoggerFactory.getLogger(LogEventHandler.class);
    private EmbeddedAgent agent = null;
    private String appName;
    private String sourceIp;
    private boolean debug;

    public LogEventHandler() {}

    public LogEventHandler(EmbeddedAgent agent, String appName, String sourceIp, boolean debug) {
        this.agent = agent;
        this.appName = appName;
        this.sourceIp = sourceIp;
        this.debug = debug;
    }

    @Override
    public void onEvent(LogEventWrapper logEventWrapper, long l, boolean b) throws Exception {
        SimpleEvent simpleEvent = new SimpleEvent();
        simpleEvent.setHeaders(new HashMap(8));
        simpleEvent.setBody((byte[])null);
        logEventWrapper.buildFlumeEvent(simpleEvent, appName, sourceIp);
        if (debug) {
            System.out.println("SimpleEvent日志:" + simpleEvent + "，body:" + new String(simpleEvent.getBody()));
        }
        agent.put(simpleEvent);
        if (debug) {
            System.out.println("SimpleEvent日志:执行完毕");
        }
    }
}
