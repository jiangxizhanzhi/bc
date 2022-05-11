package com.cyberpeace.common.util;

import java.io.*;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * markdown操作工具类
 */
@Slf4j
public class MdUtil {

	private MdUtil() {}

	/**
	 * 解析markdown文件 返回json数据
	 * @param file markdown文件 不做文件存在判断 请在业务代码中判断
	 * @return
	 */
	public static JSONObject parseJsonMarkdown(File file) {

		// 获取markdown行数据
		List<String> contentList = FileUtil.listContent(file);

		// 新建json对象存储数据
		JSONObject valueObj = new JSONObject();

		// 遍历content数据
		String key = "";
		Integer startIndex = -1;
		for (int index = 0; index < contentList.size(); index++) {

			if (contentList.get(index).matches(PatternConstant.MARKDOWN_TITLE)) {
				// 数据校验
				key = validateMarkdownContent(contentList.get(index), file.getAbsolutePath());

				// 标题数据获取
				if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinList(contentList, startIndex, index);
					valueObj.put(key, value);
				}
				startIndex = index + CommonConstant.ONE;
			}

			if (contentList.get(index).matches(PatternConstant.MARKDOWN_TAG)) {

				// 标签数据获取
				if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinList(contentList, startIndex, index);
					valueObj.put(key, value);
				}
				startIndex = index + CommonConstant.ONE;

				// 数据校验
				key = validateMarkdownContent(contentList.get(index), file.getAbsolutePath());
			}

			if (index + 1 == contentList.size()) {
				String value = ListUtil.joinList(contentList, startIndex, contentList.size());
				valueObj.put(key, value);
			}
		}

		return valueObj;
	}

	/**
	 * 校验行内容
	 * @param lineContent
	 */
	private static String validateMarkdownContent(String lineContent, String path) {
		String[] titleArray = lineContent.split(" ");
		if (titleArray.length < CommonConstant.TWO) {
			log.error("parseJsonMarkdown: markdown_path=[{}]", path);
			throw new BusinessException(ErrorCode.FILE_CONTENT_INVALID);
		}

		return titleArray[1].trim();
	}

	public static void main(String[] args) {
		parseMarkdown("/Users/chenzhuo/home/讲义.md");
	}

	/**
	 * 解读markdown文件内容 替换文件路径 由相对路径替换为绝对路径 用于后续文件复制然后替换
	 * @param markdownPath
	 * @return
	 */
	public static String parseMarkdown(String markdownPath) {

		// 获取当前文件夹
		String currentDir = new File(markdownPath).getParentFile().getAbsolutePath();

		// 获取markdown文件内容
		File markdownFile = new File(markdownPath);
		String content = FileUtil.getContent(markdownFile);
		String withOutCode = getContentWithOutCode(markdownFile, CommonConstant.ONE);

		// 匹配文件路径
		List<String> matchStrList = LocalStringUtil.getMatchStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE);

		// 文件路径替换
		for (String matchStr : matchStrList) {
			String imagePath = LocalStringUtil.getFirstMatchStr(matchStr,
					PatternConstant.MARKDOWN_IMAGE_URL);
			// 部分markdown文件中文件路径 存在./xxx/xxx.png 所以要把逗点给去掉
			String relativePath = imagePath;
			if (imagePath.startsWith(CommonConstant.PERIOD)) {
				relativePath = imagePath.replaceFirst(CommonConstant.PERIOD,
						CommonConstant.EMPTY_STRING);
			}

			File replaceFile = new File(currentDir, relativePath);
			if (replaceFile.exists()) {
				content = content.replace(imagePath, replaceFile.getAbsolutePath());
			} else {
				// 文件后缀匹配，如果文件无后缀则可能不是文件
				String firstMatchStr = LocalStringUtil.getFirstMatchStr(relativePath,
						PatternConstant.FILE_SUFFIX_MATCH);
				if (StringUtils.isNotBlank(firstMatchStr)) {
					log.error("File not exist! file_path=[{}]", replaceFile.getAbsolutePath());
					throw new BusinessException(ErrorCode.FILE_NOT_EXIST);
				}
			}

		}

		return content;
	}

	/**
	 * 读取md文件内容 不包含代码块
	 * @param file
	 * @param type 0=读取全部内容 1=过滤掉代码块
	 * @return
	 */
	public static String getContentWithOutCode(File file, int type) {

		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		boolean coding = false;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String content;

			while ((content = br.readLine()) != null) {
				if (content.trim().equals(CommonConstant.MD_CODING_BLOCK)
						&& CommonConstant.ONE.intValue() == type) {
					coding = !coding;
				} else {
					if (!coding) {
						builder.append(content + System.lineSeparator());
					}
				}

			}

			return builder.toString();
		} catch (Exception e) {
			// 文件读取失败
			log.error("file read failed! filePath={} error:", file.getAbsolutePath(), e);
			throw new BusinessException(ErrorCode.FILE_CONTENT_INVALID);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// 不做任何处理
			} catch (NullPointerException e) {
				// 对象未创建空指针异常捕获，不做任何处理
			}
		}
	}
}
