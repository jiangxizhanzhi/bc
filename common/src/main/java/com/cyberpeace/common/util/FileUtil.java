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

	public static String textFromFile(String filePath) {
		File file = new File(filePath);
		return textFromFile(file);
	}

	public static String textFromFile(File file) {
		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		String text;

		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));
			while (true) {
				try {
					text = reader.readLine();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				if (text == null) {
					break;
				} ;

				stringBuilder.append(text);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("File encoding unsupported");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found");
		} catch (IOException e) {
			throw new RuntimeException("File not opened");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return stringBuilder.toString();
	}

	public static void textToFile(String path, String text) throws IOException {
		textToFile(new FileWriter(path), text);
	}

	public static void textToFile(File file, String text) throws IOException {
		textToFile(new FileWriter(file), text);
	}

	public static void textToFile(FileWriter fileWriter, String text) throws IOException {
		BufferedWriter out = new BufferedWriter(fileWriter);
		out.write(text);
		out.close();
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
	 * @param filePath
	 * @return
	 */
	public static String getContent(String filePath) {

		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));

			String content = "";
			while ((content = br.readLine()) != null) {
				builder.append(content);
			}

			return builder.toString();
		} catch (Exception e) {
			log.error("文件读取失败！ filePath={} error:", filePath, e);
			return "";
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// 不做任何处理
			}
		}
	}
}
