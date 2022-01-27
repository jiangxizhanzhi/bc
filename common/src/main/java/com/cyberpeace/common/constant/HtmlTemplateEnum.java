package com.cyberpeace.common.constant;

import lombok.Getter;
import org.springframework.util.ClassUtils;

import java.io.File;

public enum HtmlTemplateEnum {
	/** html模板枚举*/
	INDIVIDUAL("index.html", "template/html_to_pdf_template/Individual"),
	REDBLUE("index.html", "template/html_to_pdf_template/Team"),
	TEAM("index.html", "template/html_to_pdf_template/Team");

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
