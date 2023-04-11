package com.cyberpeace.common.util;

import java.io.*;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    /**
     * 初始化压缩包信息并开始进行压缩
     *
     * @param inputFile  需要压缩的文件或文件夹
     * @param outputFile 压缩后的文件
     */
    public static void zip(File inputFile, File outputFile) {
        ZipOutputStream zos = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outputFile);
            zos = new ZipOutputStream(out);
            // 设置压缩包注释
//            zos.setComment("From Log");
            zipFile(zos, inputFile, null);
            System.err.println("压缩完成!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("压缩失败!");
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null){
                try {
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 如果是单个文件，那么就直接进行压缩。如果是文件夹，那么递归压缩所有文件夹里的文件
     *
     * @param zos       压缩输出流
     * @param inputFile 需要压缩的文件
     * @param path      需要压缩的文件在压缩包里的路径
     */
    public static void zipFile(ZipOutputStream zos, File inputFile, String path) {
        if (inputFile.isDirectory()) {
            // 记录压缩包中文件的全路径
            String p = null;
            File[] fileList = inputFile.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                File file = fileList[i];
                // 如果路径为空，说明是根目录
                if (path == null || path.isEmpty()) {
                    p = file.getName();
                } else {
                    p = path + "/" + file.getName();
                    // 打印路径
                }
                // 如果是目录递归调用，直到遇到文件为止
                zipFile(zos, file, p);
            }
        } else {
            zipSingleFile(zos, inputFile, path);
        }
    }

    /**
     * 压缩单个文件到指定压缩流里
     *
     * @param zos       压缩输出流
     * @param inputFile 需要压缩的文件
     * @param path      需要压缩的文件在压缩包里的路径
     * @throws FileNotFoundException
     */
    public static void zipSingleFile(ZipOutputStream zos, File inputFile, String path) {
        try {
            InputStream in = new FileInputStream(inputFile);
            zos.putNextEntry(new ZipEntry(path));
            write(in, zos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * zip解压
     *
     * @param srcFile     zip源文件
     * @param destDirPath 解压后的目标文件夹
     * @throws RuntimeException 解压失败会抛出运行时异常
     */
    public static HashSet<String> unZip(File srcFile, String destDirPath) throws RuntimeException {
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        HashSet<String> fileList = new HashSet<String>();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        //生成XML的参数
        try {
            zipFile = new ZipFile(srcFile);
            FileInputStream fis = new FileInputStream(srcFile);
            ZipInputStream zin = new ZipInputStream(fis);
            ZipEntry entry = null;
            while ((entry = zin.getNextEntry()) != null) {
                String fName1 = entry.getName();
                fName1 = fName1.replace("\\", "/");
                String fName = fName1.substring(fName1.lastIndexOf("/") + 1);
                if (fName != null && fName.trim().length() > 0) {
                    fileList.add(fName);
                }
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + File.separator + fName1;
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + File.separator + fName1);
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            fis.close();
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }

    /**
     * 从输入流写入到输出流的方便方法 【注意】这个函数只会关闭输入流，且读写完成后会调用输出流的flush()函数，但不会关闭输出流！
     *
     * @param input
     * @param output
     */
    private static void write(InputStream input, OutputStream output) {
        int len = -1;
        byte[] buff = new byte[1024];
        try {
            while ((len = input.read(buff)) != -1) {
                output.write(buff, 0, len);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        File srcFile = new File("E:\\download\\test_after.zip");
        unZip(srcFile,srcFile.getParent()+"\\"+srcFile.getName().split("\\.")[0]);
    }
}
