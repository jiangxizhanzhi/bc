package com.cyberpeace.rtks.constant;

/**
 * 靶场自定义错误码。
 * 1、从103001开始递增编号；
 * 2、截止编号地址103999；
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface RtksBusinessErrorCode {

	/**
	 * RTKS框架管理中，当前层级下存在角色的时候，用户试图删除该层级
	 *
	 * @see String
	 */
	String UNABLE_DELETE_FRAME = "103001";

	/**
	 * 已经绑定了岗位的层级不允许新增子层级
	 *
	 * @see String
	 */
	String UNABLE_ADD_FRAME = "103002";

	/**
	 * 已经绑定了知识点，无法删除
	 *
	 * @see String
	 */
	String UNABLE_DELETE_KNOWLEDGE = "103003";

	/**
	 * 已经绑定了技能点，无法删除
	 *
	 * @see String
	 */
	String UNABLE_DELETE_SKILL = "103004";

	/**
	 * 未检索到相应岗位
	 *
	 * @see String
	 */
	String ROLE_NOT_EXIST = "103005";

	/**
	 * 技能不存在
	 *
	 * @see String
	 */
	String SKILL_NOT_EXIST = "103006";

	/**
	 * 知识点不存在
	 *
	 * @see String
	 */
	String KNOWLEDGE_NOT_EXIST = "103007";

	/**
	 * 框架/层级不存在
	 *
	 * @see String
	 */
	String LEVEL_NOT_EXIST = "103008";

	/**
	 * 能力不存在
	 *
	 * @see String
	 */
	String ABILITY_NOT_EXIST = "103009";

	/**
	 * 类别体系不存在
	 *
	 * @see String
	 */
	String CATEGORY_NOT_EXIST = "103010";

	/**
	 * 职责不存在
	 *
	 * @see String
	 */
	String TASK_NOT_EXIST = "103011";

	/**
	 * 框架/层级不合法
	 *
	 * @see String
	 */
	String INVALID_LEVEL = "103012";

	/**
	 * 知识点类别已存在
	 *
	 * @see String
	 */
	String KNOWLEDGE_CATEGORY_NAME_DUPLICATE = "103013";

	/**
	 * 无效权重
	 *
	 * @see String
	 */
	String INVALID_WEIGHT = "103014";

	/**
	 * 时间格式错误
	 *
	 * @see String
	 */
	String TIME_FORMAT_ERROR = "103015";

	/**
	 * 输入课程错误
	 *
	 * @see String
	 */
	String INPUT_COURSE_ERROR = "103016";

	/**
	 * 单框架禁用
	 *
	 * @see String
	 */
	String SINGLE_FRAME_DISABLE = "103017";

	/**
	 * 多框架禁用
	 *
	 * @see String
	 */
	String MULTI_FRAME_DISABLE = "103018";

	/**
	 * 技能编号已被占用
	 *
	 * @see String
	 */
	String SKILL_ID_OCCUPIED = "103019";

	/**
	 * 技能编号过长
	 *
	 * @see String
	 */
	String INVALID_SKILL_ID = "103020";

	/**
	 * 技能内容过长
	 *
	 * @see String
	 */
	String INVALID_CONTENT = "103021";

	/**
	 * 只允许在二级层级关联岗位
	 *
	 * @see String
	 */
	String INVALID_ROLE_LEVEL = "103022";

	/**
	 * 层级" + levelId + "有子层级，不能绑定岗位
	 *
	 * @see String
	 */
	String LEVEL_HAS_CHILD = "103023";

	/**
	 * 岗位名称已存在
	 *
	 * @see String
	 */
	String ROLE_ALREADY_EXISTS = "103024";
}