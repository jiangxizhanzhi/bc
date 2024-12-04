package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

/**
 * 课堂导出报告常量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see BaseI18n
 * @since 1.0.0
 */
public enum ClassroomExportReportConstant implements BaseI18n {

	/**
	 * 实验室报告后缀
	 *
	 * @see ClassroomExportReportConstant
	 */
	LABORATORY_REPORT_SUFFIX("-实验报告.zip","-report.zip"),
	/**
	 * 会话报告
	 *
	 * @see ClassroomExportReportConstant
	 */
	SESSION_REPORT("-课时报告","-class report"),
	/**
	 * 实验室报告标题
	 *
	 * @see ClassroomExportReportConstant
	 */
	LABORATORY_REPORT_TITLE("实验报告:","Report:"),
	/**
	 * 未获批准
	 *
	 * @see ClassroomExportReportConstant
	 */
	NOT_APPROVED("未审核","not review"),
	/**
	 * 是
	 *
	 * @see ClassroomExportReportConstant
	 */
	YES("是","yes"),
	/**
	 * 不
	 *
	 * @see ClassroomExportReportConstant
	 */
	NO("否","no"),
	/**
	 * 乐谱标题
	 *
	 * @see ClassroomExportReportConstant
	 */
	SCORE_TITLE("评分:","Score:"),
	/**
	 * 传递标题
	 *
	 * @see ClassroomExportReportConstant
	 */
	PASSED_TITLE("是否通过:","Passed or not:"),
	/**
	 * 评估评论标题
	 *
	 * @see ClassroomExportReportConstant
	 */
	EVALUATE_COMMENT_TITLE("评语:","Comments:");

	/**
	 * 中文
	 *
	 * @see String
	 */
	@Getter
	private final String chinese;

	/**
	 * 英语
	 *
	 * @see String
	 */
	@Getter
	private final String english;

	ClassroomExportReportConstant(String chinese, String english) {
		this.chinese = chinese;
		this.english = english;
	}

}
