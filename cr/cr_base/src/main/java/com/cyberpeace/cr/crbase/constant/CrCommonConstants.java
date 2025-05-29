package com.cyberpeace.cr.crbase.constant;

/**
 * 靶场常用常量
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `CrCommonConstants` 的 Java 接口，用于存储靶场系统中的常用常量。以下是代码的主要分析和总结： 
 * ### 主要功能 
 * - **常量管理**：提供了一系列与靶场系统相关的常量，包括文件名、目录名、配置参数、字符串长度限制等。 
 * - **模块化设计**：通过接口的方式组织常量，便于统一管理和维护。 
 * ### 关键特性 
 * 1. **静态常量**： 
 * - 定义了多种类型的常量，如 `String`、`Integer` 和基本数据类型（如 `int`）。 
 * - 示例：`RESOURCE_FILE_NAME`、`GENERATED_UUID_LENGTH` 等。 
 * 2. **动态配置支持**： 
 * - 使用 `ConfigReader.readInt()` 方法从外部配置中读取部分常量值，例如 `SKILL_POINTS_SIZE`、`SCORE_MAX_VALUE` 等。 
 * - 这种方式使得常量值可以灵活调整，而无需直接修改代码。 
 * 3. **命名规范**： 
 * - 常量命名遵循驼峰命名法，并使用全大写字母（如 `MIN_TAG_COUNT`），符合 Java 编码规范。 
 * 4. **分组逻辑**： 
 * - 常量按照功能或用途分组，例如文件名相关、配置相关、图像路径相关等。 
 * - 示例：`RESOURCE_FILE_NAME` 和 `RESOURCE_IMAGE_DIR` 属于文件和目录相关；`RED_GROUP_AVATAR` 和 `BLUE_GROUP_AVATAR` 属于图像路径相关。 
 * 5. **国际化兼容性**： 
 * - 提供了一些默认值（如 `"null"`），可能用于处理空值场景。 
 * ### 可能存在的问题 
 * 1. **硬编码依赖**： 
 * - 部分常量依赖于外部配置文件（如 `ConfigReader`），如果配置文件丢失或内容错误，可能会导致运行时异常。 
 * 2. **扩展性**： 
 * - 如果需要新增常量，可以直接在接口中添加，但需注意避免命名冲突。 
 * 3. **冗余性**： 
 * - 某些常量（如 `TEAM_GROUP_AVATAR` 和 `TEAM_GROUP_JAR_AVATAR`）可能是重复定义，建议检查是否可以合并或优化。 
 * ### 总结 
 * 该代码是一个典型的常量管理类，结构清晰，功能明确，适合用于靶场系统的全局常量配置。通过引入动态配置机制，提高了灵活性和可维护性。建议在实际开发中继续优化命名和分组逻辑，确保代码的可读性和扩展性。 
**/
public interface CrCommonConstants {

	/**
	 * 场景模板导入导出的数据文件名
	 */
	String RESOURCE_FILE_NAME = "data.json";

	/**
	 * 场景模板资源导入导出包图片文件夹名
	 */
	String RESOURCE_IMAGE_DIR = "images";

	/**
	 * 生成 UUID 长度
	 */
	Integer GENERATED_UUID_LENGTH = 32;

	/**
	 * 中台场景id钥匙
	 */
	String MP_SCENE_ID_KEY = "resource_id";

	/**
	 * 拓扑数据钥匙
	 */
	String TOPO_DATA_KEY = "scene_config";

	/**
	 * 学员岗位
	 */
	Integer STUDENT_ROLE = 1;

	/**
	 * 终端类型代号
	 */
	Integer TERMINAL_TYPE_CODE = 4;

	/**
	 * 评分界面，教员主观打分的指标项数
	 */
	Integer SCORE_ABILITY_SIZE = 5;

	/**
	 * 评分界面，每颗星代表多少分
	 */
	Integer SCORE_PER_STAR = 4;

	/**
	 * 技能点大小
	 */
	int SKILL_POINTS_SIZE = ConfigReader.readInt("cr.skill.point.size");

	/**
	 * 技能点分钟值
	 */
	int SKILL_POINT_MIN_VALUE = ConfigReader.readInt("cr.skill.point.min");

	/**
	 * 技能点麦克斯值
	 */
	int SKILL_POINT_MAX_VALUE = ConfigReader.readInt("cr.skill.point.max");

