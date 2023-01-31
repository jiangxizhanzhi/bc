package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

public enum ClassroomExportReportConstant implements BaseI18n {

	LABORATORY_REPORT_SUFFIX("-实验报告.zip","-report.zip"),
	SESSION_REPORT("-课时报告","-class report"),
	LABORATORY_REPORT_TITLE("实验报告:","Report:"),
	NOT_APPROVED("未审核","not review"),
	YES("是","yes"),
	NO("否","no"),
	SCORE_TITLE("评分:","Score:"),
	PASSED_TITLE("是否通过:","Passed or not:"),
	EVALUATE_COMMENT_TITLE("评语:","Comments:");

	@Getter
	private final String chinese;

	@Getter
	private final String english;

	ClassroomExportReportConstant(String chinese, String english) {
		this.chinese = chinese;
		this.english = english;
	}

}
