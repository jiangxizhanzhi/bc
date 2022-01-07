package com.cyberpeace.common.constant;

public interface PatternConstant {

	/** 匹配markdown文件中的图片*/
	String MARKDOWN_IMAGE = "!\\[.*?\\]\\(.+?\\)";

	/** 获取匹配到图片内容的路径*/
	String MARKDOWN_IMAGE_URL = "(?<=\\()[^\\)]+";

	/** 动态flag匹配*/
	String DYNAMIC_FLAG = "\\[FLAG\\[[0-9]+\\]\\]";

	/** 解析markdown文件匹配标题*/
	String MARKDOWN_TITLE = "#{2}[^#]+";

	/** 解析markdown文件匹配标签*/
	String MARKDOWN_TAG = "#{3}[^#]+";

	/** 知识点切割使用*/
	String KNOWLEDGE_SPLIT_STR = "- ";

	/** 横线*/
	String UNDER_LINE = "_";

	/**
	 * 下划线 + 小写字母
	 */
	String UNDER_LINE_LOWERCASE = "_[a-z]";

}
