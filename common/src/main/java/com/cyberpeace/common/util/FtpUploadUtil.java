package com.cyberpeace.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FtpUploadUtil {


    public static final String hostname = SystemPropUtil.getProp("FTP_HOST");
    public static final String username = SystemPropUtil.getProp("FTP_USER");
    public static final Integer port = 21;
    public static final String password = SystemPropUtil.getProp("FTP_PASSWORD");

    public static void main(String[] args) {
        FtpUploadUtil.uploadFile("/65463645634/", "hld3.log", "C:\\work\\hld3.log");
    }

    /**
     * 上传文件
     *
     * @param pathname       ftp服务保存地址文件夹路径
     * @param fileName       上传到ftp的文件名
     * @param originfilename 待上传文件的名称（绝对地址） *
     * @return
     */
    @SuppressWarnings("static-access")
    public static void uploadFile(String pathname, String fileName, String originfilename) {


        InputStream inputStream = null;
        FTPClient ftp = new FTPClient();
        ftp.setDefaultTimeout(600 * 1000);
        ftp.setDataTimeout(600 * 1000);
        ftp.setControlEncoding("utf-8");
        try {
            log.info("用户密码" + username +"----------"+ password);

            inputStream = new FileInputStream(new File(originfilename));

            log.info("ftp服务器为" + hostname);
            ftp.connect(hostname, port); //连接ftp服务器
             log.info("path为" + pathname + fileName);

            ftp.login(username, password); //登录ftp服务器
            log.info("文件名为" + originfilename);
            ftp.enterLocalPassiveMode();
            log.info("ftp login start！");

            int replyCode = ftp.getReplyCode(); //是否成功登录服务器
            log.info("返回值为" + replyCode);

            if (!FTPReply.isPositiveCompletion(replyCode)) {
                //链接失败
                log.error("ftp login failed！");
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            CreateDirecroty(ftp, pathname);
            ftp.makeDirectory(pathname);
            ftp.changeWorkingDirectory(pathname);
            ftp.storeFile(fileName, inputStream);
            inputStream.close();
            ftp.logout();
            ftp.disconnect();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
            log.info(e.getMessage());
            log.error(e.toString());

            log.error("ftp error-----------------------",e);
        }
    }


    /**
     * 改变目录路径
     *
     * @param directory
     * @return
     */
    public static boolean changeWorkingDirectory(FTPClient ftp, String directory) {
        boolean flag = true;
        try {
            flag = ftp.changeWorkingDirectory(directory);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    /**
     * 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
     *
     * @param ftp
     * @param remote
     * @return
     * @throws IOException
     */
    public static boolean CreateDirecroty(FTPClient ftp, String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(ftp, new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(ftp, path)) {
                    if (makeDirectory(ftp, subDirectory)) {
                        changeWorkingDirectory(ftp, subDirectory);
                    } else {
                        changeWorkingDirectory(ftp, subDirectory);
                    }
                } else {
                    changeWorkingDirectory(ftp, subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    /**
     * 判断ftp服务器文件是否存在
     *
     * @param ftp
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean existFile(FTPClient ftp, String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftp.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }


    /**
     * 创建目录
     *
     * @param ftp
     * @param dir
     * @return
     */
    public static boolean makeDirectory(FTPClient ftp, String dir) {
        boolean flag = true;
        try {
            flag = ftp.makeDirectory(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


}