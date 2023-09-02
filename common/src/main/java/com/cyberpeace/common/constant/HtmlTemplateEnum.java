package com.cyberpeace.common.constant;

import lombok.Getter;
import org.springframework.util.ClassUtils;

import java.io.File;

public enum HtmlTemplateEnum {
	/** html模板枚举*/
	INDIVIDUAL("index.html", "static/template/html_to_pdf_template/Individual"),
	REDBLUE("index.html", "static/template/html_to_pdf_template/Team"),
	TEAM("index.html", "static/template/html_to_pdf_template/Team"),
	EVAL("index.html", "static/template/html_to_pdf_template/Eval"),
	LESSON("index.html", "static/template/html_to_pdf_template/Lesson"),
	EVAL_RULE("index.html", "static/template/html_to_pdf_template/EvalRule"),
	MISSION_RANK("index.html", "static/template/html_to_pdf_template/MissionRank");

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
