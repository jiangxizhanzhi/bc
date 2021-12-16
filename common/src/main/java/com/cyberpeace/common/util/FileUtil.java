package com.cyberpeace.common.util;

import java.io.*;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.constant.CommonConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	public static File saveFile(MultipartFile file, String basePath) throws IOException {
		String originalFilename = file.getOriginalFilename();
		assert originalFilename != null;
		String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		String uuid = String.valueOf(UUID.randomUUID());
		String fileName = uuid + fileSuffix;

		mkdirs(basePath);
		File newFile = new File(basePath, fileName);
		file.transferTo(newFile);
		return newFile;
	}

	public static File mkdirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 获取文件名称
	 * @param filePath 文件路径
	 * @param suffix 是否包含后缀
	 * @return
	 */
	public static String getFileName(String filePath, Boolean suffix) {

		File file = new File(filePath);
		return !suffix
				? file.getName().substring(CommonConstant.ZERO, file.getName().lastIndexOf("."))
				: file.getName();
	}

	/**
	 * 读取文件内容
	 * @param file
	 * @return
	 */
	public static String getContent(File file) {
		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

			String content = "";
			while ((content = br.readLine()) != null) {
				builder.append(content);
			}

			return builder.toString();
		} catch (Exception e) {
			log.error("文件读取失败！ filePath={} error:", file.getAbsolutePath(), e);
			throw new RuntimeException();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// 不做任何处理
			}
		}
	}
}
