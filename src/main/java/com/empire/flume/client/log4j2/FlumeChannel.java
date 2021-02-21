package com.empire.flume.client.log4j2;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.status.StatusLogger;

/**
 * local flume agent channel config
 * 
 * @author aaron.xu
 * @date 2021/2/21
 */
@Plugin(name = "channel", category = "Core", printObject = true)
public class FlumeChannel {
    private static final Logger logger = StatusLogger.getLogger();
    private String local_cache_dir;
    private int local_cache_capacity = 10000000;
    private int local_cache_min_storage_space = 524288000;
    private String local_cache_max_file_size = "134217728";

    private int local_cache_data_dir_num = 4;

    private long local_cache_checkpoint_interval = 30000L;
    private boolean local_cache_backup_checkpoint = true;
    private boolean local_cache_checkpoint_on_close = false;
    private int local_cache_transaction_capacity = 6000;
    private int local_cache_request_timeout = 10;

    public FlumeChannel() {}

    public FlumeChannel(String local_cache_dir) {
        this.local_cache_dir = local_cache_dir;
    }

    @PluginFactory
    public static FlumeChannel createFlumeChannel(@PluginAttribute("local_cache_dir") String local_cache_dir,
        @PluginAttribute(value = "local_cache_capacity", defaultInt = 10000000) int local_cache_capacity,
        @PluginAttribute(value = "local_cache_min_storage_space",
            defaultInt = 524288000) int local_cache_min_storage_space,
        @PluginAttribute(value = "local_cache_max_file_size",
            defaultString = "134217728") String local_cache_max_file_size,
        @PluginAttribute(value = "local_cache_data_dir_num", defaultInt = 4) int local_cache_data_dir_num,
        @PluginAttribute(value = "local_cache_checkpoint_interval",
            defaultLong = 30000L) long local_cache_checkpoint_interval,
        @PluginAttribute(value = "local_cache_backup_checkpoint",
            defaultBoolean = true) boolean local_cache_backup_checkpoint,
        @PluginAttribute(value = "local_cache_checkpoint_on_close",
            defaultBoolean = false) boolean local_cache_checkpoint_on_close,
        @PluginAttribute(value = "local_cache_transaction_capacity",
            defaultInt = 6000) int local_cache_transaction_capacity,
        @PluginAttribute(value = "local_cache_request_timeout", defaultInt = 10) int local_cache_request_timeout) {
        FlumeChannel channel = new FlumeChannel(local_cache_dir);
        channel.local_cache_capacity = local_cache_capacity;
        channel.local_cache_min_storage_space = local_cache_min_storage_space;
        channel.local_cache_max_file_size = local_cache_max_file_size;
        channel.local_cache_data_dir_num = local_cache_data_dir_num;
        channel.local_cache_checkpoint_interval = local_cache_checkpoint_interval;
        channel.local_cache_backup_checkpoint = local_cache_backup_checkpoint;
        channel.local_cache_checkpoint_on_close = local_cache_checkpoint_on_close;
        channel.local_cache_transaction_capacity = local_cache_transaction_capacity;
        channel.local_cache_request_timeout = local_cache_request_timeout;
        return channel;
    }

    @Override
    public String toString() {
        return "FlumeChannel{" + "local_cache_dir='" + local_cache_dir + '\'' + ", local_cache_capacity="
            + local_cache_capacity + ", local_cache_min_storage_space=" + local_cache_min_storage_space
            + ", local_cache_max_file_size='" + local_cache_max_file_size + '\'' + ", local_cache_data_dir_num="
            + local_cache_data_dir_num + ", local_cache_checkpoint_interval=" + local_cache_checkpoint_interval
            + ", local_cache_backup_checkpoint=" + local_cache_backup_checkpoint + ", local_cache_checkpoint_on_close="
            + local_cache_checkpoint_on_close + ", local_cache_transaction_capacity=" + local_cache_transaction_capacity
            + ", local_cache_request_timeout=" + local_cache_request_timeout + '}';
    }

    public void setLocal_cache_dir(String local_cache_dir) {
        this.local_cache_dir = local_cache_dir;
    }

    public String getLocal_cache_dir() {
        return local_cache_dir;
    }

    public int getLocal_cache_capacity() {
        return local_cache_capacity;
    }

    public void setLocal_cache_capacity(int local_cache_capacity) {
        this.local_cache_capacity = local_cache_capacity;
    }

    public int getLocal_cache_min_storage_space() {
        return local_cache_min_storage_space;
    }

    public void setLocal_cache_min_storage_space(int local_cache_min_storage_space) {
        this.local_cache_min_storage_space = local_cache_min_storage_space;
    }

    public String getLocal_cache_max_file_size() {
        return local_cache_max_file_size;
    }

    public void setLocal_cache_max_file_size(String local_cache_max_file_size) {
        this.local_cache_max_file_size = local_cache_max_file_size;
    }

    public int getLocal_cache_data_dir_num() {
        return local_cache_data_dir_num;
    }

    public void setLocal_cache_data_dir_num(int local_cache_data_dir_num) {
        this.local_cache_data_dir_num = local_cache_data_dir_num;
    }

    public long getLocal_cache_checkpoint_interval() {
        return local_cache_checkpoint_interval;
    }

    public void setLocal_cache_checkpoint_interval(long local_cache_checkpoint_interval) {
        this.local_cache_checkpoint_interval = local_cache_checkpoint_interval;
    }

    public boolean isLocal_cache_backup_checkpoint() {
        return local_cache_backup_checkpoint;
    }

    public void setLocal_cache_backup_checkpoint(boolean local_cache_backup_checkpoint) {
        this.local_cache_backup_checkpoint = local_cache_backup_checkpoint;
    }

    public boolean isLocal_cache_checkpoint_on_close() {
        return local_cache_checkpoint_on_close;
    }

    public void setLocal_cache_checkpoint_on_close(boolean local_cache_checkpoint_on_close) {
        this.local_cache_checkpoint_on_close = local_cache_checkpoint_on_close;
    }

    public int getLocal_cache_transaction_capacity() {
        return local_cache_transaction_capacity;
    }

    public void setLocal_cache_transaction_capacity(int local_cache_transaction_capacity) {
        this.local_cache_transaction_capacity = local_cache_transaction_capacity;
    }

    public int getLocal_cache_request_timeout() {
        return local_cache_request_timeout;
    }

    public void setLocal_cache_request_timeout(int local_cache_request_timeout) {
        this.local_cache_request_timeout = local_cache_request_timeout;
    }

}
