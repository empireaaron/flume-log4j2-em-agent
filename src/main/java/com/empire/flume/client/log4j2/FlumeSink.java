package com.empire.flume.client.log4j2;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.status.StatusLogger;

/**
 * @author aaron.xu
 * @date 2021/2/19
 */
@Plugin(name = "sink", category = "Core", printObject = true)
public class FlumeSink {
    private String servers;
    private String serversLoadBalance = "round_robin";
    private int serversMaxIoWorkers = 4;
    private int serversMaxBackoff = 6000;
    private int serversConnectTimeout = 8000;
    private int serversRequestTimeout = 8000;
    private int serversTransactionCapacity = 300;

    public FlumeSink() {}

    public FlumeSink(String servers) {
        this.servers = servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public String getServers() {
        return servers;
    }

    @PluginFactory
    public static FlumeSink createFlumeChannel(@PluginAttribute("servers") String servers,
        @PluginAttribute(value = "servers_load_balance", defaultString = "round_robin") String serversLoadBalance,
        @PluginAttribute(value = "servers_max_io_workers", defaultInt = 4) int serversMaxIoWorkers,
        @PluginAttribute(value = "servers_max_backoff", defaultInt = 6000) int serversMaxBackoff,
        @PluginAttribute(value = "servers_connect_timeout", defaultInt = 8000) int serversConnectTimeout,
        @PluginAttribute(value = "servers_request_timeout", defaultInt = 8000) int serversRequestTimeout,
        @PluginAttribute(value = "servers_transaction_capacity", defaultInt = 300) int serversTransactionCapacity) {
        FlumeSink sink = new FlumeSink(servers);
        sink.serversLoadBalance = serversLoadBalance;
        sink.serversMaxIoWorkers = serversMaxIoWorkers;
        sink.serversMaxBackoff = serversMaxBackoff;
        sink.serversConnectTimeout = serversConnectTimeout;
        sink.serversRequestTimeout = serversRequestTimeout;
        sink.serversTransactionCapacity = serversTransactionCapacity;
        return new FlumeSink();
    }

    @Override
    public String toString() {
        return "FlumeSink{" +
                "servers='" + servers + '\'' +
                ", serversLoadBalance='" + serversLoadBalance + '\'' +
                ", serversMaxIoWorkers=" + serversMaxIoWorkers +
                ", serversMaxBackoff=" + serversMaxBackoff +
                ", serversConnectTimeout=" + serversConnectTimeout +
                ", serversRequestTimeout=" + serversRequestTimeout +
                ", serversTransactionCapacity=" + serversTransactionCapacity +
                '}';
    }



    public String getServersLoadBalance() {
        return serversLoadBalance;
    }

    public void setServersLoadBalance(String serversLoadBalance) {
        this.serversLoadBalance = serversLoadBalance;
    }

    public int getServersMaxIoWorkers() {
        return serversMaxIoWorkers;
    }

    public void setServersMaxIoWorkers(int serversMaxIoWorkers) {
        this.serversMaxIoWorkers = serversMaxIoWorkers;
    }

    public int getServersMaxBackoff() {
        return serversMaxBackoff;
    }

    public void setServersMaxBackoff(int serversMaxBackoff) {
        this.serversMaxBackoff = serversMaxBackoff;
    }

    public int getServersConnectTimeout() {
        return serversConnectTimeout;
    }

    public void setServersConnectTimeout(int serversConnectTimeout) {
        this.serversConnectTimeout = serversConnectTimeout;
    }

    public int getServersRequestTimeout() {
        return serversRequestTimeout;
    }

    public void setServersRequestTimeout(int serversRequestTimeout) {
        this.serversRequestTimeout = serversRequestTimeout;
    }

    public int getServersTransactionCapacity() {
        return serversTransactionCapacity;
    }

    public void setServersTransactionCapacity(int serversTransactionCapacity) {
        this.serversTransactionCapacity = serversTransactionCapacity;
    }
}
