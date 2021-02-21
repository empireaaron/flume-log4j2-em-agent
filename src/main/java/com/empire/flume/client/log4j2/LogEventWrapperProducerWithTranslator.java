package com.empire.flume.client.log4j2;

import org.apache.logging.log4j.core.LogEvent;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * @author aaron.xu
 */
public class LogEventWrapperProducerWithTranslator {
    /**
     * 一个translator可以看做一个事件初始化器，publicEvent方法会调用它，填充Event
     */
    private static final EventTranslatorOneArg<LogEventWrapper, LogEvent> TRANSLATOR =
        new EventTranslatorOneArg<LogEventWrapper, LogEvent>() {
            @Override
            public void translateTo(LogEventWrapper event, long sequence, LogEvent logEvent) {
                event.setLoggerName(logEvent.getLoggerName());
                event.setLevel(logEvent.getLevel());
                event.setTimeMillis(logEvent.getTimeMillis());
                event.setThreadName(logEvent.getThreadName());
                event.setMessage(logEvent.getMessage().getFormattedMessage());
                event.setParameters(logEvent.getMessage().getParameters());
                event.setThrown(event.getThrown());
                if (LogEventWrapperProducerWithTranslator.WRITE_LOG_LOCATION) {
                    StackTraceElement st = logEvent.getSource();
                    event.setMethod(st.getMethodName());
                    event.setLineNumber(st.getLineNumber());
                }
            }
        };

    private final RingBuffer<LogEventWrapper> ringBuffer;

    private static Boolean WRITE_LOG_LOCATION = null;

    public LogEventWrapperProducerWithTranslator(RingBuffer<LogEventWrapper> ringBuffer, Boolean writeLogLocation) {
        this.ringBuffer = ringBuffer;
        WRITE_LOG_LOCATION = writeLogLocation;
    }

    public void onData(LogEvent logEvent) {
        ringBuffer.publishEvent(TRANSLATOR, logEvent);
    }
}