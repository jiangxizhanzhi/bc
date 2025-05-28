/**
 * ### 代码分析 
 * #### 1. **功能概述** 
 * 该代码定义了一个枚举类 `AttachmentPath`，用于表示不同类型的文件或资源目录路径。每个枚举项代表一种特定的目录类型，并提供了一些元数据（如编号、路径模板等）。这些信息可以用于生成动态路径。 
 * --- 
 * #### 2. **主要特性** 
 * - **枚举设计**：使用了 Java 的枚举类型，方便管理和扩展。 
 * - **实现接口**：实现了 `GenericEnum` 接口，可能需要某些通用方法（如 `getCode()` 和 `getDesc()`）。 
 * - **Lombok 注解**：使用了 `@Getter` 注解自动生成 getter 方法，减少了冗余代码。 
 * - **路径生成逻辑**：通过构造函数动态拼接路径，结合父路径 `/cpms` 和子路径模板生成最终路径。 
 * --- 
 * #### 3. **关键字段与方法** 
 * - **字段**： 
 * - `code`：每个目录类型的唯一编号。 
 * - `desc`：目录的完整路径（包含父路径 `/cpms` 和子路径模板）。 
 * - `originDesc`：原始路径模板（未拼接父路径的部分）。 
 * - `parentPath`：默认父路径，固定为 `/cpms`。 
 * - **构造函数**： 
 * - 参数 `code` 和 `desc` 分别设置编号和路径模板。 
 * - 动态生成 `desc` 字段时，将 `parentPath` 和 `desc` 拼接在一起。 
 * --- 
 * #### 4. **优点** 
 * - **可读性强**：通过枚举统一管理目录类型，避免硬编码。 
 * - **灵活性高**：可以通过修改 `parentPath` 或路径模板快速调整路径结构。 
 * - **易于扩展**：新增目录类型只需添加新的枚举项即可。 
 * --- 
 * #### 5. **潜在问题** 
 * - **路径冲突**：多个枚举项使用相同的 `code` 值可能会导致冲突（如 `SINGLE_HTML_TEMPLATE` 和 `TEAM_HTML_TEMPLATE` 的 `code` 都是 `13`）。 
 * - **重复代码**：多个枚举项的路径模板类似，但存在冗余（如 `html_template/RussiaCopy/...`）。 
 * - **国际化支持不足**：路径描述目前仅支持英文，未考虑多语言场景。 
 * --- 
 * #### 6. **改进建议** 
 * - **解决冲突**：确保每个枚举项的 `code` 值唯一。 
 * - **优化路径模板**：提取公共路径部分（如 `html_template/RussiaCopy/`），减少重复代码。 
 * - **国际化支持**：将路径描述存储在资源文件中，支持多语言。 
 * --- 
 * #### 7. **总结** 
 * 该代码是一个典型的枚举类实现，用于管理文件目录路径。虽然功能完整，但仍需注意潜在的问题并进行适当优化以提高代码质量和可维护性。 
**/
package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

import java.io.File;

/**
 * 目录划分
 *
 * @author SN
 * @date 2024/11/08
 */
public enum AttachmentPath implements GenericEnum {

	/**
	 * 讲义
	 */
	HANDOUT(0, "oj/course/%s/handout/"),
	/**
	 * 视频
	 */
	VIDEO(1, "oj/course/%s/video/"),
	/**
	 * 课程附件
	 */
	ATTACHMENT(2, "oj/course_attachment/"),
	/**
	 * Markdown
	 */
	MARKDOWN(3, "oj/course/%s/markdown/"),
	/**
	 * 大纲
	 */
	OUTLINE(4, "oj/outline/"),
	/**
	 * 课后练习解题思路附件存放地址
	 */
	PRACTICE_KEY(6, "oj/lesson_practice_key/"),
	/**
	 * 笔记附件存放地址
	 */
	NOTE(7, "oj/note/"),
	/**
	 * 实验报告文件存放地址
	 */
	REPORT(8, "oj/report/"),
	/**
	 * 资源logo存放地址
	 */
	LOGO(9, "logo/"),
	/**
	 * 练习附件存放地址
	 */
	PRACTICE_ATTACHMENT(10, "oj/practice_attachment/"),
	/**
	 * 任务
	 */
	MISSION(11, "cr/mission/"),
	/**
	 * 上传图片
	 */
	UPLOAD_IMAGE(12,"upload/images/"),
	/**
	 * 单个 HTML 模板
	 */
	SINGLE_HTML_TEMPLATE(13, "html_template/RussiaCopy/Individual/"),
	/**
	 * 团队 HTML 模板
	 */
	TEAM_HTML_TEMPLATE(13, "html_template/RussiaCopy/Team/"),
	/**
	 * 红蓝 html 模板
	 */
	RED_BLUE_HTML_TEMPLATE(13, "html_template/RussiaCopy/RedBlue/"),
	/**
	 * EVAL HTML 模板
	 */
	EVAL_HTML_TEMPLATE(13, "html_template/RussiaCopy/Eval/"),
	/**
	 * 上传附件
	 */
	UPLOAD_ATTACHMENT(14, "upload/attachment/"),
	/**
	 * 上传态势
	 */
	UPLOAD_SITUATION(15,"upload/situation/"),
	/**
	 * 课程 HTML 模板
	 */
	LESSON_HTML_TEMPLATE(16,"html_template/RussiaCopy/Lesson"),
	/**
	 * 上传 Markdown
	 */
	UPLOAD_MARKDOWN(17, "upload/markdown/"),
	/**
	 * 红vs蓝
	 */
	REDVSBLUE(18, "html_template/RussiaCopy/redvsblue/"),
	/**
	 * Strategy
	 */
	STRATEGY(19, "html_template/RussiaCopy/Strategy/"),
	/**
	 * 其他
	 */
	OTHER(99, "other/");


	/**
	 * 代号
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	/**
	 * 起源描述
	 */
	@Getter
	private String originDesc;

	/**
	 * 初始化值
	 */
	private String parentPath = "/cpms";

	AttachmentPath(Integer code, String desc) {
		this.code = code;
		this.originDesc = desc;
		this.desc = parentPath + File.separator +desc;
	}

}
