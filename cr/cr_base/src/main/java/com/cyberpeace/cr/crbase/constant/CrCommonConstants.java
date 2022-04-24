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

	Integer TERMINAL_TYPE_CODE = 4;

	int SKILL_POINTS_SIZE = ConfigReader.readInt("cr.skill.point.size");

	int SKILL_POINT_MIN_VALUE = ConfigReader.readInt("cr.skill.point.min");

	int SKILL_POINT_MAX_VALUE = ConfigReader.readInt("cr.skill.point.max");

	int MIN_STAGE_NAME_LEN = ConfigReader.readInt("cr.stage.name.len.min");

	int MAX_STAGE_NAME_LEN = ConfigReader.readInt("cr.stage.name.len.max");

	int SCORE_MAX_VALUE = ConfigReader.readInt("cr.score.max");

	int MIN_TRAINING_NAME_LEN = ConfigReader.readInt("cr.training.name.len.min");

	int MAX_TRAINING_NAME_LEN = ConfigReader.readInt("cr.training.name.len.max");

	String NULL = "null";

	Integer MIN_TAG_COUNT = ConfigReader.readInt("cr.tag.count.min");

	Integer MAX_TAG_COUNT = ConfigReader.readInt("cr.tag.count.max");

	Integer MIN_STR_LEN = ConfigReader.readInt("cr.str.len.min");

	Integer MAX_NAME_STR_LEN = ConfigReader.readInt("cr.str.len.name.max");

	Integer MAX_DESC_LEN = ConfigReader.readInt("cr.desc.len.max");

	Integer TITLE_MIN_SCORE = ConfigReader.readInt("cr.title.score.min");

	Integer TITLE_MAX_SCORE = ConfigReader.readInt("cr.title.score.max");

	Integer TITLE_NAME_MAX_LEN = ConfigReader.readInt("cr.title.title.len.max");

	Integer LEVEL_NAME_MAX_LEN = ConfigReader.readInt("cr.title.level.len.max");

	Integer COMMENT_MAX_LEN = ConfigReader.readInt("cr.comment.len.max");
}
