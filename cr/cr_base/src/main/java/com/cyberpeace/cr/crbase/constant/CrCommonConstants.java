package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.common.util.ConfigUtil;

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

	int SKILL_POINTS_SIZE = new Integer(ConfigUtil.getContextProperty("cr.skill.point.size"));

	int SKILL_POINT_MIN_VALUE = new Integer(ConfigUtil.getContextProperty("cr.skill.point.min"));

	int SKILL_POINT_MAX_VALUE = new Integer(ConfigUtil.getContextProperty("cr.skill.point.max"));

	int MIN_STAGE_NAME_LEN = new Integer(ConfigUtil.getContextProperty("cr.stage.name.len.min"));

	int MAX_STAGE_NAME_LEN = new Integer(ConfigUtil.getContextProperty("cr.stage.name.len.max"));

	int SCORE_MAX_VALUE = new Integer(ConfigUtil.getContextProperty("cr.score.max"));

	int MIN_TRAINING_NAME_LEN = new Integer(ConfigUtil.getContextProperty("cr.training.name.len.min"));

	int MAX_TRAINING_NAME_LEN = new Integer(ConfigUtil.getContextProperty("cr.training.name.len.max"));

	String NULL = "null";

	Integer MIN_TAG_COUNT = new Integer(ConfigUtil.getContextProperty("cr.tag.count.min"));

	Integer MAX_TAG_COUNT = new Integer(ConfigUtil.getContextProperty("cr.tag.count.max"));

	Integer MIN_STR_LEN = new Integer(ConfigUtil.getContextProperty("cr.str.len.min"));

	Integer MAX_NAME_STR_LEN = new Integer(ConfigUtil.getContextProperty("cr.str.len.name.max"));

	Integer MAX_DESC_LEN = new Integer(ConfigUtil.getContextProperty("cr.desc.len.max"));
}
