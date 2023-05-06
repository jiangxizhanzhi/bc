package com.cyberpeace.situation.constant;

import java.util.List;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.LocalType;
import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * 态势大屏状态
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum SituationInstanceStatus {

	/**
	 * 草稿
	 */
	DRAFT("1", "草稿", "Draft"),

	/**
	 * 发布
	 */
	RELEASE("2", "发布", "Published");

	/**
	 * 代码
	 */
	@Getter
	private String code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	/**
	 * 英文描述
	 */
	@Getter
	private String enDesc;

	SituationInstanceStatus(String code, String desc, String enDesc) {
		this.code = code;
		this.desc = desc;
		this.enDesc = enDesc;
	}

	/**
	 * 根据请求语言获取对应描述
	 *
	 * @return {@code String}
	 */
	public String getMsg() {
		return CommonContext.isChinese() ? desc : enDesc;
	}

	/**
	 * 获取态势大屏状态列表
	 *
	 * @return {@code List<LocalType>}
	 */
	public static List<LocalType> getMsgList() {
		return StreamApiUtils.map(Lists.newArrayList(SituationInstanceStatus.values()),
				situationInstanceStatus -> new LocalType(situationInstanceStatus.getCode(),
						situationInstanceStatus.getMsg()));
	}

}
