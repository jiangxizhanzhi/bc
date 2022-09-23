package com.cyberpeace.common.util;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TarGzUtils {

	private static final int BUFFER_SIZE = 1024 * 100;

	public static void main(String[] args) throws Exception {

		// 压缩文件
		// CompressUtil.compress("/a/tmp/tmp/test","/a/tmp/tmp/tar/","test");

		// 解压文件
		TarGzUtils.decompress("/a/tmp/tmp/tar/test.tar.gz", "/a/tmp/tmp/tar");

	}

	private TarGzUtils() {}

	public static List<File> getFiles(String path) {
		List<File> list = Lists.newArrayList();

		File file = new File(path);
		File[] tempList = file.listFiles();

		if (null == tempList || tempList.length <= 0) {
			return list;
		}

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				list.add(new File(tempList[i].getPath()));
			}

			if (tempList[i].isDirectory()) {
				List tmpList = getFiles(tempList[i].getPath());
				if (null != tmpList && tmpList.size() > 0) {
					list.addAll(tmpList);
				}
			}
		}
		return list;
	}

	/**
	 * 将文件集合压缩成tar包后返回
	 *
	 * @param files  要压缩的文件集合
	 * @param target tar 输出流的目标文件
	 * @return File  指定返回的目标文件
	 * @throws Exception
	 */
	public static File pack(List<File> files, String inPutPath, File target) throws Exception {

		try (FileOutputStream out = new FileOutputStream(target);
				BufferedOutputStream bos = new BufferedOutputStream(out, BUFFER_SIZE);
				TarArchiveOutputStream os = new TarArchiveOutputStream(bos)) {

			// 解决文件名过长问题
			os.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
			for (File file : files) {
				// 去掉文件前面的目录
				os.putArchiveEntry(
						new TarArchiveEntry(file, file.getAbsolutePath().replace(inPutPath, "")));
				try (FileInputStream fis = new FileInputStream(file)) {
					IOUtils.copy(fis, os);
					os.closeArchiveEntry();
				}
			}
		}

		return target;
	}

	/**
	 * 文件压缩
	 * @param source 要压缩的文件夹
	 * @param target 压缩文件生成目录
	 * @param fileName 文件名称
	 * @throws Exception
	 */
	public static void compress(String source, String target, String fileName, boolean retainDir)
			throws Exception {

		List<File> list = getFiles(source);
		if (list.size() <= 0) {
			return;
		}
		File file = new File(target);
		if (!file.exists()) {
			file.mkdirs();
		}

		if (retainDir) {
			String parent = new File(source).getParent();
			source = StringUtils.isBlank(parent) ? source : parent;
		}

		compressTar(list, source, target, fileName);

	}

	/**
	 * 压缩tar文件
	 *
	 * @param list
	 * @param outPutPath
	 * @param fileName
	 */
	public static File compressTar(List<File> list, String inPutPath, String outPutPath,
			String fileName) throws Exception {
		File outPutFile = new File(outPutPath + File.separator + fileName);
		File tempTar = new File(outPutPath + File.separator + IDGeneratorUtil.getID() + ".tar");

		try (FileInputStream fis = new FileInputStream(pack(list, inPutPath, tempTar))) {
			try (BufferedInputStream bis = new BufferedInputStream(fis, BUFFER_SIZE)) {
				try (FileOutputStream fos = new FileOutputStream(outPutFile)) {
					try (GZIPOutputStream gzp = new GZIPOutputStream(fos)) {
						int count;
						byte[] data = new byte[BUFFER_SIZE];
						while ((count = bis.read(data, 0, BUFFER_SIZE)) != -1) {
							gzp.write(data, 0, count);
						}
					}
				}
			}
		}

		Files.deleteIfExists(tempTar.toPath());
		return outPutFile;
	}

	/**
	 * 解压文件
	 * @param filePath 压缩包
	 * @param outputDir 解压目录
	 */
	public static void decompress(String filePath, String outputDir) {
		File file = new File(filePath);
		if (!file.exists()) {
			log.error("File decompress failed, file not found! file_path=[{}]", filePath);
			throw new BusinessException(ErrorCode.FILE_NOT_EXIST);
		}

		try {
			if (!filePath.endsWith(FileSunffixConstant.TAR.getDesc())
					&& !filePath.endsWith(FileSunffixConstant.TGZ.getDesc())) {
				throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
			}

			decompressTarGz(file, outputDir);
			filterFile(new File(outputDir));

		} catch (Exception e) {
			log.error("File decompress failed! file_path=[{}], output_dir=[{}], catch error:",
					filePath, outputDir, e);
			throw new BusinessException(ErrorCode.FILE_DECOMPRESS_FAILED);
		}

	}

	/**
	 * 解压tar.gz文件
	 * @param file
	 * @param outputDir
	 * @throws IOException
	 */
	public static void decompressTarGz(File file, String outputDir) throws IOException {

		try (FileInputStream fileInput = new FileInputStream(file);
				BufferedInputStream buffer = new BufferedInputStream(fileInput);
				GzipCompressorInputStream compressor = new GzipCompressorInputStream(buffer);
				TarArchiveInputStream tarIn = new TarArchiveInputStream(compressor)) {

			// 创建输出目录
			createDirectory(outputDir, null);

			TarArchiveEntry entry;
			while ((entry = tarIn.getNextTarEntry()) != null) {

				// 是目录
				if (entry.isDirectory()) {
					// 创建空目录
					createDirectory(outputDir, entry.getName());
				} else {
					// 是文件
					String outFile = outputDir + File.separator + entry.getName();
					FileUtil.createParentPath(outFile);
					try (OutputStream out = new FileOutputStream(outFile)) {
						writeFile(tarIn, out);
					}
				}
			}
		}

	}

	/**
	 * 解压缩tar.bz2文件
	 *
	 * @param file      压缩包文件
	 * @param outputDir 目标文件夹
	 */
	public static void decompressTarBz2(File file, String outputDir) throws IOException {
		try (FileInputStream inputStream = new FileInputStream(file);
				BZip2CompressorInputStream bzip = new BZip2CompressorInputStream(inputStream);
				TarArchiveInputStream tarIn = new TarArchiveInputStream(bzip)) {

			createDirectory(outputDir, null);

			TarArchiveEntry entry = null;
			while ((entry = tarIn.getNextTarEntry()) != null) {
				if (entry.isDirectory()) {
					createDirectory(outputDir, entry.getName());
				} else {
					try (OutputStream out = new FileOutputStream(
							outputDir + File.separator + entry.getName())) {
						writeFile(tarIn, out);
					}
				}
			}
		}
	}

	/**
	 * 写文件
	 *
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void writeFile(InputStream in, OutputStream out) throws IOException {
		int length;
		byte[] b = new byte[BUFFER_SIZE];
		while ((length = in.read(b)) != -1) {
			out.write(b, 0, length);
		}
	}

	/**
	 * 创建目录
	 *
	 * @param outputDir
	 * @param subDir
	 */
	public static void createDirectory(String outputDir, String subDir) {
		File file = new File(outputDir);
		// 子目录不为空
		if (!(subDir == null || subDir.trim().equals(""))) {
			file = new File(outputDir + File.separator + subDir);
		}

		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.mkdirs();
		}
	}

	/**
	 * 删除Mac压缩再解压产生的 __MACOSX 文件夹和 .开头的其他文件
	 *
	 * @param filteredFile
	 */
	public static void filterFile(File filteredFile) {
		if (filteredFile != null) {
			File[] files = filteredFile.listFiles();
			for (File file : files) {
				if (file.getName().startsWith(".")
						|| (file.isDirectory() && file.getName().equals("__MACOSX"))) {
					FileUtils.deleteQuietly(file);
				}
			}
		}
	}

}
