package com.cyberpeace.common.model;

import java.io.*;

import net.sf.sevenzipjbinding.*;

/**
 * @Author xuxz
 * @Date 30/1/2023 下午8:03
 */
public class ExtractCallback implements IArchiveExtractCallback {


    private IInArchive inArchive;
    private String ourDir;

    public ExtractCallback(IInArchive inArchive, String ourDir) {
        this.inArchive = inArchive;
        this.ourDir = ourDir;
    }


    @Override
    public void setCompleted(long arg0) throws SevenZipException {
        // do nothing 
    }

    @Override
    public void setTotal(long arg0) throws SevenZipException {
        // do nothing 
    }

    @Override
    public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) throws SevenZipException {
        final String path = (String) inArchive.getProperty(index, PropID.PATH);
        final boolean isFolder = (boolean) inArchive.getProperty(index, PropID.IS_FOLDER);

        File file1 = new File(ourDir + File.separator+ path);
        File parentFile;
        if (!file1.exists() && isFolder) {
            parentFile = file1.getParentFile();
            if (!parentFile.exists()) {
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

    @Override
    public void prepareOperation(ExtractAskMode arg0) throws SevenZipException {
        // do nothing 
    }

    @Override
    public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {
        // do nothing 
    }

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
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
			try {
				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

}
