package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 目录划分
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum DumpModule implements GenericEnum {

	/**
	 * 课程
	 *
	 * @see DumpModule
	 */
	COURSE(0, "course"),
	/**
	 * CTF 实践
	 *
	 * @see DumpModule
	 */
	CTF_PRACTICE(1, "ctf");


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


	DumpModule(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
