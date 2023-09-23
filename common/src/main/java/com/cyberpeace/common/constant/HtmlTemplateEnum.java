package com.cyberpeace.common.constant;

import lombok.Getter;
import org.springframework.util.ClassUtils;

import java.io.File;

public enum HtmlTemplateEnum {
	/** html模板枚举*/
	INDIVIDUAL(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/Individual"),
	SINGLE(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/single"),
	REDBLUE(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/redvsblue"),
	TEAM(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/teamNew"),
	EVAL(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/Eval"),
	LESSON(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/Lesson");

	@Getter
	private String templatePath;

	@Getter
	private String templateName;

	HtmlTemplateEnum(String templateName, String templatePath) {
		this.templateName = templateName;
		this.templatePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()
				+ File.separatorChar + templatePath;
	}

}
