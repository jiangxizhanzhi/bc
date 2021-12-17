package com.cyberpeace.common.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.google.common.collect.Lists;
import net.sf.jsqlparser.statement.create.table.Index;
import org.apache.commons.lang3.StringUtils;
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

	/**
	 * 以行读取文件 返回行数据列表
	 * @param file
	 * @return
	 */
	public static List<String> listContent(File file) {
		BufferedReader br = null;
		List<String> contentList = Lists.newArrayList();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

			String content;
			while ((content = br.readLine()) != null) {
				contentList.add(content);
			}

			return contentList;
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

	/**
	 * 文件拷贝
	 * @param path
	 * @param dest
	 */
	public static void copyFile(String path, String dest) {

		FileChannel input = null;
		FileChannel output = null;

		try {
			input = new FileInputStream(path).getChannel();
			output = new FileOutputStream(dest).getChannel();

			output.transferFrom(input, 0, input.size());
		} catch (Exception e) {
			log.error("copyFile: File copy failed! path=[{}], dest=[{}]", path, dest);
			throw new BusinessException(ErrorCode.FILE_COPY_FAIL);
		} finally {
			try {
				input.close();
				output.close();
			} catch (Exception e) {
				// 不管
			}

		}
	}
}
