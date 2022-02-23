package com.cyberpeace.rtks.constant;

/**
 *
 * 靶场自定义错误码。
 *  1、从130001开始递增编号；
 *  2、截止编号地址130999；
 *
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 * wiki地址：http://10.10.10.11/pages/viewpage.action?pageId=27367377
 *
 */
public interface RtksBusinessErrorCode {

	/** RTKS框架管理中，当前层级下存在角色的时候，用户试图删除该层级*/
	String UNABLE_DELETE_FRAME = "130001";

	/** 已经绑定了岗位的层级不允许新增子层级*/
	String UNABLE_ADD_FRAME = "130002";

	/** 已经绑定了知识点，无法删除*/
	String UNABLE_DELETE_KNOWLEDGE = "130003";

	/** 已经绑定了技能点，无法删除*/
	String UNABLE_DELETE_SKILL = "130004";

	/** 未检索到相应岗位 */
	String ROLE_NOT_EXIST = "130005";

	/** 技能不存在 */
	String SKILL_NOT_EXIST = "130006";

	/** 知识点不存在 */
	String KNOWLEDGE_NOT_EXIST = "130007";

	/** 框架/层级不存在 */
	String LEVEL_NOT_EXIST = "130008";

	/** 能力不存在 */
	String ABILITY_NOT_EXIST = "130009";

	/** 类别体系不存在 */
	String CATEGORY_NOT_EXIST = "130010";

	/** 职责不存在 */
	String TASK_NOT_EXIST = "130011";

	/** 框架/层级不合法 */
	String INVALID_LEVEL = "130012";
}