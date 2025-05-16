package com.cyberpeace.common.constant;

import lombok.Getter;
import org.springframework.util.ClassUtils;

import java.io.File;

/**
 * HTML 模板枚举
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @since 1.0.0
 */
public enum HtmlTemplateEnum {
	/**
	 * html模板枚举
	 *
	 * @see HtmlTemplateEnum
	 */
	INDIVIDUAL("index.html", "static/template/html_to_pdf_template/Individual"),
	/**
	 * 单
	 *
	 * @see HtmlTemplateEnum
	 */
	SINGLE("index.html", "static/template/html_to_pdf_template/single"),
	/**
	 * 红蓝
	 *
	 * @see HtmlTemplateEnum
	 */
	REDBLUE("index.html", "static/template/html_to_pdf_template/redvsblue"),
	/**
	 * 团队
	 *
	 * @see HtmlTemplateEnum
	 */
	TEAM("index.html", "static/template/html_to_pdf_template/teamNew"),
	/**
	 * 评估
	 *
	 * @see HtmlTemplateEnum
	 */
	EVAL("index.html", "static/template/html_to_pdf_template/Eval"),
	/**
	 * 课
	 *
	 * @see HtmlTemplateEnum
	 */
	LESSON("index.html", "static/template/html_to_pdf_template/Lesson"),
	/**
	 * 策略
	 *
	 */
	 Strategy("index.html", "static/template/html_to_pdf_template/Strategy");
	/**
	 * 模板路径
	 *
	 * @see String
	 */
	@Getter
	private String templatePath;

	/**
	 * 模板名称
	 *
	 * @see String
	 */
	@Getter
	private String templateName;

	HtmlTemplateEnum(String templateName, String templatePath) {
		this.templateName = templateName;
		this.templatePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()
				+ File.separatorChar + templatePath;
	}

}
