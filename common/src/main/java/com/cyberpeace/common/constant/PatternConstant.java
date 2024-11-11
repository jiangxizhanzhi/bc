package com.cyberpeace.common.constant;

/**
 * 模式常数
 *
 * @author SN
 * @date 2024/11/07
 */
public interface PatternConstant {

	/**
	 * 匹配markdown文件中的图片
	 */
	String MARKDOWN_IMAGE = "!\\[.*?\\]\\(.+?\\)";

	/**
	 * 匹配markdown文件中md的url
	 */
	String MARKDOWN_IMAGE_MD_URL = "!\\[.*?\\]\\((.+?)\\)";

	/**
	 * 匹配markdown文件中src的url
	 */
	String MARKDOWN_IMAGE_SRC_URL = "<img src=[\'\"](.+?)[\'\"]";

	/**
	 * 获取匹配到图片内容的路径
	 */
	String MARKDOWN_IMAGE_URL = "(?<=\\()[^\\)]+";

	/**
	 * 获取匹配到的压缩文件路径
	 */
	String MARKDOWN_ZIP_RAR_URL = "(\\[.+?\\])\\((.+?[\\.zip|\\.rar])\\)";

	/**
	 * 动态flag匹配
	 */
	String DYNAMIC_FLAG = "\\{FLAG\\[[0-9]+\\]\\}";

	/**
	 * 解析markdown文件匹配标题
	 */
	String MARKDOWN_TITLE = "#{2}[^#]+";

	/**
	 * 解析器 md 标题
	 */
	String PARSER_MD_TITLE = "#{1,6}[^#]+";

	/**
	 * 解析 器理论Markdown标题
	 */
	String PARSER_THEORY_MD_TITLE = "#{1,2}[^#]+";

	/**
	 * 解析markdown文件匹配标签
	 */
	String MARKDOWN_TAG = "#{3}[^#]+";

	/**
	 * 题目属性使用
	 */
	String ATTRIBUTE_SPLIT_STR = " {2}-{1}[^-]+";

	/**
	 * 选择题选项匹配
	 */
	String THEORY_CHOICE_ANSWER = "[ ]{4}([A-Z])\\.[ ](.+)";

	/**
	 * 题目答案使用
	 */
	String ANWSER_SPLIT_STR = "-{1}[^-]+";

	/**
	 * 填空答案匹配
	 */
	String BLANK_ANWSER_STR = "[ ]{0,}\\d\\.[ ](.+)";

	/**
	 * Markdown 标题匹配
	 */
	String MARKDOWN_TITLE_MATCH = "#{1-6}[^#]+";

	/**
	 * 知识点切割使用
	 */
	String KNOWLEDGE_SPLIT_STR = "- ";

	/**
	 * 横线
	 */
	String UNDER_LINE = "_";

	/**
	 * 纹状
	 */
	String STRIGULA = "-";

	/**
	 * 下划线 + 小写字母
	 */
	String UNDER_LINE_LOWERCASE = "_[a-z]";

	/**
	 * 匹配汉字及汉字字符
	 */
	String CHINESE_CHARACTERS = "[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]";

	/**
	 * 匹配 writeup 中的图片
	 */
	String WRITEUP_IMG = "!\\[[^]]*]\\(.*%s([0-9a-z]{%d})\\.(?:jpg|jpeg|png|bmp|webp|gif)\\)";

	/**
	 * 匹配生成的 uuid
	 */
	String RESOURCE_ID = "\"([a-z0-9]{%d})\"";

	/**
	 * 资管资源题解的图片
	 */
	String WRITEUP_IMG_MD = "!\\[[^]]*]\\((\\./%s/[0-9]+\\.(?:jpg|jpeg|png|bmp|webp|gif))\\)";

	/**
	 * 理论题导入关键字分隔符
	 */
	String VERTICAL_LINE = "\\|";

	/**
	 * `代码块`｜``代码块``
	 */
	String SINGLE_CODING_BLOCK = "`{1,2}[^`](.*?)`{1,2}";

	/**
	 * ```代码块```
	 */
	String MULTIPLE_CODING_BLOCK = "```([\\s\\S]*?)```[\\s]?";

	/**
	 * 一级列表
	 */
	String MD_LIST = "- ";

	/**
	 * 二级列表
	 */
	String SECOND_MD_LIST = "  - ";

	/**
	 * 选择题答案匹配
	 */
	String CHOICE_ANSWER = "^[a-zA-Z|]+$";

	/**
	 * 判断题答案
	 */
	String JUDGEMENT_ANSWER = "^[a-zA-Z]$";

	/**
	 * 匹配markdown文件中的链接
	 */
	String MARKDOWN_URL = "[^!]\\[.*?\\]\\(.+?\\)";

	/**
	 * 匹配<p></p>标签
	 */
	String HTML_P = "<p>(?:(?!<\\/p>)[\\s\\S])*<\\/p>";

	/**
	 * 小写字母
	 */
	String LOWER_CASE_LETTER = "[a-z]";
}
