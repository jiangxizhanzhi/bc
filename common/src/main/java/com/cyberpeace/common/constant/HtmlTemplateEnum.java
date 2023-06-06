package com.cyberpeace.common.constant;

import lombok.Getter;
import org.springframework.util.ClassUtils;

import java.io.File;

public enum HtmlTemplateEnum {
	/** html模板枚举*/
	INDIVIDUAL("index.html", "static/template/html_to_pdf_template/Individual"),
	SINGLE("index.html", "static/template/html_to_pdf_template/single"),
	REDBLUE("index.html", "static/template/html_to_pdf_template/redvsblue"),
	TEAM("index.html", "static/template/html_to_pdf_template/Team"),
	EVAL("index.html", "static/template/html_to_pdf_template/Eval"),
	LESSON("index.html", "static/template/html_to_pdf_template/Lesson");

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
