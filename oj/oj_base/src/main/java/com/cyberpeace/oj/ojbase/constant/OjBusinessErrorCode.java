package com.cyberpeace.oj.ojbase.constant;

/**
 * 靶场自定义错误码。
 *  1、从102001开始递增编号；
 *  2、截止编号地址102999；
 */
public interface OjBusinessErrorCode {

	/** 资源无权限*/
	String RESOURCE_PERMISSION = "102004";

	/** 课堂场景已启动*/
	String GROUP_SCENE_IS_RUNNING = "102005";

	/** 课堂场景启动失败*/
	String GROUP_SCENE_START_FAIL = "102006";

}
