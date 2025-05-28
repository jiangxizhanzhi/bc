/**
 * 这段代码是一个用于解析Markdown文件的工具类。以下是对其主要部分的简要分析： 
 * ### 类定义与成员变量 
 * - `ParseMarkdown` 类用于解析Markdown文件。 
 * - `file`: 保存要解析的文件对象。 
 * - `TITLE`: 定义标题的特殊字符串。 
 * - `TITLE_PREFIX`: 标识标题的前缀（例如："## "）。 
 * - `LABEL_PREFIX`: 标识标签的前缀（例如："### "）。 
 * - `data`: 使用 `ArrayListMultimap` 存储解析后的数据。 
 * ### 方法 
 * - **构造函数**: 
 * - `ParseMarkdown(File file)`: 初始化时设置要解析的文件对象。 
 * - **核心方法**: 
 * - `handler()`: 调用 `loadFile()` 和 `parse()` 来加载并解析文件内容。 
 * - **辅助方法**: 
 * - `loadFile()`: 读取文件内容，并将其按行存储在 `ArrayList<String>` 中。使用 `BufferedReader` 逐行读取文件，并处理可能的 `IOException`。 
 * - `parse()`: 解析文件内容，根据行的前缀（如 `##` 或 `###`）来确定标题或标签，并将内容存储到 `data` 中。 
 * - `setMap(String key, String value)`: 将解析的内容添加到 `data` 中。如果行以 `- ` 开头，则去掉 `- ` 后的内容。 
 * ### 总结 
 * - 这个类主要用于解析Markdown文件中的标题和正文内容。 
 * - 使用 `ArrayListMultimap` 存储解析结果，其中键是标题或标签，值是对应的内容。 
 * - 该类通过读取文件并逐行解析，根据行的前缀来确定内容类型并进行存储。 
 * 这个工具类能够有效地从Markdown文件中提取出标题和正文内容，并将其组织成易于访问的数据结构。 
**/
package com.cyberpeace.cr.tool.utils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析Markdown
 * 传入f.readlines()的返回值或小段内容列表
 * 默认解析标题（##）及正文（###）的内容
 * 也可传入tag，如'*'，则解析两个'*'之间的内容或直到文本结束
 *
 * @author SN
 * @date 2024/11/08
 */
public class ParseMarkdown {


	/**
	 * 文件
	 */
	private final File file;
	/**
	 * 标题
	 */
	public static final String TITLE = "__title__";

	/**
	 * 标题前缀
	 */
	private static final String TITLE_PREFIX = "## ";

	/**
	 * 标签前缀
	 */
	private static final String LABEL_PREFIX = "### ";

	/**
	 * 数据
	 */
	private ArrayListMultimap<String, String> data = ArrayListMultimap.create();

	/**
	 * 设置地图
	 *
	 * @param key   钥匙
	 * @param value 值
	 */
	private void setMap(String key, String value) {
		String newValue;
		if (value.startsWith("- ")) {
			newValue = value.split("- ", 2)[1];
		}else {
			newValue = value;
		}
		data.put(key.trim(), newValue.trim());
	}

	/**
	 * 获取数据
	 *
	 * @return {@code ArrayListMultimap<String, String> }
	 */
	public ArrayListMultimap<String, String> getData() {
		return data;
	}

	/**
	 * 解析 Markdown
	 *
	 * @param file 文件
	 */
	public ParseMarkdown(File file) {
		this.file = file;
	}

	/**
	 * 处理markdown流程
	 */
	public void handler(){
		loadFile();
		parse();
	}

	/**
	 * 加载文件内容
	 *
	 * @return
	 */
	private List<String> loadFile() {
		BufferedReader reader = null;
		ArrayList<String> lines = Lists.newArrayList();

		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			reader = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));
			String tempStr;
			while ((tempStr = reader.readLine()) != null) {
				lines.add(tempStr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lines;
	}

	/**
	 * 解析文件内容
	 */
	private void parse() {
		List<String> lines = loadFile();
		String key = null;
		for (String line : lines) {
			if (line.startsWith(TITLE_PREFIX)) {
				String title = line.substring(TITLE_PREFIX.length());
				setMap(TITLE, title);
			} else if (line.startsWith(LABEL_PREFIX)) {
				key = line.substring(LABEL_PREFIX.length());
			} else if (key != null && !line.equals("")){
				setMap(key, line);
			}
		}
	}
}
