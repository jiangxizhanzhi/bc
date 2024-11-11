package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 目录划分
 *
 * @author SN
 * @date 2024/11/11
 */
public enum DumpModule implements GenericEnum {

	/**
	 * 课程
	 */
	COURSE(0, "course"),
	/**
	 * CTF 练习
	 */
	CTF_PRACTICE(1, "ctf");


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


	DumpModule(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
