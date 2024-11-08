package com.cyberpeace.common.model;

import net.sf.sevenzipjbinding.*;

import java.io.*;

/**
 * 提取回调
 *
 * @author SN
 * @date 2024/11/08
 */
public class ExtractCallback implements IArchiveExtractCallback {


    /**
     * 指数
     */
    private int index;
    /**
     * 存档
     */
    private IInArchive inArchive;
    /**
     * 我们导演
     */
    private String ourDir;

    /**
     * 提取回调
     *
     * @param inArchive 存档
     * @param ourDir    我们导演
     */
    public ExtractCallback(IInArchive inArchive, String ourDir) {
        this.inArchive = inArchive;
        this.ourDir = ourDir;
    }


    /**
     * 设置完成
     *
     * @param arg0 参数0
     * @throws SevenZipException SEVEN ZIP 例外
     */
    @Override
    public void setCompleted(long arg0) throws SevenZipException {
    }

    /**
     * 设置总
     *
     * @param arg0 参数0
     * @throws SevenZipException SEVEN ZIP 例外
     */
    @Override
    public void setTotal(long arg0) throws SevenZipException {
    }

    /**
     * 获取流
     *
     * @param index          指数
     * @param extractAskMode 提取 Ask 模式
     * @return {@code ISequentialOutStream }
     * @throws SevenZipException SEVEN ZIP 例外
     */
    @Override
    public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) throws SevenZipException {
        this.index = index;
        final String path = (String) inArchive.getProperty(index, PropID.PATH);
        final boolean isFolder = (boolean) inArchive.getProperty(index, PropID.IS_FOLDER);

        File file1 = new File(ourDir + File.separator+ path);
        File parentFile;
        if (!file1.exists() && isFolder) {
            if (!(parentFile = file1.getParentFile()).exists()) {
                parentFile.mkdirs();
            }
            if (!file1.isFile()) {
                file1.mkdir();
            }
        }
        return data -> {
            try {
                if (!isFolder) {
                    File file = new File(ourDir + path);
                    save2File(file1, data);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return data.length;
        };
    }

    /**
     * 准备实操
     *
     * @param arg0 参数0
     * @throws SevenZipException SEVEN ZIP 例外
     */
    @Override
    public void prepareOperation(ExtractAskMode arg0) throws SevenZipException {
    }

    /**
     * 设置实操结果
     *
     * @param extractOperationResult 提取实操结果
     * @throws SevenZipException SEVEN ZIP 例外
     */
    @Override
    public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {

    }

    /**
     * save2 文件
     *
     * @param file 文件
     * @param msg  味精
     * @return boolean
     * @throws FileNotFoundException 文件未找到异常
     */
    public static boolean save2File(File file, byte[] msg) throws FileNotFoundException {
        OutputStream fos = null;
        try {
            File parent = file.getParentFile();
            if ((!parent.exists()) && (!parent.mkdirs())) {
                return false;
            }
            fos = new FileOutputStream(file, true);
            fos.write(msg);
            fos.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
