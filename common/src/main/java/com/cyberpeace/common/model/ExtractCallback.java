/**
 * 这段代码定义了一个名为 `ExtractCallback` 的类，实现了 `IArchiveExtractCallback` 接口，用于处理从压缩文件中提取文件的回调操作。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **构造函数**： 
 * - 初始化 `inArchive` 和 `ourDir`，分别表示输入的压缩存档和目标解压目录。 
 * 2. **核心方法实现**： 
 * - **`getStream`**：根据指定的索引和提取模式返回一个输出流。该方法负责创建目标文件路径，并在必要时创建目录。如果文件不是文件夹，则将数据写入文件。 
 * - **`save2File`**：将字节数组写入指定的文件中，支持文件夹递归创建。 
 * 3. **其他接口方法**： 
 * - **`setCompleted`** 和 **`setTotal`**：空实现，未使用。 
 * - **`prepareOperation`** 和 **`setOperationResult`**：空实现，未使用。 
 * ### 详细分析： 
 * - **`getStream` 方法**： 
 * - 根据索引获取压缩文件的信息（路径和是否为文件夹）。 
 * - 如果是文件夹，则递归创建必要的父目录；如果是文件，则创建文件并写入数据。 
 * - 使用 lambda 表达式返回一个 `ISequentialOutStream`，用于写入数据到文件。 
 * - **`save2File` 方法**： 
 * - 将字节数组写入文件，支持递归创建父目录。 
 * - 异常处理较为全面，包括文件不存在、IO 错误等。 
 * ### 总结： 
 * 该类主要用于处理压缩文件的提取操作，能够自动创建必要的目录结构并将文件内容正确写入磁盘。代码逻辑清晰，但部分方法（如 `setCompleted` 和 `setTotal`）未被实现，可能需要根据具体需求进一步完善。 
**/
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
