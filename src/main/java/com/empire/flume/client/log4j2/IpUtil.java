package com.empire.flume.client.log4j2;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author aaron.xu
 * @date 2021/2/21
 */
public class IpUtil {
    private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

    private static final String GET_IP_ERR_MSG = "can't get server ip.";

    private static final String GET_LOCAL_HOST_IP_MSG = "get localHost ip for server env.";

    /**
     * 获取本机IP
     * 
     * @return IP
     */
    public static String getIp() {
        String ip = null;
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface currNetworkInterface = (NetworkInterface)networkInterfaces.nextElement();
                Enumeration addresses = currNetworkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress)addresses.nextElement();
                    if (inetAddress.isSiteLocalAddress()) {
                        ip = inetAddress.getHostAddress();
                        break;
                    }
                }
                if (ip != null) {
                    break;
                }
            }
            if (ip == null) {
                logger.warn(GET_LOCAL_HOST_IP_MSG);
                InetAddress localHost = InetAddress.getLocalHost();
                ip = localHost.getHostAddress();
            }
            return ip;
        } catch (Exception e) {
            logger.warn(GET_IP_ERR_MSG, e);
            throw new RuntimeException(GET_IP_ERR_MSG, e);
        }
    }
}
