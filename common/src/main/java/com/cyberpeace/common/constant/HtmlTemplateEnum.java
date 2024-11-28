package com.cyberpeace.common.constant;

import lombok.Getter;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.util.Objects;

@Getter
public enum HtmlTemplateEnum {
	/** html模板枚举*/
	INDIVIDUAL(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/Individual"),
	SINGLE(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/single"),
	REDBLUE(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/redvsblue"),
	TEAM(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/teamNew"),
	EVAL(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/Eval"),
	LESSON(CommonConstant.INDEX_HTML, "static/template/html_to_pdf_template/Lesson");

	private final String templatePath;

	private final String templateName;

	HtmlTemplateEnum(String templateName, String templatePath) {
		this.templateName = templateName;
		this.templatePath = Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResource("").getPath()
				+ File.separatorChar + templatePath;
	}

}