	/**
	 * 分钟阶段名称莱恩
	 */
	int MIN_STAGE_NAME_LEN = ConfigReader.readInt("cr.stage.name.len.min");

	/**
	 * 麦克斯阶段名称莱恩
	 */
	int MAX_STAGE_NAME_LEN = ConfigReader.readInt("cr.stage.name.len.max");

	/**
	 * 得分麦克斯值
	 */
	int SCORE_MAX_VALUE = ConfigReader.readInt("cr.score.max");

	/**
	 * 分钟训练名称莱恩
	 */
	int MIN_TRAINING_NAME_LEN = ConfigReader.readInt("cr.training.name.len.min");

	/**
	 * 麦克斯训练名称莱恩
	 */
	int MAX_TRAINING_NAME_LEN = ConfigReader.readInt("cr.training.name.len.max");

	/**
	 * 零
	 */
	String NULL = "null";

	/**
	 * 分钟标记计数
	 */
	Integer MIN_TAG_COUNT = ConfigReader.readInt("cr.tag.count.min");

	/**
	 * 麦克斯标记计数
	 */
	Integer MAX_TAG_COUNT = ConfigReader.readInt("cr.tag.count.max");

	/**
	 * 零强度
	 */
	Integer ZERO_STR_LEN = ConfigReader.readInt("cr.str.len.zero");

	/**
	 * 最小str len
	 */
	Integer MIN_STR_LEN = ConfigReader.readInt("cr.str.len.min");

	/**
	 * 麦克斯名称str莱恩
	 */
	Integer MAX_NAME_STR_LEN = ConfigReader.readInt("cr.str.len.name.max");

	/**
	 * 最大文本长度
	 */
	Integer MAX_TEXT_LEN = ConfigReader.readInt("cr.text.len.max");

	/**
	 * Max Option str len
	 */
	Integer MAX_OPTION_STR_LEN = ConfigReader.readInt("cr.option.len.max");

	/**
	 * 麦克斯描述莱恩
	 */
	Integer MAX_DESC_LEN = ConfigReader.readInt("cr.desc.len.max");

	/**
	 * 最小技能权重
	 */
	Integer MIN_SKILL_WEIGHT = ConfigReader.readInt("rtks.skill.weight.min");

	/**
	 * 最大技能权重
	 */
	Integer MAX_SKILL_WEIGHT = ConfigReader.readInt("rtks.skill.weight.max");

	/**
	 * 最小任务重量
	 */
	Integer MIN_MISSION_WEIGHT = ConfigReader.readInt("rtks.mission.weight.min");

	/**
	 * 最大任务重量
	 */
	Integer MAX_MISSION_WEIGHT = ConfigReader.readInt("rtks.mission.weight.max");

	/**
	 * AHP一致性id
	 */
	String AHP_CONSISTENCY_ID = "230ced924ec485dd37c3945b4d4c13d4";

	/**
	 * 团队组头像
	 */
	String TEAM_GROUP_AVATAR = "/cpms/upload/images/e4abbc7f396c46958f5b599a3750645a.png";

	/**
	 * 红组头像
	 */
	String RED_GROUP_AVATAR = "/cpms/upload/images/1421794ca18b48c5a968cac38c1fbb34_v2.png";

	/**
	 * 蓝色组头像
	 */
	String BLUE_GROUP_AVATAR = "/cpms/upload/images/bca4dfd40ca4446e8db80311f377c3a1_v1.png";

	/**
	 * 白色组头像
	 */
	String WHITE_GROUP_AVATAR = "/cpms/upload/images/6df60173476149e9b26f11d84a64db1e_v1.png";

	/**
	 * 团队组 jar 头像
	 */
	String TEAM_GROUP_JAR_AVATAR = "teamtrain/team_group_avatar.png";

	/**
	 * Red Group Jar 头像
	 */
	String RED_GROUP_JAR_AVATAR = "redvsblue/red_group_avatar_v1.png";

	/**
	 * 蓝色组 jar 头像
	 */
	String BLUE_GROUP_JAR_AVATAR = "redvsblue/blue_group_avatar_v1.png";

	/**
	 * 白色群组 jar 头像
	 */
	String WHITE_GROUP_JAR_AVATAR = "redvsblue/white_group_avatar_v1.png";

}

