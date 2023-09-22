package com.cyberpeace.cr.tool.utils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  解析Markdown
 *  传入f.readlines()的返回值或小段内容列表
 *  默认解析标题（##）及正文（###）的内容
 *  也可传入tag，如'*'，则解析两个'*'之间的内容或直到文本结束
 */
public class ParseMarkdown {


	private final File file;
	public static final String TITLE = "__title__";

	private static final String TITLE_PREFIX = "## ";

	private static final String LABEL_PREFIX = "### ";

	private ArrayListMultimap<String, String> data = ArrayListMultimap.create();

	private void setMap(String key, String value) {
		String newValue;
		if (value.startsWith("- ")) {
			newValue = value.split("- ", 2)[1];
		}else {
			newValue = value;
		}
		data.put(key.trim(), newValue.trim());
	}

	public ArrayListMultimap<String, String> getData() {
		return data;
	}

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
	 * @return
	 */
	private List<String> loadFile() {
		ArrayList<String> lines = Lists.newArrayList();

		try (FileInputStream fileInputStream = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(fileInputStream, "utf-8"));) {

			String tempStr;
			while ((tempStr = reader.readLine()) != null) {
				lines.add(tempStr);
			}
		} catch (IOException e) {
			e.printStackTrace();
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
