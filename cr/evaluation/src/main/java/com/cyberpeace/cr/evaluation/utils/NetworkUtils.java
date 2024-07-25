package com.cyberpeace.cr.evaluation.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkUtils {

    // 计算网络地址
    public static String calculateNetworkAddress(String ipAddress, String subnetMask) throws UnknownHostException {
        InetAddress ip = InetAddress.getByName(ipAddress);
        InetAddress mask = InetAddress.getByName(subnetMask);

        byte[] ipBytes = ip.getAddress();
        byte[] maskBytes = mask.getAddress();

        byte[] networkBytes = new byte[ipBytes.length];
        for (int i = 0; i < ipBytes.length; i++) {
            networkBytes[i] = (byte) (ipBytes[i] & maskBytes[i]);
        }

        return InetAddress.getByAddress(networkBytes).getHostAddress();
    }

    public static List<String> calculateBroadcastAddress(String ipAddress, String subnetMask) throws UnknownHostException {

        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        InetAddress subnetInetAddress = InetAddress.getByName(subnetMask);
        byte[] ipAddressBytes = inetAddress.getAddress();
        byte[] subnetMaskBytes = subnetInetAddress.getAddress();

        byte[] broadcastAddressBytes = new byte[ipAddressBytes.length];
        for (int i = 0; i < ipAddressBytes.length; i++) {
            broadcastAddressBytes[i] = (byte) ((ipAddressBytes[i] & subnetMaskBytes[i]) | ~subnetMaskBytes[i]);
        }
        InetAddress broadcastAddress = InetAddress.getByAddress(broadcastAddressBytes);


        // Calculate network address
        byte[] networkAddressBytes = new byte[ipAddressBytes.length];
        for (int i = 0; i < ipAddressBytes.length; i++) {
            networkAddressBytes[i] = (byte) (ipAddressBytes[i] & subnetMaskBytes[i]);
        }

        byte[] startAddressBytes = incrementIPAddress(networkAddressBytes);
        byte[] endAddressBytes = decrementIPAddress(broadcastAddressBytes);

        InetAddress startAddress = InetAddress.getByAddress(startAddressBytes);
        InetAddress endAddress = InetAddress.getByAddress(endAddressBytes);
        List<String> res = new ArrayList<>();
        res.add(startAddress.getHostAddress());
        res.add(endAddress.getHostAddress());
        return res;

    }

    // Helper method to increment IP address
    private static byte[] incrementIPAddress(byte[] address) {
        for (int i = address.length - 1; i >= 0; i--) {
            if (++address[i] != 0) {
                break;
            }
        }
        return address;
    }

    // Helper method to decrement IP address
    private static byte[] decrementIPAddress(byte[] address) {
        for (int i = address.length - 1; i >= 0; i--) {
            if (--address[i] != -1) {
                break;
            }
        }
        return address;
    }

    // 检查是否在同一网段
    public static boolean sameSubnet(String ip1, String ip2, String subnetMask1, String subnetMask2) throws UnknownHostException {
        String network1 = calculateNetworkAddress(ip1, subnetMask1);
        String network2 = calculateNetworkAddress(ip2, subnetMask2);
        return network1.equals(network2);
    }

    public static void main(String[] args) {
        List<String> ip1s = new ArrayList<>(Arrays.asList("192.168.249.102", "192.168.249.118", "192.168.251.117"
        ,"192.168.251.29", "192.168.245.101", "192.168.251.146"));
        List<String> ip2s = new ArrayList<>(Arrays.asList("192.168.249.103", "192.168.249.119", "192.168.251.149"
        ,"192.168.251.2"));
//        String ip1 = "192.168.249.117";
//        String ip2 = "192.168.249.119";
        List<String> subnetMask1s = new ArrayList<>(Arrays.asList("255.255.255.240", "255.255.255.240", "255.255.255.252"
                ,"255.255.255.252", "255.255.255.252", "255.255.255.252"));
        List<String> subnetMask2s = new ArrayList<>(Arrays.asList("255.255.255.240", "255.255.255.240", "255.255.255.252"
                ,"255.255.255.252")); // 例如，/24 子网掩码对应的是 255.255.255.0
//        String subnetMask2 = "255.255.255.240"; // 例如，/24 子网掩码对应的是 255.255.255.0

        try {
            for(String ip1:ip1s){
                String subnetMask1 = subnetMask1s.get(ip1s.indexOf(ip1));
                for(String ip2:ip2s){
                    String subnetMask2 = subnetMask2s.get(ip2s.indexOf(ip2));
                    boolean sameNetwork = sameSubnet(ip1, ip2, subnetMask1, subnetMask2);
                    if (sameNetwork) {
                        System.out.println(ip1 + " 和 " + ip2 + " 在同一网段。");
                    } else {
//                        System.out.println(ip1 + " 和 " + ip2 + " 不在同一网段。");
                    }
                }

            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
