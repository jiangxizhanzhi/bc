package com.cyberpeace.common.constant;

import lombok.Getter;

public enum HtmlTemplateEnum {

	INDIVIDUAL("index.html", "/cpms/html_template/RussiaCopy/Individual"),
	REDBLUE("index.html", "/cpms/html_template/RussiaCopy/Team"),
	TEAM("index.html", "/cpms/html_template/RussiaCopy/Team");

	@Getter
	private String templatePath;

	@Getter
	private String templateName;

	HtmlTemplateEnum(String templateName, String templatePath) {
		this.templateName = templateName;
		this.templatePath = templatePath;
	}

}
