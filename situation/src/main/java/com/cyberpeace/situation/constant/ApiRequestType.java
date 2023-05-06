package com.cyberpeace.situation.constant;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * api请求类型
 *
 * @Author xuxz
 * @Date 4/11/2022 上午11:11
 */
public enum ApiRequestType {

	/**
	 * POST
	 */
	POST(1, "POST"),

	/**
	 * DELETE
	 */
	DELETE(2, "DELETE"),

	/**
	 * PATCH
	 */
	PATCH(3, "PATCH"),

	/**
	 * GET
	 */
	GET(4, "GET");

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	ApiRequestType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 获取请求类型列表
	 *
	 * @return {@code List<String>}
	 */
	public static List<String> getRequestType() {
		return Lists.newArrayList(POST.desc, DELETE.desc, PATCH.desc, GET.desc);
	}
}
