package com.empire.flume.client.log4j2;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.flume.FlumeException;

/**
 * @author aaron.xu
 * @date 2021/2/21
 */
public abstract class AbstractConfiguration {
    private String name;
    private FlumeAgentConfig flumeAgentConfig;

    public String getName() {
        return name;
    }

    public FlumeAgentConfig getFlumeAgentConfig() {
        return flumeAgentConfig;
    }

    public Map<String, String> configure(String name, FlumeAgentConfig flumeAgentConfig) {
        this.name = name;
        this.flumeAgentConfig = flumeAgentConfig;
        return this.doConfigure();
    }

    protected Map<String, String> doConfigure() {
        Map<String, String> conf = new HashMap<>(8);
        conf.putAll(this.getCollectChannelConf(flumeAgentConfig.getChannel()));
        conf.putAll(this.getCollectSinkConf(flumeAgentConfig.getSink()));
        conf.put("agent.name", this.getName());
        conf.put("lcd", flumeAgentConfig.getChannel().getLocal_cache_dir());
        return conf;
    }

    private Map<String, String> getCollectChannelConf(FlumeChannel channel) {
        Map<String, String> conf = new HashMap<>(8);
        conf.put("channel.type", "file");
        conf.put("channel.capacity", String.valueOf(channel.getLocal_cache_capacity()));
        conf.put("channel.minimumRequiredSpace", String.valueOf(channel.getLocal_cache_min_storage_space()));
        conf.put("channel.maxFileSize", channel.getLocal_cache_max_file_size());
        if (StringUtils.isBlank(channel.getLocal_cache_dir())) {
            throw new FlumeException(this.getName() + " local_cache_dir is empty!");
        }
        if (channel.getLocal_cache_data_dir_num() < 4) {
            throw new FlumeException(this.getName() + " local_cache_data_dir_num < 4 !");
        }
        String[] dataDirArr = new String[channel.getLocal_cache_data_dir_num()];

        for (int i = 0; i < channel.getLocal_cache_data_dir_num(); ++i) {
            dataDirArr[i] = channel.getLocal_cache_dir() + '/' + "data" + i;
        }

        String dataDirs = StringUtils.join(dataDirArr, ',');
        conf.put("channel.dataDirs", dataDirs);
        String checkpointDir = channel.getLocal_cache_dir() + '/' + "checkpoint";
        conf.put("channel.checkpointDir", checkpointDir);
        conf.put("channel.checkpointInterval", String.valueOf(channel.getLocal_cache_checkpoint_interval()));
        if (channel.isLocal_cache_backup_checkpoint()) {
            conf.put("channel.useDualCheckpoints", String.valueOf(true));
            String backupCheckpointDir = channel.getLocal_cache_dir() + '/' + "backupCheckpoint";
            conf.put("channel.backupCheckpointDir", backupCheckpointDir);
        }

        if (!channel.isLocal_cache_checkpoint_on_close()) {
            conf.put("channel.checkpointOnClose", String.valueOf(channel.isLocal_cache_checkpoint_on_close()));
        }

        conf.put("channel.transactionCapacity", String.valueOf(channel.getLocal_cache_transaction_capacity()));
        conf.put("channel.keep-alive", String.valueOf(channel.getLocal_cache_request_timeout()));
        Map<String, String> specificConf = this.collectSpecificChannelConf();
        if (specificConf != null) {
            conf.putAll(specificConf);
        }

        return conf;
    }

    private Map<String, String> getCollectSinkConf(FlumeSink sink) {
        Map<String, String> conf = new HashMap<>(8);
        String[] serverArr = StringUtils.split(sink.getServers(), ',');
        int totalServer = serverArr.length;
        String[] sinkArr = new String[totalServer];

        for (int i = 0; i < totalServer; ++i) {
            String[] ap = StringUtils.split(serverArr[i], ':');
            String a = ap[0];
            String p = ap[1];
            String sinkName = i + "sink" + StringUtils.replaceChars(a, '.', '_');
            sinkArr[i] = sinkName;
            conf.put(sinkName + ".type", "avro");
            conf.put(sinkName + ".hostname", a);
            conf.put(sinkName + ".port", p);
            conf.put(sinkName + ".batch-size", String.valueOf(sink.getServers_transaction_capacity()));
            conf.put(sinkName + ".maxIoWorkers", String.valueOf(sink.getServers_max_io_workers()));
            conf.put(sinkName + ".connect-timeout", String.valueOf(sink.getServers_connect_timeout()));
            conf.put(sinkName + ".request-timeout", String.valueOf(sink.getServers_request_timeout()));
        }

        conf.put("sinks", StringUtils.join(sinkArr, ' '));
        if (totalServer == 1) {
            conf.put("processor.type", "failover");
            conf.put("processor.maxpenalty", String.valueOf(sink.getServers_max_backoff()));
        } else {
            conf.put("processor.type", "load_balance");
            conf.put("processor.selector", sink.getServers_load_balance());
            conf.put("processor.backoff", "true");
            conf.put("processor.selector.maxTimeOut", String.valueOf(sink.getServers_max_backoff()));
        }

        Map<String, String> specificConf = this.collectSpecificSinkConf();
        if (specificConf != null) {
            conf.putAll(specificConf);
        }

        return conf;
    }

    /**
     * specific channel config
     * 
     * @return the specific config for channel
     */
    protected abstract Map<String, String> collectSpecificChannelConf();

    /**
     * specific sink config
     *
     * @return the specific config for sink
     */
    protected abstract Map<String, String> collectSpecificSinkConf();

}
