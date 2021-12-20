package com.cyberpeace.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.InvalidParamException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

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
		String[] titleArray = lineContent.split(" ", 2);
		if (titleArray.length != CommonConstant.TWO) {
			log.error("parseJsonMarkdown: markdown_path=[{}]", path);
			throw new InvalidParamException("md文件内容格式错误！");
		}

		return titleArray[1].trim();
	}

	/**
	 * 解读mark文件内容 替换文件路径
	 * @param markdownPath
	 * @return
	 */
	public static String parseMarkdown(String markdownPath) {

		String currentDir = new File(markdownPath).getParentFile().getAbsolutePath();

		String content = FileUtil.getContent(new File(markdownPath));

		List<String> matchStrList = LocalStringUtil.getMatchStr(content,
				PatternConstant.MARKDOWN_IMAGE);

		for (String matchStr : matchStrList) {
			String imagePath = LocalStringUtil.getFirstMatchStr(matchStr,
					PatternConstant.MARKDOWN_IMAGE_URL);
			File replaceFile = new File(currentDir, imagePath);
			if (replaceFile.exists()) {
				content = content.replace(imagePath, replaceFile.getAbsolutePath());
			}
		}

		return content;
	}

}
