package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 目录划分
 */
public enum DumpModule implements GenericEnum {

	COURSE(0, "course"),
	CTF_PRACTICE(1, "ctf");


	@Getter
	private Integer code;

	@Getter
	private String desc;


	DumpModule(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
