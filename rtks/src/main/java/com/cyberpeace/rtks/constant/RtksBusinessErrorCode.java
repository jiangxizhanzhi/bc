package com.cyberpeace.rtks.constant;

/**
 *
 * 靶场自定义错误码。
 *  1、从103001开始递增编号；
 *  2、截止编号地址103999；
 *
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 * wiki地址：http://10.10.10.11/pages/viewpage.action?pageId=27367377
 *
 */
public final class RtksBusinessErrorCode {

	/** RTKS框架管理中，当前层级下存在角色的时候，用户试图删除该层级*/
	public static final String UNABLE_DELETE_FRAME = "103001";

	/** 已经绑定了岗位的层级不允许新增子层级*/
	public static final String UNABLE_ADD_FRAME = "103002";

	/** 已经绑定了知识点，无法删除*/
	public static final String UNABLE_DELETE_KNOWLEDGE = "103003";

	/** 已经绑定了技能点，无法删除*/
	public static final String UNABLE_DELETE_SKILL = "103004";

	/** 未检索到相应岗位 */
	public static final String ROLE_NOT_EXIST = "103005";

	/** 技能不存在 */
	public static final String SKILL_NOT_EXIST = "103006";

	/** 知识点不存在 */
	public static final String KNOWLEDGE_NOT_EXIST = "103007";

	/** 框架/层级不存在 */
	public static final String LEVEL_NOT_EXIST = "103008";

	/** 能力不存在 */
	public static final String ABILITY_NOT_EXIST = "103009";

	/** 类别体系不存在 */
	public static final String CATEGORY_NOT_EXIST = "103010";

	/** 职责不存在 */
	public static final String TASK_NOT_EXIST = "103011";

	/** 框架/层级不合法 */
	public static final String INVALID_LEVEL = "103012";

	/** 知识点类别已存在 */
	public static final String KNOWLEDGE_CATEGORY_NAME_DUPLICATE = "103013";

	public static final String INVALID_WEIGHT = "103014";

	public static final String TIME_FORMAT_ERROR = "103015";

	public static final String INPUT_COURSE_ERROR = "103016";

	public static final String SINGLE_FRAME_DISABLE = "103017";

	public static final String MULTI_FRAME_DISABLE = "103018";

	/** 技能编号已被占用 */
	public static final String SKILL_ID_OCCUPIED = "103019";

	/** 技能编号过长 */
	public static final String INVALID_SKILL_ID = "103020";

	/** 技能内容过长 */
	public static final String INVALID_CONTENT = "103021";

	/** 只允许在二级层级关联岗位 */
	public static final String INVALID_ROLE_LEVEL = "103022";

	/** 层级" + levelId + "有子层级，不能绑定岗位 */
	public static final String LEVEL_HAS_CHILD = "103023";

	/** 岗位名称已存在 */
	public static final String ROLE_ALREADY_EXISTS = "103024";

	private RtksBusinessErrorCode() {}
}