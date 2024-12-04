package com.cyberpeace.cr.crbase.constant;

/**
 * 靶场常用常量
 *
 * @author SN
 * @date 2024/11/11
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

	/**
	 * 生成 UUID 长度
	 */
	public static final Integer GENERATED_UUID_LENGTH = 32;

	/**
	 * 中台场景id钥匙
	 */
	public static final String MP_SCENE_ID_KEY = "resource_id";

	/**
	 * 拓扑数据钥匙
	 */
	public static final String TOPO_DATA_KEY = "scene_config";

	/**
	 * 学员岗位
	 */
	public static final Integer STUDENT_ROLE = 1;

	/**
	 * 终端类型代号
	 */
	public static final Integer TERMINAL_TYPE_CODE = 4;

	/**
	 * 评分界面，教员主观打分的指标项数
	 */
	public static final Integer SCORE_ABILITY_SIZE = 5;

	/**
	 * 评分界面，每颗星代表多少分
	 */
	public static final Integer SCORE_PER_STAR = 4;

	/**
	 * 技能点大小
	 */
	public static final int SKILL_POINTS_SIZE = ConfigReader.readInt("cr.skill.point.size");

	/**
	 * 技能点最小值
	 */
	public static final int SKILL_POINT_MIN_VALUE = ConfigReader.readInt("cr.skill.point.min");

	/**
	 * 技能点最大值
	 */
	public static final int SKILL_POINT_MAX_VALUE = ConfigReader.readInt("cr.skill.point.max");

	/**
	 * 最小阶段名称长度
	 */
	public static final int MIN_STAGE_NAME_LEN = ConfigReader.readInt("cr.stage.name.len.min");

	/**
	 * 最大阶段名称长度
	 */
	public static final int MAX_STAGE_NAME_LEN = ConfigReader.readInt("cr.stage.name.len.max");

	/**
	 * 得分最大值
	 */
	public static final int SCORE_MAX_VALUE = ConfigReader.readInt("cr.score.max");

	/**
	 * 最小训练名称长度
	 */
	public static final int MIN_TRAINING_NAME_LEN = ConfigReader
			.readInt("cr.training.name.len.min");

	/**
	 * 最大训练名称长度
	 */
	public static final int MAX_TRAINING_NAME_LEN = ConfigReader
			.readInt("cr.training.name.len.max");

	/**
	 * 零
	 */
	public static final String NULL = "null";

	/**
	 * 最小标记计数
	 */
	public static final Integer MIN_TAG_COUNT = ConfigReader.readInt("cr.tag.count.min");

	/**
	 * 最大标记计数
	 */
	public static final Integer MAX_TAG_COUNT = ConfigReader.readInt("cr.tag.count.max");

	/**
	 * 零强度
	 */
	public static final Integer ZERO_STR_LEN = ConfigReader.readInt("cr.str.len.zero");

	/**
	 * 最小str len
	 */
	public static final Integer MIN_STR_LEN = ConfigReader.readInt("cr.str.len.min");

	/**
	 * 最大名称str长度
	 */
	public static final Integer MAX_NAME_STR_LEN = ConfigReader.readInt("cr.str.len.name.max");

	/**
	 * 最大文本长度
	 */
	public static final Integer MAX_TEXT_LEN = ConfigReader.readInt("cr.text.len.max");

	/**
	 * Max Option str len
	 */
	public static final Integer MAX_OPTION_STR_LEN = ConfigReader.readInt("cr.option.len.max");

	/**
	 * 最大描述长度
	 */
	public static final Integer MAX_DESC_LEN = ConfigReader.readInt("cr.desc.len.max");

	/**
	 * 最小技能权重
	 */
	public static final Integer MIN_SKILL_WEIGHT = ConfigReader.readInt("rtks.skill.weight.min");

	/**
	 * 最大技能权重
	 */
	public static final Integer MAX_SKILL_WEIGHT = ConfigReader.readInt("rtks.skill.weight.max");

	/**
	 * 最小任务重量
	 */
	public static final Integer MIN_MISSION_WEIGHT = ConfigReader
			.readInt("rtks.mission.weight.min");

	/**
	 * 最大任务重量
	 */
	public static final Integer MAX_MISSION_WEIGHT = ConfigReader
			.readInt("rtks.mission.weight.max");

	/**
	 * AHP一致性id
	 */
	public static final String AHP_CONSISTENCY_ID = "230ced924ec485dd37c3945b4d4c13d4";

	/**
	 * 团队组头像
	 */
	public static final String TEAM_GROUP_AVATAR = "/cpms/upload/images/e4abbc7f396c46958f5b599a3750645a.png";

	/**
	 * 红组头像
	 */
	public static final String RED_GROUP_AVATAR = "/cpms/upload/images/1421794ca18b48c5a968cac38c1fbb34_v2.png";

	/**
	 * 蓝色组头像
	 */
	public static final String BLUE_GROUP_AVATAR = "/cpms/upload/images/bca4dfd40ca4446e8db80311f377c3a1_v1.png";

	/**
	 * 白色组头像
	 */
	public static final String WHITE_GROUP_AVATAR = "/cpms/upload/images/6df60173476149e9b26f11d84a64db1e_v1.png";

	/**
	 * 团队组 jar 头像
	 */
	public static final String TEAM_GROUP_JAR_AVATAR = "teamtrain/team_group_avatar.png";

	/**
	 * Red Group Jar 头像
	 */
	public static final String RED_GROUP_JAR_AVATAR = "redvsblue/red_group_avatar_v1.png";

	/**
	 * 蓝色组 jar 头像
	 */
	public static final String BLUE_GROUP_JAR_AVATAR = "redvsblue/blue_group_avatar_v1.png";

	/**
	 * 白色群组 jar 头像
	 */
	public static final String WHITE_GROUP_JAR_AVATAR = "redvsblue/white_group_avatar_v1.png";

	/**
	 * 靶场常用常数
	 */
	private CrCommonConstants() {}

	/**
	 * 每分页
	 */
	public static final int PER_PAGE = 9999;

	public static final String TIME_FORMATION = "yyyy-MM-dd HH:mm:ss";

}
