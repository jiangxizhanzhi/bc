/**
 * ### 代码分析 
 * 该代码定义了一个名为 `LocalStringUtil` 的工具类，提供了一系列与字符串操作相关的实用方法。以下是对其功能的简要分析： 
 * --- 
 * #### **1. 私有构造函数** 
 * - `private LocalStringUtil()`：防止实例化此工具类，确保其方法只能通过静态方式调用。 
 * --- 
 * #### **2. 方法概览** 
 * ##### **字符串格式化相关** 
 * - **`frontBackLine(String value)`** 
 * 在字符串前后添加换行符。 
 * - 如果输入为空或空白，则返回默认空字符串。 
 * - **`snakeCaseFormat(String name)`** 
 * 将字符串转换为蛇形命名法（snake_case）。 
 * - 处理大小写、空格、下划线等字符，生成符合规范的蛇形命名字符串。 
 * --- 
 * ##### **字符串转换为列表** 
 * - **`strToList(String data)`** 
 * 将逗号分隔的字符串转换为整数列表。 
 * - 如果输入为空或空白，则返回空列表。 
 * - **`strToListStr(String data)`** 
 * 将逗号分隔的字符串转换为字符串列表。 
 * - 同样处理空输入情况。 
 * --- 
 * ##### **字符串操作** 
 * - **`strRemoveSubStr(String ids, String data)`** 
 * 从以 JSON 数组形式存储的字符串中移除指定的子串。 
 * - 使用 `JsonUtil` 解析并修改数组内容。 
 * - **`replaceAll(String content, List<String> old, List<String> newValue)`** 
 * 批量替换字符串内容。 
 * - 支持动态调整新值数量以匹配旧值。 
 * - **`replaceToBlank(String content, List<String> matchStr)`** 
 * 将匹配到的子串替换为空白。 
 * - 避免空值或无效输入导致异常。 
 * --- 
 * ##### **基于正则表达式的字符串操作** 
 * - **`getMatchStr(String content, String regex)`** 
 * 根据正则表达式提取匹配的内容。 
 * - 返回所有匹配结果的列表。 
 * - **`getMatchUrlStr(String content, String regex)`** 
 * 提取 URL 类型的匹配结果。 
 * - 特殊处理匹配组编号为 `1` 的内容。 
 * - **`getMatchStr(String content, String regex, Integer index)`** 
 * 根据正则表达式和指定子组索引提取匹配内容。 
 * - **`getMatchStrBool(String content, String regex)`** 
 * 检查是否匹配正则表达式。 
 * - 返回布尔值。 
 * - **`getFirstMatchStr(String content, String regex)`** 
 * 获取第一个匹配项。 
 * - 若无匹配，返回空字符串。 
 * - **`getAllMatchStr(String content, String regex, Integer index)`** 
 * 获取所有匹配项。 
 * - 返回完整的匹配列表。 
 * --- 
 * ##### **随机字符串生成** 
 * - **`getRandomStr(int length)`** 
 * 生成指定长度的随机字符串。 
 * - 使用 `IDGeneratorUtil` 提供的随机 ID 进行拼接。 
 * --- 
 * ##### **字符串转义** 
 * - **`transfer(String search)`** 
 * 对字符串中的特殊字符进行转义（如 `_`, `%`, `'`），用于安全处理查询条件。 
 * --- 
 * #### **总结** 
 * 该工具类提供了丰富的字符串操作功能，包括格式化、转换、替换、正则匹配及随机字符串生成等。代码结构清晰，逻辑严谨，依赖了多个外部工具类（如 `CommonConstant`, `JsonUtil`, `IDGeneratorUtil` 等），适合在需要复杂字符串处理的场景中使用。 
**/
package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.CommonConstant;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * local string util （本地字符串 util）
 *
 * @author SN
 * @date 2024/11/08
 */
public class LocalStringUtil {

	/**
	 * local string util （本地字符串 util）
	 */
	private LocalStringUtil() {}

	/**
	 * 前后增加换行符
	 *
	 * @param value
	 * @return
	 */
	public static String frontBackLine(String value) {
        if (StringUtils.isBlank(value)) {
            value = CommonConstant.EMPTY_STRING;
        }
        return System.lineSeparator() + value + System.lineSeparator();
    }

