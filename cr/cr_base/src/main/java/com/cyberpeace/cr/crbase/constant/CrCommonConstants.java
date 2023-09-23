package com.cyberpeace.cr.crbase.constant;

/**
 * 靶场常用常量
 */
public final class CrCommonConstants {

	/**
	 * 场景模板导入导出的数据文件名
	 */
	public static final String RESOURCE_FILE_NAME = "data.json";

	/**
	 * 场景模板资源导入导出包图片文件夹名
	 */
	public static final String RESOURCE_IMAGE_DIR = "images";

	public static final Integer GENERATED_UUID_LENGTH = 32;

	public static final String MP_SCENE_ID_KEY = "resource_id";

	public static final String TOPO_DATA_KEY = "scene_config";

	public static final Integer STUDENT_ROLE = 1;

	public static final Integer TERMINAL_TYPE_CODE = 4;

	/**
	 * 评分界面，教员主观打分的指标项数
	 */
	public static final Integer SCORE_ABILITY_SIZE = 5;

	/**
	 * 评分界面，每颗星代表多少分
	 */
	public static final Integer SCORE_PER_STAR = 4;

	public static final int SKILL_POINTS_SIZE = ConfigReader.readInt("cr.skill.point.size");

	public static final int SKILL_POINT_MIN_VALUE = ConfigReader.readInt("cr.skill.point.min");

	public static final int SKILL_POINT_MAX_VALUE = ConfigReader.readInt("cr.skill.point.max");

	public static final int MIN_STAGE_NAME_LEN = ConfigReader.readInt("cr.stage.name.len.min");

	public static final int MAX_STAGE_NAME_LEN = ConfigReader.readInt("cr.stage.name.len.max");

	public static final int SCORE_MAX_VALUE = ConfigReader.readInt("cr.score.max");

	public static final int MIN_TRAINING_NAME_LEN = ConfigReader
			.readInt("cr.training.name.len.min");

	public static final int MAX_TRAINING_NAME_LEN = ConfigReader
			.readInt("cr.training.name.len.max");

	public static final String NULL = "null";

	public static final Integer MIN_TAG_COUNT = ConfigReader.readInt("cr.tag.count.min");

	public static final Integer MAX_TAG_COUNT = ConfigReader.readInt("cr.tag.count.max");

	public static final Integer ZERO_STR_LEN = ConfigReader.readInt("cr.str.len.zero");

	public static final Integer MIN_STR_LEN = ConfigReader.readInt("cr.str.len.min");

	public static final Integer MAX_NAME_STR_LEN = ConfigReader.readInt("cr.str.len.name.max");

	public static final Integer MAX_TEXT_LEN = ConfigReader.readInt("cr.text.len.max");

	public static final Integer MAX_OPTION_STR_LEN = ConfigReader.readInt("cr.option.len.max");

	public static final Integer MAX_DESC_LEN = ConfigReader.readInt("cr.desc.len.max");

	public static final Integer MIN_SKILL_WEIGHT = ConfigReader.readInt("rtks.skill.weight.min");

	public static final Integer MAX_SKILL_WEIGHT = ConfigReader.readInt("rtks.skill.weight.max");

	public static final Integer MIN_MISSION_WEIGHT = ConfigReader
			.readInt("rtks.mission.weight.min");

	public static final Integer MAX_MISSION_WEIGHT = ConfigReader
			.readInt("rtks.mission.weight.max");

	public static final String AHP_CONSISTENCY_ID = "230ced924ec485dd37c3945b4d4c13d4";

	public static final String TEAM_GROUP_AVATAR = "/cpms/upload/images/e4abbc7f396c46958f5b599a3750645a.png";

	public static final String RED_GROUP_AVATAR = "/cpms/upload/images/1421794ca18b48c5a968cac38c1fbb34_v2.png";

	public static final String BLUE_GROUP_AVATAR = "/cpms/upload/images/bca4dfd40ca4446e8db80311f377c3a1_v1.png";

	public static final String WHITE_GROUP_AVATAR = "/cpms/upload/images/6df60173476149e9b26f11d84a64db1e_v1.png";

	public static final String TEAM_GROUP_JAR_AVATAR = "teamtrain/team_group_avatar.png";

	public static final String RED_GROUP_JAR_AVATAR = "redvsblue/red_group_avatar_v1.png";

	public static final String BLUE_GROUP_JAR_AVATAR = "redvsblue/blue_group_avatar_v1.png";

	public static final String WHITE_GROUP_JAR_AVATAR = "redvsblue/white_group_avatar_v1.png";

	private CrCommonConstants() {}

}
