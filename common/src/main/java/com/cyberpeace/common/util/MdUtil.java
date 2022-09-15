package com.cyberpeace.common.util;

import java.io.*;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
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
		String contentWithOutCode = getContentWithOutCode(new File("/Users/chenzhuo/home/md/指导书.md"));
		System.out.println(contentWithOutCode);
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
		String withOutCode = getContentWithOutCode(markdownFile);

		// 匹配文件路径
		List<String> matchStrList = LocalStringUtil.getMatchUrlStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE_MD_URL);

		// 匹配文件src地址
		List<String> matchSrcStrList = LocalStringUtil.getMatchUrlStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE_SRC_URL);

		matchStrList.addAll(matchSrcStrList);

		// markdown中引用重复文件, 避免String.replace替换多次, 转为Set（zentao 9894）
		Set<String> matchStrSet = Sets.newHashSet(matchStrList);

		// 文件路径替换
		for (String imagePath : matchStrSet) {
//			String imagePath = LocalStringUtil.getFirstMatchStr(matchStr,
//					PatternConstant.MARKDOWN_IMAGE_URL);
			// 部分markdown文件中文件路径 存在./xxx/xxx.png 所以要把逗点给去掉
			String relativePath = imagePath;
			if (imagePath.startsWith(CommonConstant.PERIOD)) {
				relativePath = imagePath.replaceFirst(CommonConstant.PERIOD,
						CommonConstant.EMPTY_STRING);
			}

			File replaceFile = new File(currentDir, relativePath);
			if (!replaceFile.exists()) {
				log.error("File not exist! file_path=[{}]", replaceFile.getAbsolutePath());
				throw new BusinessException(ErrorCode.FILE_ERROR_FAIL, imagePath);
			}
			content = content.replace(imagePath, replaceFile.getAbsolutePath());
		}

		return content;
	}

	/**
	 * markdown内容过滤掉代码块
	 * @return
	 */
	public static String filterCodeContent(String content) {
		// 匹配```代码块```
		List<String> matchStr = LocalStringUtil.getMatchStr(content, PatternConstant.MULTIPLE_CODING_BLOCK);
		String tmp = LocalStringUtil.replaceToBlank(content, matchStr);

		// 匹配`代码块`｜``代码块``
		List<String> singleMatchStr = LocalStringUtil.getMatchStr(tmp, PatternConstant.SINGLE_CODING_BLOCK);
		return LocalStringUtil.replaceToBlank(tmp, singleMatchStr);
	}


	private static boolean append(String content, boolean coding, StringBuilder builder, int type) {
		// 如果开始和结尾在一行里 直接跳过此行
		boolean singleLine = content.trim().length() >= CommonConstant.FOUR
				&& content.trim().startsWith(CommonConstant.MD_CODING_BLOCK)
				&& content.trim().endsWith(CommonConstant.MD_CODING_BLOCK)
				&& CommonConstant.ONE.intValue() == type;
		if (singleLine) {
			return coding;
		}

		// 如果此行开始为代码块标识符 则对下面内容进行略过
		boolean omit = (content.trim().startsWith(CommonConstant.MD_CODING_BLOCK)
				|| content.trim().endsWith(CommonConstant.MD_CODING_BLOCK))
				&& CommonConstant.ONE.intValue() == type;
		if (omit) {
			coding = !coding;
		} else {
			if (!coding) {
				String compareContent = content;
				//当前行内存在字符 ` 时，开始裁剪
				while (content.contains(CommonConstant.MD_CODING_BLOCK_ONE)) {
					int firstIndex = content.indexOf(CommonConstant.MD_CODING_BLOCK_ONE, 0);
					compareContent = compareContent.replaceFirst(CommonConstant.MD_CODING_BLOCK_ONE, "");
					//存在两个`符号时，进行裁剪，否则跳出循环
					if (compareContent.contains(CommonConstant.MD_CODING_BLOCK_ONE)) {
						int secondIndex = compareContent.indexOf(CommonConstant.MD_CODING_BLOCK_ONE, 0);
						content = content.replace(content.substring(firstIndex,secondIndex + 2),"");
						compareContent = content;
					}else {
						break;
					}
				}
				builder.append(content + System.lineSeparator());
			}
		}

		return coding;
	}

	/**
	 * 读取md文件内容 不包含代码块
	 * @param file
	 * @return
	 */
	public static String getContentWithOutCode(File file) {
		String content = FileUtil.getContent(file);
		return filterCodeContent(content);
	}
}
