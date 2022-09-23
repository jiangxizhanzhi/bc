package com.cyberpeace.common.constant;

import java.io.File;

public interface CommonConstant {

	/** 常量定义 便于后续查找代码中默认值使用*/

	/** 分类的code分割符*/
	String CODE_COMMA = ",";

	String PERIOD = ".";

	/** 空字符串*/
	String BLANK_STR = "\"\"";

	/** 竖线分割符*/
	String VERTICAL_LINE = "|";

	String UNDER_LINE = "_";

	String SHORT_TRANSVERSE_LINE = "-";

	String LEFT_OBLIQUE_LINE = "/";

	Integer ZERO = 0;

	Integer ONE = 1;

	int TWO = 2;

	int THREE = 3;

	int FOUR = 4;

	int FIVE = 5;

	int SIX = 6;

	int NEGATIVE_ONE = -1;

	Integer HUNDRED = 100;

	Integer NINE_NINE_NINE = 999;

	Integer SIXTY = 60;

	Integer EIGHTY = 80;

	Integer NINETY = 90;

	Integer NINE = 9;

	int EIGHT = 8;

	int TEN = 10;

	int FOURTEEN = 14;

	int FIFTEEN = 15;

	int SIXTEEN = 16;

	Boolean TRUE = true;

	Boolean FALSE = false;

	String EMPTY_LIST_JSON = "[]";

	String EMPTY_OBJECT = "{}";

	String RIGHT = "是";

	String NO = "否";

	String EMPTY_STRING = "";

	String MD_CODING_BLOCK = "```";

	String MD_CODING_BLOCK_ONE = "`";

	String MD_CODING_SINGLE_BLOCK = "`";

	String MD_CODING_TWO_BLOCK = "``";

	String ONLINE = "online";

	Integer THIRTY = 30;

	Integer TWENTY_FOUR_MONTHS = 24 * 30;

	String UTF_8 = "UTF-8";

	String ADMIN_ID = "3f655d87112f4ae094bbaf70830e20aa";

	String SPACE = " ";

	long TOOL_ATTACHMENT_FILE_SIZE = 500 * 1024 * 1024;

	/** 底层 中台 靶场 竞赛共享目录*/
	String SHARE_DIR = "/data/resource/export/";

	String EXPORT_CTF_PATH = SHARE_DIR + "CTF";

	String EXPORT_TRAIN_PATH = SHARE_DIR + "train/";

	String MD_IMAGE_TEMPLATE = "![](%s)";

	String UPDATE_WAR_NAME = "bc.war";

	String WAR_FILE_NAME = "ojj.war";

	String WAR_FILE_PATH = "/usr/local/tomcat/webapps/" + WAR_FILE_NAME;

	String APPS_FILE_PATH = "/home/apps";

	String PAGES_FILE_PATH = APPS_FILE_PATH + File.separator + "pages";

	String BACKUP_PAGES_NAME = "pages.tar.gz";

	String UPDATE_SCRIPT_NAME = "update.sh";

	String UPDATE_SCRIPT_LOG = "update.log";

}
