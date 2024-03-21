package com.cyberpeace.common.constant;

public final class PatternConstant {

	/** 匹配markdown文件中的图片*/
	public static final String MARKDOWN_IMAGE = "!\\[.*?\\]\\(.+?\\)";

	/** 匹配markdown文件中md的url*/
	public static final String MARKDOWN_IMAGE_MD_URL = "!\\[.*?\\]\\((.+?)\\)";

	/** 匹配markdown文件中src的url*/
	public static final String MARKDOWN_IMAGE_SRC_URL = "<img src=[\'\"](.+?)[\'\"]";

	/** 获取匹配到图片内容的路径*/
	public static final String MARKDOWN_IMAGE_URL = "(?<=\\()[^\\)]+";

	/** 获取匹配到的压缩文件路径 */
	public static final String MARKDOWN_ZIP_RAR_URL = "(\\[.+?\\])\\((.+?[\\.zip|\\.rar])\\)";

	/** 动态flag匹配*/
	public static final String DYNAMIC_FLAG = "\\{FLAG\\[[0-9]+\\]\\}";

	/** 解析markdown文件匹配标题*/
	public static final String MARKDOWN_TITLE = "#{2}[^#]+";

	public static final String PARSER_MD_TITLE = "#{1,6}[^#]+";

	public static final String PARSER_THEORY_MD_TITLE = "#{1,2}[^#]+";

	/** 解析markdown文件匹配标签*/
	public static final String MARKDOWN_TAG = "#{3}[^#]+";

	/** 题目属性使用*/
	public static final String ATTRIBUTE_SPLIT_STR = " {2}-{1}[^-]+";

	/** 选择题选项匹配*/
	public static final String THEORY_CHOICE_ANSWER = "[ ]{4}([A-Z])\\.[ ](.+)";

	/** 题目答案使用*/
	public static final String ANWSER_SPLIT_STR = "-{1}[^-]+";

	/** 填空答案匹配*/
	public static final String BLANK_ANWSER_STR = "[ ]{0,}\\d\\.[ ](.+)";

	public static final String MARKDOWN_TITLE_MATCH = "#{1-6}[^#]+";

	/** 知识点切割使用*/
	public static final String KNOWLEDGE_SPLIT_STR = "- ";

	/** 横线*/
	public static final String UNDER_LINE = "_";

	public static final String STRIGULA = "-";

	/** 下划线 + 小写字母*/
	public static final String UNDER_LINE_LOWERCASE = "_[a-z]";

	/** 匹配汉字及汉字字符*/
	public static final String CHINESE_CHARACTERS = "[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]";

	/**
	 * 匹配 writeup 中的图片
	 */
	public static final String WRITEUP_IMG = "!\\[[^]]*]\\(.*%s([0-9a-z]{%d})\\.(?:jpg|jpeg|png|bmp|webp|gif)\\)";

	/**
	 * 匹配生成的 uuid
	 */
	public static final String RESOURCE_ID = "\"([a-z0-9]{%d})\"";

	/**
	 * 资管资源题解的图片
	 */
	public static final String WRITEUP_IMG_MD = "!\\[[^]]*]\\((\\./%s/[0-9]+\\.(?:jpg|jpeg|png|bmp|webp|gif))\\)";

	/**
	 * 理论题导入关键字分隔符
	 */
	public static final String VERTICAL_LINE = "\\|";

	/**
	 * `代码块`｜``代码块``
	 */
	public static final String SINGLE_CODING_BLOCK = "`{1,2}[^`](.*?)`{1,2}";

	/**
	 * ```代码块```
	 */
	public static final String MULTIPLE_CODING_BLOCK = "```([\\s\\S]*?)```[\\s]?";

	/** 一级列表*/
	public static final String MD_LIST = "- ";

	/** 二级列表*/
	public static final String SECOND_MD_LIST = "  - ";

	/** 选择题答案匹配*/
	public static final String CHOICE_ANSWER = "^[a-zA-Z|]+$";

	/** 判断题答案*/
	public static final String JUDGEMENT_ANSWER = "^[a-zA-Z]$";

	/** 匹配markdown文件中的链接*/
	public static final String MARKDOWN_URL = "[^!]\\[.*?\\]\\(.+?\\)";

	/** 匹配<p></p>标签*/
	public static final String HTML_P = "<p>(?:(?!<\\/p>)[\\s\\S])*<\\/p>";

	/*课时开头匹配*/
	public static final String REGEX_LESSON_NAME = "^\\d{1,2}-\\d{1,3}_";

	/*课时章节开头匹配*/
	public static final String REGEX_LESSON_CHAPTER_NAME = "^[0-9]{1,2}_";

	private PatternConstant() {}
}
