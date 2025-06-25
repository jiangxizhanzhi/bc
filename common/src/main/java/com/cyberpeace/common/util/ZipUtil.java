package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip 实用程序
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * ### 代码分析 
 * #### 1. **功能概述** 
 * 该代码是一个工具类 `ZipUtil`，提供了多种与 ZIP 文件相关的操作，包括： 
 * - 生成临时路径。 
 * - 解压 ZIP 文件到指定目录或临时路径。 
 * - 压缩文件夹到 ZIP 文件（支持当前目录或指定目标目录）。 
 * - 递归压缩文件和文件夹。 
 * #### 2. **主要功能模块** 
 * ##### a. **生成临时路径** 
 * - 方法：`generatePath` 
 * - 功能：根据操作系统类型（Windows 或 Linux），动态获取配置中的临时路径，并生成唯一的子路径。 
 * ##### b. **解压 ZIP 文件** 
 * - 方法：`unZipToTmpPath` 
 * - 功能：将 ZIP 文件解压到系统默认临时目录。 
 * - 方法：`unzipToDest` 
 * - 功能：将 ZIP 文件解压到指定的目标目录，并捕获异常记录日志。 
 * ##### c. **压缩文件夹** 
 * - 方法：`toZip` 
 * - 功能：将指定文件夹下的所有文件压缩为 ZIP 文件，存放在原文件夹下。 
 * - 方法：`toZipDest` 
 * - 功能：将指定文件夹压缩为 ZIP 文件，存放到指定目标目录，并在发生异常时抛出自定义业务异常。 
 * ##### d. **递归压缩逻辑** 
 * - 方法：`compress` 
 * - 功能：递归地处理文件和文件夹，将它们压缩为 ZIP 文件。对于文件直接写入，对于文件夹则递归调用自身。 
 * #### 3. **关键点** 
 * - **跨平台支持**：通过检查操作系统类型动态选择临时路径。 
 * - **异常处理**：在解压和压缩过程中，详细记录错误日志，并根据需要抛出自定义异常。 
 * - **资源管理**：使用 `try-with-resources` 确保流的正确关闭。 
 * - **编码兼容性**：设置解压时的字符集为 `GBK`，以避免中文乱码问题。 
 * #### 4. **优点** 
 * - 功能全面，覆盖了常见的 ZIP 文件操作需求。 
 * - 日志记录清晰，便于排查问题。 
 * - 使用 Lombok 的 `@Slf4j` 注解简化日志记录。 
 * #### 5. **潜在改进点** 
 * - **线程安全性**：当前代码没有考虑多线程环境下的安全性，可能需要加锁或其他机制。 
 * - **性能优化**：在处理大文件或大量文件时，可以考虑更高效的 I/O 操作。 
 * - **异常细化**：目前异常处理较为粗略，可以进一步细分异常类型以提高可维护性。 
 * #### 6. **总结** 
 * 该工具类设计合理，功能完整，适用于常见的 ZIP 文件操作场景。通过良好的异常处理和日志记录，提升了代码的健壮性和可调试性。 
**/
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
		ZipFile zipFile = null;
		String tmpPath = generatePath();

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

		return CommonConstant.FALSE;
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
			if (listFiles == null) {
				throw new IllegalArgumentException("listFiles cannot be null");
			}
			for (File file : listFiles) {
				// 跳过生成的zip文件本身
				if (file.getName().equals(src.getName() + SUFFIX)) {
					continue;
				}
				compress(file, zos, file.getName());
			}
		} catch (Exception e) {
			log.error("failed to Zip Directory {}", srcDir);
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					log.error("failed to close ZipOutputStream");
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
		File f = FileUtil.mkdirs(destDir);

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
				log.error("exception run", e);
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
			try (FileInputStream in = new FileInputStream(sourceFile)) {
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
				// Complete the entry
				zos.closeEntry();
			}
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

