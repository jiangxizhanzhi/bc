package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 场景事件
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum SceneEvent implements GenericEnum {

	/**
	 * 默认
	 *
	 * @see SceneEvent
	 */
	DEFAULT(0, "默认"),
	/**
	 * 初始化
	 *
	 * @see SceneEvent
	 */
	INITIALIZE(1, "初始化"),
	/**
	 * 创造
	 *
	 * @see SceneEvent
	 */
	CREATE(2, "创建"),
	/**
	 * 开始
	 *
	 * @see SceneEvent
	 */
	START(3, "启动"),
	/**
	 * 停
	 *
	 * @see SceneEvent
	 */
	STOP(4, "停止"),
	/**
	 * 重新启动
	 *
	 * @see SceneEvent
	 */
	RESTART(5, "重启"),
	/**
	 * 暂停
	 *
	 * @see SceneEvent
	 */
	PAUSE(6, "暂停"),
	/**
	 * 恢复
	 *
	 * @see SceneEvent
	 */
	RESUME(7, "恢复"),
	/**
	 * 快照
	 *
	 * @see SceneEvent
	 */
	SNAPSHOOT(8, "快照"),
	/**
	 * 关闭
	 *
	 * @see SceneEvent
	 */
	CLOSE(9, "关闭"),
	/**
	 * 更新
	 *
	 * @see SceneEvent
	 */
	UPDATE(10, "修改");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	SceneEvent(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
