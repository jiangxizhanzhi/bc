package com.cyberpeace.common.constant;

/**
 * 公共常数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface CommonConstant {

	/** 常量定义 便于后续查找代码中默认值使用*/

	/**
	 * 分类的code分割符
	 *
	 * @see String
	 */
	String CODE_COMMA = ",";

	/**
	 * 时期
	 *
	 * @see String
	 */
	String PERIOD = ".";

	/**
	 * 左经期斜线
	 *
	 * @see String
	 */
	String LEFT_PERIOD_OBLIQUE_LINE = "./";

	/**
	 * 空字符串
	 *
	 * @see String
	 */
	String BLANK_STR = "\"\"";

	/**
	 * 竖线分割符
	 *
	 * @see String
	 */
	String VERTICAL_LINE = "|";

	/**
	 * 下线
	 *
	 * @see String
	 */
	String UNDER_LINE = "_";

	/**
	 * 短横线
	 *
	 * @see String
	 */
	String SHORT_TRANSVERSE_LINE = "-";

	/**
	 * 左斜线
	 *
	 * @see String
	 */
	String LEFT_OBLIQUE_LINE = "/";

	/**
	 * 零
	 *
	 * @see Integer
	 */
	Integer ZERO = 0;

	/**
	 * 一
	 *
	 * @see Integer
	 */
	Integer ONE = 1;

	/**
	 * 二
	 */
	int TWO = 2;

	/**
	 * 三
	 */
	int THREE = 3;

	/**
	 * 四
	 */
	int FOUR = 4;

	/**
	 * 五
	 */
	int FIVE = 5;

	/**
	 * 六
	 */
	int SIX = 6;

	/**
	 * 负一
	 */
	int NEGATIVE_ONE = -1;

	/**
	 * 百
	 *
	 * @see Integer
	 */
	Integer HUNDRED = 100;

	/**
	 * 九九九
	 *
	 * @see Integer
	 */
	Integer NINE_NINE_NINE = 999;

	/**
	 * 六十
	 *
	 * @see Integer
	 */
	Integer SIXTY = 60;

	/**
	 * 八十
	 *
	 * @see Integer
	 */
	Integer EIGHTY = 80;

	/**
	 * 九十
	 *
	 * @see Integer
	 */
	Integer NINETY = 90;

	/**
	 * 九
	 *
	 * @see Integer
	 */
	Integer NINE = 9;

	/**
	 * 八
	 */
	int EIGHT = 8;

	/**
	 * 十
	 */
	int TEN = 10;

	/**
	 * 十四
	 */
	int FOURTEEN = 14;

	/**
	 * 十五
	 */
	int FIFTEEN = 15;

	/**
	 * 十六
	 */
	int SIXTEEN = 16;

	/**
	 * 真
	 *
	 * @see Boolean
	 */
	Boolean TRUE = true;

	/**
	 * 假
	 *
	 * @see Boolean
	 */
	Boolean FALSE = false;

	/**
	 * 空列表 JSON
	 *
	 * @see String
	 */
	String EMPTY_LIST_JSON = "[]";

	/**
	 * 空对象
	 *
	 * @see String
	 */
	String EMPTY_OBJECT = "{}";

	/**
	 * 右
	 *
	 * @see String
	 */
	String RIGHT = "是";

	/**
	 * 不
	 *
	 * @see String
	 */
	String NO = "否";

	/**
	 * 空字符串
	 *
	 * @see String
	 */
	String EMPTY_STRING = "";

	/**
	 * MD 编码块
	 *
	 * @see String
	 */
	String MD_CODING_BLOCK = "```";

	/**
	 * MD 编码模块 1
	 *
	 * @see String
	 */
	String MD_CODING_BLOCK_ONE = "`";

	/**
	 * MD 编码单块
	 *
	 * @see String
	 */
	String MD_CODING_SINGLE_BLOCK = "`";

	/**
	 * MD 编码 2 块
	 *
	 * @see String
	 */
	String MD_CODING_TWO_BLOCK = "``";

	/**
	 * 在线
	 *
	 * @see String
	 */
	String ONLINE = "online";

	/**
	 * 三十
	 *
	 * @see Integer
	 */
	Integer THIRTY = 30;

	/**
	 * 二十四个月
	 *
	 * @see Integer
	 */
	Integer TWENTY_FOUR_MONTHS = 24 * 30;

	/**
	 * UTF 8
	 *
	 * @see String
	 */
	String UTF_8 = "UTF-8";

	/**
	 * 管理id
	 *
	 * @see String
	 */
	String ADMIN_ID = "3f655d87112f4ae094bbaf70830e20aa";

	/**
	 * 空间
	 *
	 * @see String
	 */
	String SPACE = " ";

	/**
	 * 返回
	 *
	 * @see String
	 */
	String RETURN = "\n";

	/**
	 * 工具附件文件大小
	 */
	long TOOL_ATTACHMENT_FILE_SIZE =  500 * 1024 * 1024;

	/**
	 * 底层 中台 靶场 竞赛共享目录
	 *
	 * @see String
	 */
	String SHARE_DIR = "/data/resource/export/";

	/**
	 * 导出 CTF 路径
	 *
	 * @see String
	 */
	String EXPORT_CTF_PATH = SHARE_DIR + "CTF";

	/**
	 * 导出训练路径
	 *
	 * @see String
	 */
	String EXPORT_TRAIN_PATH =  SHARE_DIR + "train/";

	/**
	 * md 映像模板
	 *
	 * @see String
	 */
	String MD_IMAGE_TEMPLATE = "![](%s)";

	/**
	 * 二百五十五
	 */
	int TWO_HUNDRED_AND_FIFTY_FIVE = 255;

	/**
	 * 反斜線
	 *
	 * @see String
	 */
	String BACKSLASH = "\\";

}
