package com.cyberpeace.oj.ojbase.constant;

/**
 * 靶场自定义错误码。
 *  1、从102001开始递增编号；
 *  2、截止编号地址102999；
 */
public interface OjBusinessErrorCode {

	/** 对应的课程资源未找到*/
	String COURSE_NOT_FIND = "102001";

	/** 对应的课时资源未找到*/
	String LESSON_NOT_FIND = "102002";

	/** 习题集未找到*/
	String PRACTICE_SET_NOT_FIND = "102006";

	/** 关联的分类资源未找到*/
	String RELATE_CATEGORY_NOT_FIND = "102003";

	/** 资源无权限*/
	String RESOURCE_PERMISSION = "102004";

	/** 参数数据与数据库数据不一致*/
	String PARAM_DATABASE_ERROR = "102005";

	/** 实践操作习题找不到*/
	String OPERATION_NOT_FIND = "102007";

	/** 理论基础习题找不到*/
	String THEORY_NOT_FIND = "102008";

}
