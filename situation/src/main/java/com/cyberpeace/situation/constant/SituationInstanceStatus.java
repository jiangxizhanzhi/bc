package com.cyberpeace.situation.constant;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.LocalType;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public enum SituationInstanceStatus {

	/**
	 * 过程状态
	 */

	DRAFT("1", "草稿", "Draft"),
	RELEASE("2", "发布", "Published");

	@Getter
	private String code;

	@Getter
	private String desc;

	@Getter
	private String enDesc;


	SituationInstanceStatus(String code, String desc, String enDesc) {
		this.code = code;
		this.desc = desc;
		this.enDesc = enDesc;
	}

	public String getMsg() {
		return CommonContext.isChinese() ? desc : enDesc;
	}

	public static List<LocalType> getMsgList(){
		return StreamApiUtils.map(Lists.newArrayList(SituationInstanceStatus.values()),
				situationInstanceStatus -> new LocalType(situationInstanceStatus.getCode(), situationInstanceStatus.getMsg()));
	}

}
