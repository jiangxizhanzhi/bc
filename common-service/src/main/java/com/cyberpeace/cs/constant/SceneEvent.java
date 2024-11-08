package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 场景事件
 *
 * @author SN
 * @date 2024/11/08
 */
public enum SceneEvent implements GenericEnum {

	/**
	 * 违约
	 */
	DEFAULT(0, "默认"),
	/**
	 * 初始化
	 */
	INITIALIZE(1, "初始化"),
	/**
	 * 创造
	 */
	CREATE(2, "创建"),
	/**
	 * 开始
	 */
	START(3, "启动"),
	/**
	 * 停
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

	/**
	 * 代号
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	SceneEvent(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
