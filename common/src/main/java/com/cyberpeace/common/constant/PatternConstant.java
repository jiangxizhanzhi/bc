package com.cyberpeace.common.constant;

/**
 * 模式常数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PatternConstant` 的 Java 接口，主要用于存储各种正则表达式模式的常量。这些常量用于匹配或解析不同类型的文本内容，如 Markdown 文件、HTML 标签、图片链接等。以下是代码的主要分析： 
 * ### 主要功能： 
 * 1. **正则表达式模式的集中管理**： 
 * - 该接口通过定义多个静态字符串常量，存储了各种正则表达式模式。 
 * - 这些模式涵盖了从图片链接、标题、标签到特殊符号等多种场景。 
 * 2. **应用场景广泛**： 
 * - 涉及 Markdown 文件的解析与处理（如图片、标题、标签等）。 
 * - 包含 HTML 元素（如 `<p>` 标签）的匹配。 
 * - 支持多种文本格式的解析，如选择题、填空题等。 
 * ### 关键特性： 
 * - **模块化设计**：每个常量都有清晰的注释，描述其用途和匹配目标。 
 * - **复用性**：通过将正则表达式集中存储在接口中，方便在其他类中直接引用。 
 * - **扩展性**：可以轻松添加新的正则表达式模式以支持更多功能。 
 * ### 示例说明： 
 * - `MARKDOWN_IMAGE`：匹配 Markdown 文件中的图片链接。 
 * - `PARSER_MD_TITLE`：匹配 Markdown 文件中的标题（1 至 6 级）。 
 * - `WRITEUP_IMG`：匹配 Writeup 文件中的图片链接。 
 * - `HTML_P`：匹配 HTML 中的 `<p>` 标签及其内容。 
 * ### 总结： 
 * 该代码是一个典型的工具类接口，主要用于提供通用的正则表达式模式。它简化了正则表达式的重复编写，并提高了代码的可维护性和复用性。适用于需要处理多种文本格式的应用场景。 
**/
public interface PatternConstant {

	/**
	 * 匹配markdown文件中的图片
	 *
	 * @see String
	 */
	String MARKDOWN_IMAGE = "!\\[.*?\\]\\(.+?\\)";

	/**
	 * 匹配markdown文件中md的url
	 *
	 * @see String
	 */
	String MARKDOWN_IMAGE_MD_URL = "!\\[.*?\\]\\((.+?)\\)";

	/**
	 * 匹配markdown文件中src的url
	 *
	 * @see String
	 */
	String MARKDOWN_IMAGE_SRC_URL = "<img src=[\'\"](.+?)[\'\"]";

	/**
	 * 获取匹配到图片内容的路径
	 *
	 * @see String
	 */
	String MARKDOWN_IMAGE_URL = "(?<=\\()[^\\)]+";

	/**
	 * 获取匹配到的压缩文件路径
	 *
	 * @see String
	 */
	String MARKDOWN_ZIP_RAR_URL = "(\\[.+?\\])\\((.+?[\\.zip|\\.rar])\\)";

	/**
	 * 动态flag匹配
	 *
	 * @see String
	 */
	String DYNAMIC_FLAG = "\\{FLAG\\[[0-9]+\\]\\}";

	/**
	 * 解析markdown文件匹配标题
	 *
	 * @see String
	 */
	String MARKDOWN_TITLE = "#{2}[^#]+";

	/**
	 * 解析器 md 标题
	 *
	 * @see String
	 */
	String PARSER_MD_TITLE = "#{1,6}[^#]+";

	/**
	 * 解析 器理论Markdown标题
	 *
	 * @see String
	 */
	String PARSER_THEORY_MD_TITLE = "#{1,2}[^#]+";

	/**
	 * 解析markdown文件匹配标签
	 *
	 * @see String
	 */
	String MARKDOWN_TAG = "#{3}[^#]+";

	/**
	 * 题目属性使用
	 *
	 * @see String
	 */
	String ATTRIBUTE_SPLIT_STR = " {2}-{1}[^-]+";

	/**
	 * 选择题选项匹配
	 *
	 * @see String
	 */
	String THEORY_CHOICE_ANSWER = "[ ]{4}([A-Z])\\.[ ](.+)";

	/**
	 * 题目答案使用
	 *
	 * @see String
	 */
	String ANWSER_SPLIT_STR = "-{1}[^-]+";

	/**
	 * 填空答案匹配
	 *
	 * @see String
	 */
	String BLANK_ANWSER_STR = "[ ]{0,}\\d\\.[ ](.+)";

	/**
	 * Markdown 标题匹配
	 *
	 * @see String
	 */
	String MARKDOWN_TITLE_MATCH = "#{1-6}[^#]+";

	/**
	 * 知识点切割使用
	 *
	 * @see String
	 */
	String KNOWLEDGE_SPLIT_STR = "- ";

	/**
	 * 横线
	 *
	 * @see String
	 */
	String UNDER_LINE = "_";

	/**
	 * 纹状
	 *
	 * @see String
	 */
	String STRIGULA = "-";

	/**
	 * 下划线 + 小写字母
	 *
	 * @see String
	 */
	String UNDER_LINE_LOWERCASE = "_[a-z]";

	/**
	 * 匹配汉字及汉字字符
	 *
	 * @see String
	 */
	String CHINESE_CHARACTERS = "[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]";

	/**
	 * 匹配 writeup 中的图片
	 *
	 * @see String
	 */
	String WRITEUP_IMG = "!\\[[^]]*]\\(.*%s([0-9a-z]{%d})\\.(?:jpg|jpeg|png|bmp|webp|gif)\\)";

	/**
	 * 匹配生成的 uuid
	 *
	 * @see String
	 */
	String RESOURCE_ID = "\"([a-z0-9]{%d})\"";

	/**
	 * 资管资源题解的图片
	 *
	 * @see String
	 */
	String WRITEUP_IMG_MD = "!\\[[^]]*]\\((\\./%s/[0-9]+\\.(?:jpg|jpeg|png|bmp|webp|gif))\\)";

	/**
	 * 理论题导入关键字分隔符
	 *
	 * @see String
	 */
	String VERTICAL_LINE = "\\|";

	/**
	 * `代码块`｜``代码块``
	 *
	 * @see String
	 */
	String SINGLE_CODING_BLOCK = "`{1,2}[^`](.*?)`{1,2}";

	/**
	 * ```代码块```
	 *
	 * @see String
	 */
	String MULTIPLE_CODING_BLOCK = "```([\\s\\S]*?)```[\\s]?";

	/**
	 * 一级列表
	 *
	 * @see String
	 */
	String MD_LIST = "- ";

	/**
	 * 二级列表
	 *
	 * @see String
	 */
	String SECOND_MD_LIST = "  - ";

	/**
	 * 选择题答案匹配
	 *
	 * @see String
	 */
	String CHOICE_ANSWER = "^[a-zA-Z|]+$";

	/**
	 * 判断题答案
	 *
	 * @see String
	 */
	String JUDGEMENT_ANSWER = "^[a-zA-Z]$";

	/**
	 * 匹配markdown文件中的链接
	 *
	 * @see String
	 */
	String MARKDOWN_URL = "[^!]\\[.*?\\]\\(.+?\\)";

	/**
	 * 匹配<p></p>标签
	 *
	 * @see String
	 */
	String HTML_P = "<p>(?:(?!<\\/p>)[\\s\\S])*<\\/p>";

	/**
	 * 小写字母
	 *
	 * @see String
	 */
	String LOWER_CASE_LETTER = "[a-z]";
}

