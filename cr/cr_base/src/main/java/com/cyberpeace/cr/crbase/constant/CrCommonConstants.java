package com.cyberpeace.cr.crbase.constant;

/**
 * 靶场常用常量
 */
public interface CrCommonConstants {

	/**
	 * 场景模板导入导出的数据文件名
	 */
	String RESOURCE_FILE_NAME = "data.json";

	/**
	 * 场景模板资源导入导出包图片文件夹名
	 */
	String RESOURCE_IMAGE_DIR = "images";

	Integer GENERATED_UUID_LENGTH = 32;

	String MP_SCENE_ID_KEY = "resource_id";

	String TOPO_DATA_KEY = "scene_config";

	Integer STUDENT_ROLE = 1;

	int MIN_STAGE_NAME_LEN = 4;

	int MAX_STAGE_NAME_LEN = 20;

	int MIN_MISSION_NAME_LEN = 1;

	int MAX_MISSION_NAME_LEN = 255;

	int SCORE_MAX_VALUE = 500;

	int SKILL_POINTS_SIZE = 6;

	int SKILL_POINT_MIN_VALUE = 1;

	int SKILL_POINT_MAX_VALUE = 5;
}
