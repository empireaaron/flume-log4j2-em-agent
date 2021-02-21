package com.empire.flume.client.log4j2;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.flume.agent.embedded.EmbeddedAgent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * kafka appender将日志收集到kafka
 * 
 * @author aaron.xu
 */
@Plugin(name = "Log4j2EmAgentAppender", category = "Core", elementType = "appender", printObject = true)
public class Log4j2EmAgentAppender extends AbstractAppender {
    public static final Logger logger;
    private static boolean logConfig = false;
    public String sourceIp;
    public String appName;
    private boolean writeLogLocation = true;
    private boolean debug = false;
    private FlumeAgentConfig flumeAgentConfig;
    private EmbeddedAgent agent = null;
    private LogEventWrapperProducerWithTranslator producer;
    static {
        logger = LOGGER;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setProducer(LogEventWrapperProducerWithTranslator producer) {
        this.producer = producer;
    }

    public void setFlumeAgentConfig(FlumeAgentConfig flumeAgentConfig) {
        this.flumeAgentConfig = flumeAgentConfig;
    }

    public void setWriteLogLocation(boolean writeLogLocation) {
        this.writeLogLocation = writeLogLocation;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    protected Log4j2EmAgentAppender(String name, Filter filter, Layout<? extends Serializable> layout,
        boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @Override
    public void start() {
        Class clazz = Log4j2EmAgentAppender.class;
        synchronized (clazz) {
            Log4j2AgentConfiguration log4j2AgentConfiguration = new Log4j2AgentConfiguration();
            Map<String, String> conf = log4j2AgentConfiguration.configure(this.getName(), this.flumeAgentConfig);
            String agentName = (String)conf.get("agent.name");
            conf.remove("agent.name");
            conf.remove("lcd");
            this.agent = new EmbeddedAgent(agentName);
            this.agent.configure(conf);
            this.agent.start();
            logger.info(agentName + ' ' + this.agent + " start.");

            // RingBuffer 大小，必须是 2 的 N 次方；
            int ringBufferSize = 1024 * 1024;
            EventFactory<LogEventWrapper> eventFactory = new LogEventWrapperFactory();
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("Disruptor-ThreadPool").build();
            final Disruptor<LogEventWrapper> disruptor = new Disruptor<>(eventFactory, ringBufferSize,
                namedThreadFactory, ProducerType.SINGLE, new YieldingWaitStrategy());
            EventHandler<LogEventWrapper> eventHandler =
                new LogEventHandler(this.agent, this.appName, this.sourceIp, this.debug);
            disruptor.setDefaultExceptionHandler(new EventExceptionHandler());
            disruptor.handleEventsWith(eventHandler);
            disruptor.start();
            logger.info("flume-log4j2-em-client KafkaAppender disruptor Started.");
            RingBuffer<LogEventWrapper> ringBuffer = disruptor.getRingBuffer();
            LogEventWrapperProducerWithTranslator producer =
                new LogEventWrapperProducerWithTranslator(ringBuffer, writeLogLocation);
            this.setProducer(producer);
            // 应用关闭前关闭disrupt
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    disruptor.shutdown();
                    logger.info("flume-log4j2-em-client KafkaAppender disruptor.shutdown");
                }
            }));

            logger.info("日志系统:flume-log4j2-em-client KafkaAppender Started!!!");
        }
        super.start();
    }

    @Override
    public void append(LogEvent logEvent) {
        producer.onData(logEvent);
    }

    @PluginFactory
    public static Log4j2EmAgentAppender createLog4j2EmAgentAppender(@PluginAttribute("name") String name,
        @PluginAttribute("appName") String appName, @PluginAttribute("servers") String servers,
        @PluginAttribute("sourceIp") String sourceIp, @PluginAttribute("local_cache_dir") String local_cache_dir,
        @PluginAttribute(value = "debug", defaultBoolean = false) boolean debug,
        @PluginAttribute(value = "agentNum", defaultInt = 1) int agentNum,
        @PluginAttribute(value = "writeLogLocation", defaultBoolean = true) boolean writeLogLocation,
        @PluginElement("flumeAgentConfig") FlumeAgentConfig flumeAgentConfig,
        @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
        @PluginElement("properties") Property[] properties,
        @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filter") final Filter filter) {

        // flume config init
        if (flumeAgentConfig == null) {
            flumeAgentConfig = new FlumeAgentConfig();
        }
        if (StringUtils.isNotBlank(servers)) {
            flumeAgentConfig.getSink().setServers(servers);
        }
        if (StringUtils.isNotBlank(local_cache_dir)) {
            flumeAgentConfig.getChannel().setLocal_cache_dir(local_cache_dir);
        }
        // flume config check
        if (!flumeAgentConfig.checkConf()) {
            logger.error("flumeAgentConfig配置必填参数缺失:channel[local_cache_dir],Sink[servers]");
        }

        logger.warn(flumeAgentConfig);

        if (sourceIp == null) {
            sourceIp = IpUtil.getIp();
        }
        // Appender init
        Log4j2EmAgentAppender appender = new Log4j2EmAgentAppender(name, filter, layout, ignoreExceptions, properties);
        appender.setAppName(appName);
        appender.setSourceIp(sourceIp);
        appender.setFlumeAgentConfig(flumeAgentConfig);
        appender.setWriteLogLocation(writeLogLocation);
        appender.setDebug(debug);
        StringBuffer sb = new StringBuffer();
        sb.append("\n\n").append("name=").append(name).append('\n').append("appName=").append(appName).append('\n')
            .append("servers=").append(servers).append('\n').append("sourceIp=").append(sourceIp).append('\n')
            .append("local_cache_dir=").append(local_cache_dir).append('\n').append("debug=").append(debug).append('\n')
            .append("writeLogLocation=").append(writeLogLocation).append('\n').append(flumeAgentConfig).append('\n');

        if (!logConfig) {
            logger.warn(sb.toString());
            logConfig = true;
        }

        return appender;
    }
}
