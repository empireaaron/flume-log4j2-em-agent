package com.empire.flume.client.log4j2;

import com.lmax.disruptor.EventFactory;

/**
 * @author aaron.xu
 * @date 2021/2/21
 */
public class LogEventWrapperFactory implements EventFactory<LogEventWrapper> {
    @Override
    public LogEventWrapper newInstance() {
        return new LogEventWrapper();
    }
}
