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
				validateMarkdownContent(contentList.get(index), file.getAbsolutePath());

				// 标题数据获取
				if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinList(contentList, startIndex, index);
					valueObj.put(key, value);
					startIndex = index + CommonConstant.ONE;
				}
			}

			if (contentList.get(index).matches(PatternConstant.MARKDOWN_TAG)) {
				// 数据校验
				validateMarkdownContent(contentList.get(index), file.getAbsolutePath());

				// 标签数据获取
				if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinList(contentList, startIndex, index);
					valueObj.put(key, value);
					startIndex = index + CommonConstant.ONE;
				}
			}

			if (index + 1 == contentList.size()) {
				valueObj.put(key, contentList.get(index));
			}
		}

		return valueObj;
	}

	/**
	 * 校验行内容
	 * @param lineContent
	 */
	private static void validateMarkdownContent(String lineContent, String path) {
		String[] titleArray = lineContent.split(" ", 1);
		if (titleArray.length != CommonConstant.TWO) {
			log.error("parseJsonMarkdown: markdown_path=[{}]", path);
			throw new InvalidParamException("md文件内容格式错误！");
		}
	}

}
