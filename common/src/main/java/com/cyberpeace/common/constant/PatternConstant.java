package com.cyberpeace.common.constant;

public interface PatternConstant {

	/** 匹配markdown文件中的图片*/
	String MARKDOWN_IMAGE = "!\\[.*?\\]\\(.+?\\)";

	/** 获取匹配到图片内容的路径*/
	String MARKDOWN_IMAGE_URL = "(?<=\\()[^\\)]+";

	/** 动态flag匹配*/
	String DYNAMIC_FLAG = "\\[FLAG\\[[0-9]+\\]\\]";

}
