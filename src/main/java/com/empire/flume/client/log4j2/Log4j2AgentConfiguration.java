package com.empire.flume.client.log4j2;

import java.util.Map;

/**
 * @author aaron.xu
 * @date 2021/2/21
 */
public class Log4j2AgentConfiguration extends AbstractConfiguration {

    @Override
    protected Map<String, String> collectSpecificChannelConf() {
        return null;
    }

    @Override
    protected Map<String, String> collectSpecificSinkConf() {
        return null;
    }
}
