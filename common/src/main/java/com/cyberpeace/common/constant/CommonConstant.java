package com.cyberpeace.common.constant;

public final class CommonConstant {

	private CommonConstant() {}

	public static final String ORG = "Organization";

	public static final String INDEX_HTML = "index.html";

	/** 常量定义 便于后续查找代码中默认值使用*/

	/** 分类的code分割符*/
	public static final String CODE_COMMA = ",";

	public static final String PERIOD = ".";

	public static final String LEFT_PERIOD_OBLIQUE_LINE = "./";

	/** 空字符串*/
	public static final String BLANK_STR = "\"\"";

	/** 竖线分割符*/
	public static final String VERTICAL_LINE = "|";

	public static final String UNDER_LINE = "_";

	public static final String SHORT_TRANSVERSE_LINE = "-";

	public static final String LEFT_OBLIQUE_LINE = "/";

	public static final Integer ZERO = 0;

	public static final Integer ONE = 1;

	public static final int TWO = 2;

	public static final int THREE = 3;

	public static final int FOUR = 4;

	public static final int FIVE = 5;

	public static final int SIX = 6;

	public static final int NEGATIVE_ONE = -1;

	public static final Integer HUNDRED = 100;

	public static final Integer NINE_NINE_NINE = 999;

	public static final Integer SIXTY = 60;

	public static final Integer EIGHTY = 80;

	public static final Integer NINETY = 90;

	public static final Integer NINE = 9;

	public static final int EIGHT = 8;

	public static final int TEN = 10;

	public static final int FOURTEEN = 14;

	public static final int FIFTEEN = 15;

	public static final int SIXTEEN = 16;

	public static final Boolean TRUE = true;

	public static final Boolean FALSE = false;

	public static final String EMPTY_LIST_JSON = "[]";

	public static final String EMPTY_OBJECT = "{}";

	public static final String RIGHT = "是";

	public static final String NO = "否";

	public static final String EMPTY_STRING = "";

	public static final String MD_CODING_BLOCK = "```";

	public static final String MD_CODING_BLOCK_ONE = "`";

	public static final String MD_CODING_SINGLE_BLOCK = "`";

	public static final String MD_CODING_TWO_BLOCK = "``";

	public static final String ONLINE = "online";

	public static final Integer THIRTY = 30;

	public static final Integer TWENTY_FOUR_MONTHS = 24 * 30;

	public static final String UTF_8 = "UTF-8";

	public static final String ADMIN_ID = "3f655d87112f4ae094bbaf70830e20aa";

	public static final String SPACE = " ";

	public static final String RETURN = "\n";

	public static final long TOOL_ATTACHMENT_FILE_SIZE =  500 * 1024 * 1024L;

	/** 底层 中台 靶场 竞赛共享目录*/
	public static final String SHARE_DIR = "/data/resource/export/";

	public static final String EXPORT_CTF_PATH = SHARE_DIR + "CTF";

	public static final String EXPORT_TRAIN_PATH =  SHARE_DIR + "train/";

	public static final String MD_IMAGE_TEMPLATE = "![](%s)";

	public static final int TWO_HUNDRED_AND_FIFTY_FIVE = 255;

	public static final String BACKSLASH = "\\";

	public static final Integer ZERO_STR_LEN = ConfigReader.readInt("cr.str.len.zero");

	public static final Integer MIN_STR_LEN = ConfigReader.readInt("cr.str.len.min");

	public static final Integer MAX_NAME_STR_LEN = ConfigReader.readInt("cr.str.len.name.max");

	public static final Integer MAX_DESC_LEN = ConfigReader.readInt("cr.desc.len.max");

	public static final Integer MAX_TEXT_LEN = ConfigReader.readInt("cr.text.len.max");

	public static final Integer GENERATED_UUID_LENGTH = 32;

	public static final String AD_PREFIX = "ad_";
}
