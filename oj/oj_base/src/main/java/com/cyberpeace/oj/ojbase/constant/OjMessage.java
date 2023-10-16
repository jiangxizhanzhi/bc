package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

public enum OjMessage implements BaseI18n {

	EXCEL_IMPORT_MSG("第%s行的理论题已存在，跳过导入", "Practice(s) of column %s already exist, skip importing.");

	@Getter
	private String chinese;

	@Getter
	private String english;

	OjMessage(String chinese, String english) {
		this.chinese = chinese;
		this.english = english;
    }
}
