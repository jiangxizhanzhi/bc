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
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public class ParseMarkdown {


	/**
	 * 文件
	 *
	 * @see File
	 */
	private final File file;
	/**
	 * 标题
	 *
	 * @see String
	 */
	public static final String TITLE = "__title__";

	/**
	 * 标题前缀
	 *
	 * @see String
	 */
	private static final String TITLE_PREFIX = "## ";

	/**
	 * 标签前缀
	 *
	 * @see String
	 */
	private static final String LABEL_PREFIX = "### ";

	/**
	 * 数据
	 *
	 * @see ArrayListMultimap<String, String>
	 */
	private ArrayListMultimap<String, String> data = ArrayListMultimap.create();

	/**
	 * 设置地图
	 *
	 * @param key   钥匙
	 * @param value 值
	 * @throws
	 * @see String
	 * @see String
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
	 * @throws
	 * @see ArrayListMultimap<String, String>
	 */
	public ArrayListMultimap<String, String> getData() {
		return data;
	}

	/**
	 * 解析 Markdown
	 *
	 * @param file 文件
	 * @throws
	 * @see File
	 */
	public ParseMarkdown(File file) {
		this.file = file;
	}

	/**
	 * 处理markdown流
	 *
	 * @throws
	 */
	public void handler(){
		loadFile();
		parse();
	}

	/**
	 * 加载文件内容
	 *
	 * @return
	 * @throws
	 * @see List<String>
	 */
	private List<String> loadFile() {
		BufferedReader reader = null;
		ArrayList<String> lines = Lists.newArrayList();

		try{
			FileInputStream fileInputStream = new FileInputStream(file);
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
	 *
	 * @throws
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
