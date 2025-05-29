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
/**
 * 该代码定义了一个名为 `HtmlTemplateEnum` 的枚举类，用于管理 HTML 模板的相关信息。以下是代码的主要功能和结构分析： 
 * 1. **枚举定义**： 
 * - 枚举类型 `HtmlTemplateEnum` 包含多个枚举常量，每个常量代表一种 HTML 模板。 
 * - 常量包括：`INDIVIDUAL`、`SINGLE`、`REDBLUE`、`TEAM`、`EVAL`、`LESSON` 和 `Strategy`。 
 * 2. **字段说明**： 
 * - 每个枚举常量有两个字段： 
 * - `templateName`：模板文件名（如 `"index.html"`）。 
 * - `templatePath`：模板文件的完整路径，通过 `ClassUtils.getDefaultClassLoader().getResource("").getPath()` 动态生成。 
 * 3. **构造方法**： 
 * - 构造方法接收两个参数：`templateName` 和 `templatePath`。 
 * - 在构造方法中，动态拼接模板路径，确保路径包含项目资源目录。 
 * 4. **注解与功能**： 
 * - 使用了 Lombok 的 `@Getter` 注解，自动生成字段的 getter 方法。 
 * - 提供了详细的 Javadoc 注释，说明枚举的用途和字段含义。 
 * 5. **用途**： 
 * - 该枚举类主要用于统一管理 HTML 模板的路径和名称，便于在项目中复用和维护。 
 * 总结：该代码通过枚举的方式封装了 HTML 模板的路径和名称，提高了代码的可读性和可维护性。 
**/
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

