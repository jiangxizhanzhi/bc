package com.cyberpeace.common.constant;

import lombok.Getter;
import org.springframework.util.ClassUtils;

import java.io.File;

/**
 * HTML 模板枚举
 *
 * @author SN
 * @date 2024/11/08
 */
public enum HtmlTemplateEnum {
	/**
	 * html模板枚举
	 */
	INDIVIDUAL("index.html", "static/template/html_to_pdf_template/Individual"),
	/**
	 * 单
	 */
	SINGLE("index.html", "static/template/html_to_pdf_template/single"),
	/**
	 * 红蓝
	 */
	REDBLUE("index.html", "static/template/html_to_pdf_template/redvsblue"),
	/**
	 * 团队
	 */
	TEAM("index.html", "static/template/html_to_pdf_template/teamNew"),
	/**
	 * 评估
	 */
	EVAL("index.html", "static/template/html_to_pdf_template/Eval"),
	/**
	 * 课
	 */
	LESSON("index.html", "static/template/html_to_pdf_template/Lesson");

	/**
	 * 模板路径
	 */
	@Getter
	private String templatePath;

	/**
	 * 模板名称
	 */
	@Getter
	private String templateName;

	HtmlTemplateEnum(String templateName, String templatePath) {
		this.templateName = templateName;
		this.templatePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()
				+ File.separatorChar + templatePath;
	}

}
