package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

/**
 * 课堂导出报告常量
 *
 * @author SN
 * @date 2024/11/08
 */
public enum ClassroomExportReportConstant implements BaseI18n {

	/**
	 * 实验室报告后缀
	 */
	LABORATORY_REPORT_SUFFIX("-实验报告.zip","-report.zip"),
	/**
	 * 会话报告
	 */
	SESSION_REPORT("-课时报告","-class report"),
	/**
	 * 实验室报告标题
	 */
	LABORATORY_REPORT_TITLE("实验报告:","Report:"),
	/**
	 * 未获批准
	 */
	NOT_APPROVED("未审核","not review"),
	/**
	 * 是
	 */
	YES("是","yes"),
	/**
	 * 不
	 */
	NO("否","no"),
	/**
	 * 乐谱标题
	 */
	SCORE_TITLE("评分:","Score:"),
	/**
	 * 传递标题
	 */
	PASSED_TITLE("是否通过:","Passed or not:"),
	/**
	 * 评估评论标题
	 */
	EVALUATE_COMMENT_TITLE("评语:","Comments:");

	/**
	 * 中文
	 */
	@Getter
	private final String chinese;

	/**
	 * 英语
	 */
	@Getter
	private final String english;

	ClassroomExportReportConstant(String chinese, String english) {
		this.chinese = chinese;
		this.english = english;
	}

}
