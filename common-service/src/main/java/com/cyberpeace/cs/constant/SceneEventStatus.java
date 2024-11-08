package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 场景事件状态
 *
 * @author SN
 * @date 2024/11/08
 */
public enum SceneEventStatus implements GenericEnum {

	/**
	 * 初始化
	 */
	INITIALIZE(0, "初始化"),
	/**
	 * 开始
	 */
	START(1, "开始"),
	/**
	 * 过程
	 */
	PROCESS(2, "进行中"),
	/**
	 * 暂停
	 */
	PAUSE(3, "暂停"),
	/**
	 * 完成
	 */
	FINISH(4, "完成"),
	/**
	 * 失败
	 */
	FAILED(5, "失败");

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

	SceneEventStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
