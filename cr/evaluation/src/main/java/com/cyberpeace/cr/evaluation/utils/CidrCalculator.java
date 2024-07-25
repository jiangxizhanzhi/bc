package com.cyberpeace.cr.evaluation.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CidrCalculator {

    public static void main(String[] args) {
        String routerIP = "192.168.249.119";
        String subnetMask = "255.255.255.240"; // 示例子网掩码

        getCidr(routerIP, subnetMask);
    }

    public static String getCidr(String routerIP, String subnetMask) {
        try {
            // 将IP地址和子网掩码转换为InetAddress对象
            InetAddress routerAddr = InetAddress.getByName(routerIP);
            InetAddress subnetMaskAddr = InetAddress.getByName(subnetMask);

            // 获取IP地址和子网掩码的字节数组
            byte[] routerIPBytes = routerAddr.getAddress();
            byte[] subnetMaskBytes = subnetMaskAddr.getAddress();

            // 将字节数组转换为32位无符号整数
            int routerIPInt = byteArrayToInt(routerIPBytes);
            int subnetMaskInt = byteArrayToInt(subnetMaskBytes);

            // 计算网络地址
            int networkAddressInt = routerIPInt & subnetMaskInt;

            // 将网络地址转换为IP地址格式的字符串
            String networkAddress = intToIPAddress(networkAddressInt);
            int prefixLength = calculateCIDRPrefixLength(subnetMaskBytes);
            String cidr = networkAddress + "/" + prefixLength;
            System.out.println("Network Address: " + networkAddress);
            System.out.println("cidr: " + cidr);
            return cidr;

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 辅助方法：将字节数组转换为32位无符号整数
    private static int byteArrayToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
               ((bytes[1] & 0xFF) << 16) |
               ((bytes[2] & 0xFF) << 8) |
               (bytes[3] & 0xFF);
    }

    // 辅助方法：将32位无符号整数转换为IP地址格式的字符串
    private static String intToIPAddress(int ipAddress) {
        return ((ipAddress >> 24) & 0xFF) + "." +
               ((ipAddress >> 16) & 0xFF) + "." +
               ((ipAddress >> 8) & 0xFF) + "." +
               (ipAddress & 0xFF);
    }

    private static int calculateCIDRPrefixLength(byte[] subnetMaskBytes) {
        int prefixLength = 0;
        boolean zeroEncountered = false;

        // Calculate CIDR prefix length from subnet mask bytes
        for (byte b : subnetMaskBytes) {
            int octet = b & 0xFF; // convert byte to unsigned int
            if (zeroEncountered) {
                if (octet != 0) {
                    throw new IllegalArgumentException("Invalid subnet mask.");
                }
            } else {
                if (octet == 0) {
                    zeroEncountered = true;
                } else {
                    // Count number of bits set to 1 in each octet
                    int bitCount = Integer.bitCount(octet);
                    prefixLength += bitCount;
                }
            }
        }

        return prefixLength;
    }
}
