package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 场景事件状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum SceneEventStatus implements GenericEnum {

	/**
	 * 初始化
	 *
	 * @see SceneEventStatus
	 */
	INITIALIZE(0, "初始化"),
	/**
	 * 开始
	 *
	 * @see SceneEventStatus
	 */
	START(1, "开始"),
	/**
	 * 过程
	 *
	 * @see SceneEventStatus
	 */
	PROCESS(2, "进行中"),
	/**
	 * 暂停
	 *
	 * @see SceneEventStatus
	 */
	PAUSE(3, "暂停"),
	/**
	 * 完成
	 *
	 * @see SceneEventStatus
	 */
	FINISH(4, "完成"),
	/**
	 * 失败
	 *
	 * @see SceneEventStatus
	 */
	FAILED(5, "失败");

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

	SceneEventStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
