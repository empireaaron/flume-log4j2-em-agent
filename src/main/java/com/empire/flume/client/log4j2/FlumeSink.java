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
    private static final Logger logger = StatusLogger.getLogger();
    private String servers;
    private String servers_load_balance = "round_robin";
    private int servers_max_io_workers = 4;
    private int servers_max_backoff = 6000;
    private int servers_connect_timeout = 8000;
    private int servers_request_timeout = 8000;
    private int servers_transaction_capacity = 300;

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
        @PluginAttribute(value = "servers_load_balance", defaultString = "round_robin") String servers_load_balance,
        @PluginAttribute(value = "servers_max_io_workers", defaultInt = 4) int servers_max_io_workers,
        @PluginAttribute(value = "servers_max_backoff", defaultInt = 6000) int servers_max_backoff,
        @PluginAttribute(value = "servers_connect_timeout", defaultInt = 8000) int servers_connect_timeout,
        @PluginAttribute(value = "servers_request_timeout", defaultInt = 8000) int servers_request_timeout,
        @PluginAttribute(value = "servers_transaction_capacity", defaultInt = 300) int servers_transaction_capacity) {
        FlumeSink sink = new FlumeSink(servers);
        sink.servers_load_balance = servers_load_balance;
        sink.servers_max_io_workers = servers_max_io_workers;
        sink.servers_max_backoff = servers_max_backoff;
        sink.servers_connect_timeout = servers_connect_timeout;
        sink.servers_request_timeout = servers_request_timeout;
        sink.servers_transaction_capacity = servers_transaction_capacity;
        return new FlumeSink();
    }

    @Override
    public String toString() {
        return "FlumeSink{" + "servers='" + servers + '\'' + ", servers_load_balance='" + servers_load_balance + '\''
            + ", servers_max_io_workers=" + servers_max_io_workers + ", servers_max_backoff=" + servers_max_backoff
            + ", servers_connect_timeout=" + servers_connect_timeout + ", servers_request_timeout="
            + servers_request_timeout + ", servers_transaction_capacity=" + servers_transaction_capacity + '}';
    }

    public String getServers_load_balance() {
        return servers_load_balance;
    }

    public void setServers_load_balance(String servers_load_balance) {
        this.servers_load_balance = servers_load_balance;
    }

    public int getServers_max_io_workers() {
        return servers_max_io_workers;
    }

    public void setServers_max_io_workers(int servers_max_io_workers) {
        this.servers_max_io_workers = servers_max_io_workers;
    }

    public int getServers_max_backoff() {
        return servers_max_backoff;
    }

    public void setServers_max_backoff(int servers_max_backoff) {
        this.servers_max_backoff = servers_max_backoff;
    }

    public int getServers_connect_timeout() {
        return servers_connect_timeout;
    }

    public void setServers_connect_timeout(int servers_connect_timeout) {
        this.servers_connect_timeout = servers_connect_timeout;
    }

    public int getServers_request_timeout() {
        return servers_request_timeout;
    }

    public void setServers_request_timeout(int servers_request_timeout) {
        this.servers_request_timeout = servers_request_timeout;
    }

    public int getServers_transaction_capacity() {
        return servers_transaction_capacity;
    }

    public void setServers_transaction_capacity(int servers_transaction_capacity) {
        this.servers_transaction_capacity = servers_transaction_capacity;
    }
}
