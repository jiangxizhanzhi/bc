package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip 实用程序
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
public class ZipUtil {

	/**
	 * 生成路径
	 *
	 * @return {@code String }
	 */
	public static String generatePath() {
		String tmpPath;
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			tmpPath = ConfigUtil.getContextProperty("win.tmp.path");
		} else {
			tmpPath = ConfigUtil.getContextProperty("linux.tmp.path");
		}
		File file = FileUtil.mkdirs(tmpPath + UUID.randomUUID());
		return file.getPath();
	}

	/**
	 * un zip 到 tmp 路径
	 *
	 * @param zipPath Zip 路径
	 * @return {@code String }
	 * @throws ZipException zip 异常
	 */
	public static String unZipToTmpPath(String zipPath) throws ZipException {
		ZipFile zipFile = new ZipFile(zipPath);
		String tmpPath = generatePath();
		zipFile.extractAll(tmpPath);
		return tmpPath;
	}

	/**
	 * 压缩文件解压
	 *
	 * @param zipPath
	 * @param destPath
	 * @return
	 */
	public static Boolean unzipToDest(String zipPath, String destPath) {

		try {
			ZipFile zipFile = new ZipFile(zipPath);
			zipFile.setFileNameCharset("GBK");
			FileUtil.mkdirs(destPath);
			zipFile.extractAll(destPath);

			return CommonConstant.TRUE;
		} catch (Exception e) {
			log.error("unzipToDest: Unzip failed! zip_path=[{}], dest_path=[{}] catch error:",
					zipPath, destPath, e);
			return CommonConstant.FALSE;
		}
	}

	/**
	 * 缓冲区大小
	 */
	private static final int BUFFER_SIZE = 2 * 1024;

	/**
	 * 后缀
	 */
	private static final String SUFFIX = ".zip";

	/**
	 * 将某文件夹下所有文件压缩，生成zip包到该文件夹下
	 * 如 /tmp/xyz/* ---> /tmp/xyz/xyz.zip
	 *
	 * @param srcDir 被压缩的文件夹路径
	 * @return {@code String }
	 * @throws RuntimeException 运行时异常
	 */
	public static String toZip(String srcDir) throws RuntimeException {
		ZipOutputStream zos = null;
		File src = new File(srcDir);
		String zipPath = srcDir + File.separator + src.getName() + SUFFIX;
		try {
			zos = new ZipOutputStream(new FileOutputStream(zipPath));
			File[] listFiles = new File(srcDir).listFiles();
			assert listFiles != null;
			for (File file : listFiles) {
				// 跳过生成的zip文件本身
				if (file.getName().equals(src.getName() + SUFFIX)) {
					continue;
				}
				compress(file, zos, file.getName());
			}
		} catch (Exception e) {
			log.error("failed to Zip Directory {}", srcDir);
			e.printStackTrace();
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					log.error("failed to close ZipOutputStream");
					e.printStackTrace();
				}
			}
		}
		return zipPath;
	}

	/**
	 * 压缩文件到目的文件夹 文件名称为src文件夹名称.zip
	 *
	 * @param srcDir  需要压缩的文件夹
	 * @param destDir 目标文件夹
	 * @return 压缩后文件地址
	 */
	public static String toZipDest(String srcDir, String destDir) {

		// 创建文件夹
		FileUtil.mkdirs(destDir);

		ZipOutputStream zos = null;
		File src = new File(srcDir);
		String zipPath = destDir + File.separator + src.getName() + SUFFIX;
		try {
			zos = new ZipOutputStream(new FileOutputStream(zipPath));
			File[] listFiles = src.listFiles();

			for (File file : listFiles) {
				compress(file, zos, file.getName());
			}
		} catch (Exception e) {
			log.error("toZipDest: Zip file failed! src=[{}], dest=[{}]; catch error:", srcDir,
					destDir, e);
			throw new BusinessException(ErrorCode.FILE_ZIP_FAIL);
		} finally {
			try {
				zos.close();
			} catch (IOException e) {
				// io流关闭失败 不做任何操作
			}
		}

		return zipPath;
	}

	/**
	 * 递归压缩方法
	 *
	 * @param sourceFile 源文件
	 * @param zos        zip输出流
	 * @param name       压缩后的名称
	 * @throws Exception 例外
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name)
			throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		// 压缩文件
		if (sourceFile.isFile()) {
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1) {
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			// 压缩文件夹
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				// 空文件夹的处理
				zos.putNextEntry(new ZipEntry(name + File.separator));
				// 没有文件，不需要文件的copy
				zos.closeEntry();
			} else {
				for (File file : listFiles) {
					// 递归写入
					compress(file, zos, name + File.separator + file.getName());
				}
			}
		}
	}
}
