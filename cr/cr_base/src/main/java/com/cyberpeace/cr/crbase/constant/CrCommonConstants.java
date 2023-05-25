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

	Integer ZERO_STR_LEN = ConfigReader.readInt("cr.str.len.zero");

	Integer MIN_STR_LEN = ConfigReader.readInt("cr.str.len.min");

	Integer MAX_NAME_STR_LEN = ConfigReader.readInt("cr.str.len.name.max");

	Integer MAX_TEXT_LEN = ConfigReader.readInt("cr.text.len.max");

	Integer MAX_OPTION_STR_LEN = ConfigReader.readInt("cr.option.len.max");

	Integer MAX_DESC_LEN = ConfigReader.readInt("cr.desc.len.max");

	Integer MIN_SKILL_WEIGHT = ConfigReader.readInt("rtks.skill.weight.min");

	Integer MAX_SKILL_WEIGHT = ConfigReader.readInt("rtks.skill.weight.max");

	Integer MIN_MISSION_WEIGHT = ConfigReader.readInt("rtks.mission.weight.min");

	Integer MAX_MISSION_WEIGHT = ConfigReader.readInt("rtks.mission.weight.max");

	String AHP_CONSISTENCY_ID = "230ced924ec485dd37c3945b4d4c13d4";

	String TEAM_GROUP_AVATAR = "/cpms/upload/images/e4abbc7f396c46958f5b599a3750645a.png";

	String RED_GROUP_AVATAR = "/cpms/upload/images/1421794ca18b48c5a968cac38c1fbb34.png";

	String BLUE_GROUP_AVATAR = "/cpms/upload/images/bca4dfd40ca4446e8db80311f377c3a1.png";

	String WHITE_GROUP_AVATAR = "/cpms/upload/images/6df60173476149e9b26f11d84a64db1e.png";

	String TEAM_GROUP_JAR_AVATAR = "teamtrain/team_group_avatar.png";

	String RED_GROUP_JAR_AVATAR = "redvsblue/red_group_avatar.png";

	String BLUE_GROUP_JAR_AVATAR = "redvsblue/blue_group_avatar.png";

	String WHITE_GROUP_JAR_AVATAR = "redvsblue/white_group_avatar.png";

}
