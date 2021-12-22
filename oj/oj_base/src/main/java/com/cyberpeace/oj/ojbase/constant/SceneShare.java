package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum SceneShare implements GenericEnum {

	PRIVATE(1, "个人"), SHARED(2, "共享"), GROUP(3, "小组");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	SceneShare(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
