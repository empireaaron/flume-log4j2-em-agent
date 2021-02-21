//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.empire.flume.client.log4j2;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.status.StatusLogger;

/**
 * @author aaron.xu
 */
@Plugin(name = "flumeAgentConfig", category = "Core", printObject = true)
public final class FlumeAgentConfig {
    private static final Logger logger = StatusLogger.getLogger();
    private FlumeChannel channel;
    private FlumeSink sink;

    public FlumeAgentConfig() {
        logger.warn("flume channel & sink config cannot be null, it will initialize by default");
        channel = new FlumeChannel();
        sink = new FlumeSink();
    }

    public FlumeAgentConfig(FlumeChannel channel, FlumeSink sink) {
        this.channel = channel;
        this.sink = sink;
    }

    public FlumeChannel getChannel() {
        return channel;
    }

    public FlumeSink getSink() {
        return sink;
    }

    @PluginFactory
    public static FlumeAgentConfig createFlumeAgentConfig(@PluginElement("channel") FlumeChannel channel,
        @PluginElement("sink") FlumeSink sink, @PluginAttribute("servers") String servers,
        @PluginAttribute("local_cache_dir") String local_cache_dir) {
        if (channel == null) {
            logger.warn("flume channel config cannot be null, it will initialize by default");
            channel = new FlumeChannel();
        }
        if (sink == null) {
            logger.warn("flume sink config cannot be null, it will initialize by default");
            sink = new FlumeSink();
        }
        if (StringUtils.isNotBlank(servers)) {
            sink.setServers(servers);
        }
        if (StringUtils.isNotBlank(local_cache_dir)) {
            channel.setLocal_cache_dir(local_cache_dir);
        }
        return new FlumeAgentConfig(channel, sink);
    }

    @Override
    public String toString() {
        return "FlumeAgentConfig{" + "channel=" + channel + ", sink=" + sink + '}';
    }

    public boolean checkConf() {
        String servers = this.getSink().getServers();
        String channel = this.getChannel().getLocal_cache_dir();;
        return StringUtils.isNotBlank(servers) && StringUtils.isNotBlank(channel);
    }

}
