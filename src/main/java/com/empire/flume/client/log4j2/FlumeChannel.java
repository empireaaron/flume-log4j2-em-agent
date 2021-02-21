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
    private String localCacheDir;
    private int localCacheCapacity = 10000000;
    private int localCacheMinStorageSpace = 524288000;
    private String localCacheMaxFileSize = "134217728";

    private int localCacheDataDirNum = 4;

    private long localCacheCheckpointInterval = 30000L;
    private boolean localCacheBackupCheckpoint = true;
    private boolean localCacheCheckpointOnClose = false;
    private int localCacheTransactionCapacity = 6000;
    private int localCacheRequestTimeout = 10;

    public FlumeChannel() {}

    public FlumeChannel(String localCacheDir) {
        this.localCacheDir = localCacheDir;
    }

    @PluginFactory
    public static FlumeChannel createFlumeChannel(@PluginAttribute("local_cache_dir") String localCacheDir,
        @PluginAttribute(value = "local_cache_capacity", defaultInt = 10000000) int localCacheCapacity,
        @PluginAttribute(value = "local_cache_min_storage_space",
            defaultInt = 524288000) int localCacheMinStorageSpace,
        @PluginAttribute(value = "local_cache_max_file_size",
            defaultString = "134217728") String localCacheMaxFileSize,
        @PluginAttribute(value = "local_cache_data_dir_num", defaultInt = 4) int localCacheDataDirNum,
        @PluginAttribute(value = "local_cache_checkpoint_interval",
            defaultLong = 30000L) long localCacheCheckpointInterval,
        @PluginAttribute(value = "local_cache_backup_checkpoint",
            defaultBoolean = true) boolean localCacheBackupCheckpoint,
        @PluginAttribute(value = "local_cache_checkpoint_on_close",
            defaultBoolean = false) boolean localCacheCheckpointOnClose,
        @PluginAttribute(value = "local_cache_transaction_capacity",
            defaultInt = 6000) int localCacheTransactionCapacity,
        @PluginAttribute(value = "local_cache_request_timeout", defaultInt = 10) int localCacheRequestTimeout) {
        FlumeChannel channel = new FlumeChannel(localCacheDir);
        channel.localCacheCapacity = localCacheCapacity;
        channel.localCacheMinStorageSpace = localCacheMinStorageSpace;
        channel.localCacheMaxFileSize = localCacheMaxFileSize;
        channel.localCacheDataDirNum = localCacheDataDirNum;
        channel.localCacheCheckpointInterval = localCacheCheckpointInterval;
        channel.localCacheBackupCheckpoint = localCacheBackupCheckpoint;
        channel.localCacheCheckpointOnClose = localCacheCheckpointOnClose;
        channel.localCacheTransactionCapacity = localCacheTransactionCapacity;
        channel.localCacheRequestTimeout = localCacheRequestTimeout;
        return channel;
    }

    @Override
    public String toString() {
        return "FlumeChannel{" +
                "localCacheDir='" + localCacheDir + '\'' +
                ", localCacheCapacity=" + localCacheCapacity +
                ", localCacheMinStorageSpace=" + localCacheMinStorageSpace +
                ", localCacheMaxFileSize='" + localCacheMaxFileSize + '\'' +
                ", localCacheDataDirNum=" + localCacheDataDirNum +
                ", localCacheCheckpointInterval=" + localCacheCheckpointInterval +
                ", localCacheBackupCheckpoint=" + localCacheBackupCheckpoint +
                ", localCacheCheckpointOnClose=" + localCacheCheckpointOnClose +
                ", localCacheTransactionCapacity=" + localCacheTransactionCapacity +
                ", localCacheRequestTimeout=" + localCacheRequestTimeout +
                '}';
    }



    public String getLocalCacheDir() {
        return localCacheDir;
    }

    public void setLocalCacheDir(String localCacheDir) {
        this.localCacheDir = localCacheDir;
    }

    public int getLocalCacheCapacity() {
        return localCacheCapacity;
    }

    public void setLocalCacheCapacity(int localCacheCapacity) {
        this.localCacheCapacity = localCacheCapacity;
    }

    public int getLocalCacheMinStorageSpace() {
        return localCacheMinStorageSpace;
    }

    public void setLocalCacheMinStorageSpace(int localCacheMinStorageSpace) {
        this.localCacheMinStorageSpace = localCacheMinStorageSpace;
    }

    public String getLocalCacheMaxFileSize() {
        return localCacheMaxFileSize;
    }

    public void setLocalCacheMaxFileSize(String localCacheMaxFileSize) {
        this.localCacheMaxFileSize = localCacheMaxFileSize;
    }

    public int getLocalCacheDataDirNum() {
        return localCacheDataDirNum;
    }

    public void setLocalCacheDataDirNum(int localCacheDataDirNum) {
        this.localCacheDataDirNum = localCacheDataDirNum;
    }

    public long getLocalCacheCheckpointInterval() {
        return localCacheCheckpointInterval;
    }

    public void setLocalCacheCheckpointInterval(long localCacheCheckpointInterval) {
        this.localCacheCheckpointInterval = localCacheCheckpointInterval;
    }

    public boolean isLocalCacheBackupCheckpoint() {
        return localCacheBackupCheckpoint;
    }

    public void setLocalCacheBackupCheckpoint(boolean localCacheBackupCheckpoint) {
        this.localCacheBackupCheckpoint = localCacheBackupCheckpoint;
    }

    public boolean isLocalCacheCheckpointOnClose() {
        return localCacheCheckpointOnClose;
    }

    public void setLocalCacheCheckpointOnClose(boolean localCacheCheckpointOnClose) {
        this.localCacheCheckpointOnClose = localCacheCheckpointOnClose;
    }

    public int getLocalCacheTransactionCapacity() {
        return localCacheTransactionCapacity;
    }

    public void setLocalCacheTransactionCapacity(int localCacheTransactionCapacity) {
        this.localCacheTransactionCapacity = localCacheTransactionCapacity;
    }

    public int getLocalCacheRequestTimeout() {
        return localCacheRequestTimeout;
    }

    public void setLocalCacheRequestTimeout(int localCacheRequestTimeout) {
        this.localCacheRequestTimeout = localCacheRequestTimeout;
    }
}
