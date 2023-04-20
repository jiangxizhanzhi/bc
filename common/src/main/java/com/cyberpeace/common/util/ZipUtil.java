package com.cyberpeace.common.util;

import java.io.*;
import java.util.HashSet;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.cyberpeace.common.constant.CommonConstant;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

//@Slf4j
public class ZipUtil {

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

	public static String unZipToTmpPath(String zipPath) throws ZipException {
		ZipFile zipFile = new ZipFile(zipPath);
		String tmpPath = generatePath();
		zipFile.extractAll(tmpPath);
		return tmpPath;
	}

	/**
	 * 压缩文件解压
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
			//logerror("unzipToDest: Unzip failed! zip_path=[{}], dest_path=[{}] catch error:",
//					zipPath, destPath, e);
			return CommonConstant.FALSE;
		}
	}

	private static final int BUFFER_SIZE = 2 * 1024;

	private static final String SUFFIX = ".zip";

	/**
	 * 将某文件夹下所有文件压缩，生成zip包到该文件夹下
	 * 如 /tmp/xyz/* ---> /tmp/xyz/xyz.zip
	 * @param srcDir 被压缩的文件夹路径
	 * @throws RuntimeException
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
			//logerror("failed to Zip Directory {}", srcDir);
			e.printStackTrace();
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					//logerror("failed to close ZipOutputStream");
					e.printStackTrace();
				}
			}
		}
		return zipPath;
	}

	/**
	 * 压缩文件到目的文件夹 文件名称为src文件夹名称.zip
	 * @param srcDir 需要压缩的文件夹
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
			//logerror("toZipDest: Zip file failed! src=[{}], dest=[{}]; catch error:", srcDir,
//					destDir, e);
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
	 * @param sourceFile 源文件
	 * @param zos		 zip输出流
	 * @param name		 压缩后的名称
	 * @throws Exception
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
		java.util.zip.ZipFile zipFile = null;
		//生成XML的参数
		try {
			zipFile = new java.util.zip.ZipFile(srcFile);
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

}
