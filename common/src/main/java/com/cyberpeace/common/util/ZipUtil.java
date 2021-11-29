package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.util.UUID;

@Slf4j
public class ZipUtil {

	public static String generatePath() {
		String tmpPath;
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			tmpPath = "/C:/test/";
		} else {
			tmpPath = "/tmp/";
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

	public static Boolean unzipToDest(String zipPath, String destPath) {

		try {
			ZipFile zipFile = new ZipFile(zipPath);

			FileUtil.mkdirs(destPath);
			zipFile.extractAll(destPath);

			return CommonConstant.TRUE;
		} catch (Exception e) {
			log.error(
					"ParseMarkdown unzip markdown package error! markdown_path=[{}], dest_path=[{}] catch error:",
					zipPath, destPath, e);
			return CommonConstant.FALSE;
		}
	}
}
