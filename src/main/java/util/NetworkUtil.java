package util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetworkUtil {  
	  
    static private final char COLON = ':';  
  
    /** 
     * 获取当前机器ip地址 
     * 据说多网卡的时候会有问题. 
     */  
    public static String getNetworkAddress() {  
        Enumeration<NetworkInterface> netInterfaces;  
        try {  
            netInterfaces = NetworkInterface.getNetworkInterfaces();  
            InetAddress ip;  
            while (netInterfaces.hasMoreElements()) {  
                NetworkInterface ni = netInterfaces  
                        .nextElement();  
                Enumeration<InetAddress> addresses=ni.getInetAddresses();  
                while(addresses.hasMoreElements()){  
                    ip = addresses.nextElement();  
                    System.out.println("--" +ip);
                    if (!ip.isLoopbackAddress()  
                            && ip.getHostAddress().indexOf(COLON) == -1) {  
                        return ip.getHostAddress();  
                    }  
                }  
            }  
            return "";  
        } catch (Exception e) {  
            return "";  
        }  
    }  
    public static void main(String[] args) {
	System.out.println(getNetworkAddress());	
	}
}  