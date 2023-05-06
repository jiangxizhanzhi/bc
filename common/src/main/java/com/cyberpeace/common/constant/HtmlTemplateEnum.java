package com.cyberpeace.common.constant;

import java.io.File;

import org.springframework.util.ClassUtils;

import lombok.Getter;

/**
 * html模板枚举
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum HtmlTemplateEnum {

	/** html模板枚举*/
	INDIVIDUAL("index.html", "static/template/html_to_pdf_template/Individual"), REDBLUE(
			"index.html", "static/template/html_to_pdf_template/Team"), TEAM("index.html",
					"static/template/html_to_pdf_template/Team"), EVAL("index.html",
							"static/template/html_to_pdf_template/Eval");

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

	/**
	 * html模板枚举
	 *
	 * @param templateName 模板名称
	 * @param templatePath 模板路径
	 */
	HtmlTemplateEnum(String templateName, String templatePath) {
		this.templateName = templateName;
		this.templatePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()
				+ File.separatorChar + templatePath;
	}

}
