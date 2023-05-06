package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 现场活动
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum SceneEvent implements GenericEnum {

	/**
	 * 默认
	 */
	DEFAULT(0, "默认"),
	/**
	 * 初始化
	 */
	INITIALIZE(1, "初始化"),
	/**
	 * 创建
	 */
	CREATE(2, "创建"),
	/**
	 * 开始
	 */
	START(3, "启动"),
	/**
	 * 停止
	 */
	STOP(4, "停止"),
	/**
	 * 重新启动
	 */
	RESTART(5, "重启"),
	/**
	 * 暂停
	 */
	PAUSE(6, "暂停"),
	/**
	 * 恢复
	 */
	RESUME(7, "恢复"),
	/**
	 * 快照
	 */
	SNAPSHOOT(8, "快照"),
	/**
	 * 关闭
	 */
	CLOSE(9, "关闭"),
	/**
	 * 更新
	 */
	UPDATE(10, "修改");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	/**
	 * 场景事件
	 *
	 * @param code 代码
	 * @param desc desc
	 */
	SceneEvent(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
