package com.cyberpeace.common.util;


import com.cyberpeace.common.model.ExtractCallback;
import lombok.extern.slf4j.Slf4j;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;

import java.io.File;
import java.io.RandomAccessFile;


/**
 * @Author xuxz
 */
@Slf4j
public class RarUtil {
    /**
     * 使用junrar解压,只支持winrar 5以下解压缩
     *
     * @param sourceRar 压缩文件
     * @param outDir    解压结果路径
     * @throws Exception
     */
    public static void unrarToDest(String sourceRar, String outDir) {
        File file = new File(sourceRar);
        RandomAccessFile randomAccessFile = null;
        IInArchive inArchive = null;
        try {
            // 第一个参数是需要解压的压缩包路径，第二个参数参考JdkAPI文档的RandomAccessFile
            randomAccessFile = new RandomAccessFile(file.getPath(), "r");
            inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
            int[] in = new int[inArchive.getNumberOfItems()];
            for (int i = 0; i < in.length; i++) {
                in[i] = i;
            }
            //使用回调函数
            inArchive.extract(in, false, new ExtractCallback(inArchive, outDir));
        } catch (Exception e) {

        }
    }
}
