package com.cyberpeace.common.constant;

/**
 * 公共常数
 *
 * @author SN
 * @date 2024/11/07
 */
public interface CommonConstant {

	/** 常量定义 便于后续查找代码中默认值使用*/

	/**
	 * 分类的code分割符
	 */
	String CODE_COMMA = ",";

	/**
	 * 时期
	 */
	String PERIOD = ".";

	/**
	 * 左经期斜线
	 */
	String LEFT_PERIOD_OBLIQUE_LINE = "./";

	/**
	 * 空字符串
	 */
	String BLANK_STR = "\"\"";

	/**
	 * 竖线分割符
	 */
	String VERTICAL_LINE = "|";

	/**
	 * 下线
	 */
	String UNDER_LINE = "_";

	/**
	 * 短横线
	 */
	String SHORT_TRANSVERSE_LINE = "-";

	/**
	 * 左斜线
	 */
	String LEFT_OBLIQUE_LINE = "/";

	/**
	 * 零
	 */
	Integer ZERO = 0;

	/**
	 * 一
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
	 */
	Integer HUNDRED = 100;

	/**
	 * 九九九
	 */
	Integer NINE_NINE_NINE = 999;

	/**
	 * 六十
	 */
	Integer SIXTY = 60;

	/**
	 * 八十
	 */
	Integer EIGHTY = 80;

	/**
	 * 九十
	 */
	Integer NINETY = 90;

	/**
	 * 九
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
	 */
	Boolean TRUE = true;

	/**
	 * 假
	 */
	Boolean FALSE = false;

	/**
	 * 空列表 JSON
	 */
	String EMPTY_LIST_JSON = "[]";

	/**
	 * 空对象
	 */
	String EMPTY_OBJECT = "{}";

	/**
	 * 右
	 */
	String RIGHT = "是";

	/**
	 * 不
	 */
	String NO = "否";

	/**
	 * 空字符串
	 */
	String EMPTY_STRING = "";

	/**
	 * MD 编码块
	 */
	String MD_CODING_BLOCK = "```";

	/**
	 * MD 编码模块 1
	 */
	String MD_CODING_BLOCK_ONE = "`";

	/**
	 * MD 编码单块
	 */
	String MD_CODING_SINGLE_BLOCK = "`";

	/**
	 * MD 编码 2 块
	 */
	String MD_CODING_TWO_BLOCK = "``";

	/**
	 * 在线
	 */
	String ONLINE = "online";

	/**
	 * 三十
	 */
	Integer THIRTY = 30;

	/**
	 * 二十四个月
	 */
	Integer TWENTY_FOUR_MONTHS = 24 * 30;

	/**
	 * UTF 8
	 */
	String UTF_8 = "UTF-8";

	/**
	 * 管理id
	 */
	String ADMIN_ID = "3f655d87112f4ae094bbaf70830e20aa";

	/**
	 * 空间
	 */
	String SPACE = " ";

	/**
	 * 返回
	 */
	String RETURN = "\n";

	/**
	 * 工具附件文件大小
	 */
	long TOOL_ATTACHMENT_FILE_SIZE =  500 * 1024 * 1024;

	/**
	 * 底层 中台 靶场 竞赛共享目录
	 */
	String SHARE_DIR = "/data/resource/export/";

	/**
	 * 导出 CTF 路径
	 */
	String EXPORT_CTF_PATH = SHARE_DIR + "CTF";

	/**
	 * 出口训练路径
	 */
	String EXPORT_TRAIN_PATH =  SHARE_DIR + "train/";

	/**
	 * md 映像模板
	 */
	String MD_IMAGE_TEMPLATE = "![](%s)";

	/**
	 * 二百五十五
	 */
	int TWO_HUNDRED_AND_FIFTY_FIVE = 255;

	/**
	 * 反斜線
	 */
	String BACKSLASH = "\\";

}
