package com.cyberpeace.cr.crbase.utils;

import java.io.FileInputStream;
import java.security.MessageDigest;

public class FileMD5Checksum {

    public static void main(String[] args) {
        String filePath = "D:\\cpms\\other\\ea865a58d95c438280a10aea1c893c5f\\data.json";
        String md5sum = getFileMD5Checksum(filePath);
        System.out.println("MD5 checksum: " + md5sum);
    }

    public static String getFileMD5Checksum(String filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int bytesRead;
            FileInputStream fis = new FileInputStream(filePath);
            
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            
            fis.close();
            
            byte[] md5sumBytes = digest.digest();
            
            StringBuilder sb = new StringBuilder();
            for (byte b : md5sumBytes) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}