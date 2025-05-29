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
/**
 * 这段代码定义了一个名为 `ClassroomExportReportConstant` 的枚举类，该类实现了 `BaseI18n` 接口。这个枚举类主要用于存储与课堂导出报告相关的国际化（i18n）字符串常量。以下是详细的分析： 
 * ### 类定义 
 * ```java 
 * public enum ClassroomExportReportConstant implements BaseI18n { 
 * ``` 
 * - `ClassroomExportReportConstant` 是一个枚举类型。 
 * - 它实现了 `BaseI18n` 接口。 
 * ### 枚举常量 
 * 枚举类中定义了多个枚举常量，每个常量都有中文和英文两种语言的表示： 
 * ```java 
 * LABORATORY_REPORT_SUFFIX("-实验报告.zip", "-report.zip"), 
 * SESSION_REPORT("-课时报告", "-class report"), 
 * LABORATORY_REPORT_TITLE("实验报告:", "Report:"), 
 * NOT_APPROVED("未审核", "not review"), 
 * YES("是", "yes"), 
 * NO("否", "no"), 
 * SCORE_TITLE("评分:", "Score:"), 
 * PASSED_TITLE("是否通过:", "Passed or not:"), 
 * EVALUATE_COMMENT_TITLE("评语:", "Comments:"); 
 * ``` 
 * - 每个常量都包含两个字段：中文版本和英文版本。 
 * - 这些常量代表了不同的报告相关的内容，如实验室报告、会话报告、评分等。 
 * ### 字段定义 
 * 枚举类中定义了两个私有字段： 
 * ```java 
 * @Getter 
 * private final String chinese; 
 * @Getter 
 * private final String english; 
 * ``` 
 * - `chinese` 和 `english` 分别用于存储中文和英文的字符串。 
 * - 使用 Lombok 的 `@Getter` 注解自动生成这两个字段的 getter 方法。 
 * ### 构造方法 
 * 枚举类有一个构造方法： 
 * ```java 
 * ClassroomExportReportConstant(String chinese, String english) { 
 * this.chinese = chinese; 
 * this.english = english; 
 * } 
 * ``` 
 * - 构造方法接受两个参数：中文字符串和英文字符串，并将它们分别赋值给 `chinese` 和 `english` 字段。 
 * ### 总结 
 * - `ClassroomExportReportConstant` 枚举类用于存储与课堂导出报告相关的国际化字符串常量。 
 * - 每个枚举常量都包含中文和英文两种语言的表示。 
 * - 使用 Lombok 的 `@Getter` 注解简化了字段的访问。 
 * - 该枚举类可以方便地在多语言环境下使用，根据需要选择合适的语言版本。 
**/
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