	/**
	 * 蛇形大小写格式
	 *
	 * @param name 名称
	 * @return {@code String }
	 */
	public static String snakeCaseFormat(String name) {
		final StringBuilder result = new StringBuilder();

		boolean lastUppercase = false;

		for (int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
			char lastEntry = i == 0 ? 'X' : result.charAt(result.length() - 1);
			if (ch == ' ' || ch == '_' || ch == '-' || ch == '.') {
				lastUppercase = false;

				if (lastEntry == '_') {
					continue;
				} else {
					ch = '_';
				}
			} else if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
				if (i > 0) {
					if (lastUppercase) {
						if (i + 1 < name.length()) {
							char next = name.charAt(i + 1);
							if (!Character.isUpperCase(next) && Character.isAlphabetic(next)) {
								if (lastEntry != '_') {
									result.append('_');
								}
							}
						}
					} else {
						if (lastEntry != '_') {
							result.append('_');
						}
					}
				}
				lastUppercase = true;
			} else {
				lastUppercase = false;
			}

			result.append(ch);
		}
		return result.toString();
	}

	/**
	 * str 到 list
	 *
	 * @param data 数据
	 * @return {@code List<Integer> }
	 */
	public static List<Integer> strToList(String data) {
		if(StringUtils.isBlank(data)){
			return Lists.newArrayList();
		}
		return Arrays.stream(StringUtils.split(data, ',')).map(Integer::parseInt)
				.collect(Collectors.toList());
	}

	/**
	 * str 列出 str
	 *
	 * @param data 数据
	 * @return {@code List<String> }
	 */
	public static List<String> strToListStr(String data) {
		if(StringUtils.isBlank(data)){
			return Lists.newArrayList();
		}
		return Arrays.stream(StringUtils.split(data, ',')).collect(Collectors.toList());
	}

	/**
	 * str remove sub str
	 *
	 * @param ids  id列表
	 * @param data 数据
	 * @return {@code String }
	 */
	public static String strRemoveSubStr(String ids, String data) {
		List<String> strings = JsonUtil.toJsonObjectArray(ids, String.class);

		strings.remove(data);

		return JsonUtil.toJsonString(strings);

	}

	/**
	 * 根据正则表达是获取内容中的匹配数据
	 *
	 * @param content
	 * @param regex   正则表达式
	 * @return
	 */
	public static List<String> getMatchStr(String content, String regex) {

		List<String> strList = Lists.newArrayList();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			strList.add(matcher.group());
		}
		return strList;
	}

	/**
	 * 根据正则表达是获取内容中url的匹配数据
	 *
	 * @param content
	 * @param regex   正则表达式集合
	 * @return
	 */
	public static List<String> getMatchUrlStr(String content, String regex) {

		List<String> strList = Lists.newArrayList();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			strList.add(matcher.group(1).trim());
		}
		return strList;
	}

	/**
	 * 根据正则表达是获取内容中的匹配数据
	 *
	 * @param content 内容
	 * @param regex   正则表达式
	 * @param index   子匹配序号
	 * @return 匹配到的字符串列表
	 */
	public static List<String> getMatchStr(String content, String regex, Integer index) {

		List<String> strList = Lists.newArrayList();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			strList.add(matcher.group(index).trim());
		}
		return strList;
	}

	/**
	 * 获取火柴str布尔
	 *
	 * @param content 内容
	 * @param regex   正则表达式
	 * @return boolean
	 */
	public static boolean getMatchStrBool(String content, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		return matcher.find();
	}

	/**
	 * 根据正则表达是获取内容中的匹配数据 仅匹配一个
	 *
	 * @param content
	 * @param regex
	 * @return
	 */
	public static String getFirstMatchStr(String content, String regex) {

		List<String> matchStr = getMatchStr(content, regex);

		if (CollectionUtils.isEmpty(matchStr)) {
			return "";
		}
		return matchStr.get(CommonConstant.ZERO).trim();
	}

	/**
	 * 根据正则表达是获取内容中的匹配数据 仅匹配一个
	 *
	 * @param content
	 * @param regex
	 * @param index   子匹配序号
	 * @return
	 */
	public static String getFirstMatchStr(String content, String regex, Integer index) {

		List<String> matchStr = getMatchStr(content, regex, index);

		if (CollectionUtils.isEmpty(matchStr)) {
			return CommonConstant.EMPTY_STRING;
		}
		return matchStr.get(CommonConstant.ZERO).trim();
	}

	/**
	 * 根据正则表达是获取内容中的匹配数据 匹配全部
	 *
	 * @param content
	 * @param regex
	 * @param index   子匹配序号
	 * @return
	 */
	public static List<String> getAllMatchStr(String content, String regex, Integer index) {

		List<String> matchStr = getMatchStr(content, regex, index);


		return matchStr;
	}

	/**
	 * 数据替换
	 *
	 * @param content
	 * @param old
	 * @param newValue
	 * @return
	 */
	public static String replaceAll(String content, List<String> old, List<String> newValue) {

		if (StringUtils.isBlank(content)) {
			return content;
		}

		if (old.size() > newValue.size()) {
			for (int i = 0; i < old.size() - newValue.size(); i++) {
				newValue.add("");
			}
		}

		for (int i = 0; i < old.size(); i++) {
			content = content.replace(old.get(i), newValue.get(i));
		}

		return content;
	}

	/**
	 * 获取随机长度字符串
	 *
	 * @param length
	 * @return
	 */
	public static String getRandomStr(int length) {
		String randomStr = IDGeneratorUtil.getID();
		if (randomStr.length() >= length) {
			return randomStr.substring(0, length);
		}

		return randomStr + getRandomStr(length - randomStr.length());
	}

	/**
	 * 替换为空白
	 *
	 * @param content  内容
	 * @param matchStr 匹配力量
	 * @return {@code String }
	 */
	public static String replaceToBlank(String content, List<String> matchStr) {
		if (StringUtils.isBlank(content) || CollectionUtils.isEmpty(matchStr)) {
			return content;
		}

		for (int i = 0; i < matchStr.size(); i++) {
			content = content.replace(matchStr.get(i), "");
		}

		return content;
	}

	/**
	 * 转移
	 *
	 * @param search 搜索
	 * @return {@code String }
	 */
	public static String transfer(String search) {
		String newPattern = search;

		if (newPattern.contains("_") && !newPattern.contains("\\_")) {
			newPattern = newPattern.replace("_", "\\_");
		}
		if (newPattern.contains("%") && !newPattern.contains("\\%")) {
			newPattern = newPattern.replace("%", "\\%");
		}
		if (newPattern.contains("\'") && !newPattern.contains("\\'")) {
			newPattern = newPattern.replace("\'", "\\'");
		}

		return newPattern;
	}
}
