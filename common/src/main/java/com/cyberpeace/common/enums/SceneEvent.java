package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum SceneEvent implements GenericEnum {

	DEFAULT(0, "默认"),
	INITIALIZE(1, "初始化"),
	CREATE(2, "创建"),
	START(3, "启动"),
	STOP(4, "停止"),
	RESTART(5, "重启"),
	PAUSE(6, "暂停"),
	RESUME(7, "恢复"),
	SNAPSHOOT(8, "快照"),
	CLOSE(9, "关闭"),
	UPDATE(10, "修改");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	SceneEvent(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
