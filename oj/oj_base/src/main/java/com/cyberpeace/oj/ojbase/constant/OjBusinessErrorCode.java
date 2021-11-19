package com.cyberpeace.oj.ojbase.constant;

/**
 * 靶场自定义错误码。
 *  1、从102001开始递增编号；
 *  2、截止编号地址102999；
 */
public interface OjBusinessErrorCode {

	/** 资源无权限*/
	String RESOURCE_PERMISSION = "102004";

	/** 参数数据与数据库数据不一致*/
	String PARAM_DATABASE_ERROR = "102005";

	/** 文件上传错误*/
	String FILE_UPLOAD_ERROR = "102002";

	/** 资源未找到*/
	String RESOURCE_NOT_FIND = "102001";

}
