
package com.jr.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPUtil {

    public static String LOCAL_IP = "888.888.888.888";

    static {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            Boolean getLocalIp = false;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                if (!getLocalIp){
                    while (addresses.hasMoreElements()) {
                        InetAddress ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            LOCAL_IP = ip.getHostAddress();
                            getLocalIp = true;
                            break;
                        }
                    }
                }else {
                    break;
                }
            }
        } catch (SocketException var4) {

        }
    }
}