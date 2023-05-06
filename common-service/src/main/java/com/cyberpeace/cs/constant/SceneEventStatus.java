package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 场景事件状态
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum SceneEventStatus implements GenericEnum {

	INITIALIZE(0, "初始化"), START(1, "开始"), PROCESS(2, "进行中"), PAUSE(3, "暂停"), FINISH(4,
			"完成"), FAILED(5, "失败");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	/**
	 * 场景事件状态
	 *
	 * @param code 代码
	 * @param desc desc
	 */
	SceneEventStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
