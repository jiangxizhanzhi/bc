package com.cyberpeace.cs.constant;

import java.io.File;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.ConfigUtil;

import lombok.Getter;

/**
 * 目录划分
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum AttachmentPath implements GenericEnum {

	/**
	 * 讲义
	 *
	 * @see AttachmentPath
	 */
	HANDOUT(0, "oj/course/%s/handout/"),
	/**
	 * 视频
	 *
	 * @see AttachmentPath
	 */
	VIDEO(1, "oj/course/%s/video/"),
	/**
	 * 课程附件
	 *
	 * @see AttachmentPath
	 */
	ATTACHMENT(2, "oj/course_attachment/"),
	/**
	 * Markdown
	 *
	 * @see AttachmentPath
	 */
	MARKDOWN(3, "oj/course/%s/markdown/"),
	/**
	 * 大纲
	 *
	 * @see AttachmentPath
	 */
	OUTLINE(4, "oj/outline/"),
	/**
	 * 课后练习解题思路附件存放地址
	 *
	 * @see AttachmentPath
	 */
	PRACTICE_KEY(6, "oj/lesson_practice_key/"),
	/**
	 * 笔记附件存放地址
	 *
	 * @see AttachmentPath
	 */
	NOTE(7, "oj/note/"),
	/**
	 * 实验报告文件存放地址
	 *
	 * @see AttachmentPath
	 */
	REPORT(8, "oj/report/"),
	/**
	 * 资源logo存放地址
	 *
	 * @see AttachmentPath
	 */
	LOGO(9, "logo/"),
	/**
	 * 练习附件存放地址
	 *
	 * @see AttachmentPath
	 */
	PRACTICE_ATTACHMENT(10, "oj/practice_attachment/"),
	/**
	 * 任务
	 *
	 * @see AttachmentPath
	 */
	MISSION(11, "cr/mission/"),
	/**
	 * 上传图片
	 *
	 * @see AttachmentPath
	 */
	UPLOAD_IMAGE(12,"upload/images/"),
	/**
	 * 单个 HTML 模板
	 *
	 * @see AttachmentPath
	 */
	SINGLE_HTML_TEMPLATE(13, "html_template/RussiaCopy/Individual/"),
	/**
	 * 团队 HTML 模板
	 *
	 * @see AttachmentPath
	 */
	TEAM_HTML_TEMPLATE(13, "html_template/RussiaCopy/Team/"),
	/**
	 * 红蓝 html 模板
	 *
	 * @see AttachmentPath
	 */
	RED_BLUE_HTML_TEMPLATE(13, "html_template/RussiaCopy/RedBlue/"),
	/**
	 * EVAL HTML 模板
	 *
	 * @see AttachmentPath
	 */
	EVAL_HTML_TEMPLATE(13, "html_template/RussiaCopy/Eval/"),
	/**
	 * 上传附件
	 *
	 * @see AttachmentPath
	 */
	UPLOAD_ATTACHMENT(14, "upload/attachment/"),
	/**
	 * 上传情况
	 *
	 * @see AttachmentPath
	 */
	UPLOAD_SITUATION(15,"upload/situation/"),
	/**
	 * 课程 HTML 模板
	 *
	 * @see AttachmentPath
	 */
	LESSON_HTML_TEMPLATE(16,"html_template/RussiaCopy/Lesson"),
	/**
	 * 上传 Markdown
	 *
	 * @see AttachmentPath
	 */
	UPLOAD_MARKDOWN(17, "upload/markdown/"),
	/**
	 * 红vs蓝
	 *
	 * @see AttachmentPath
	 */
	REDVSBLUE(18, "html_template/RussiaCopy/redvsblue/"),
	/**
	 * 其他
	 *
	 * @see AttachmentPath
	 */
	OTHER(99, "other/");


	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	/**
	 * 起源描述
	 *
	 * @see String
	 */
	@Getter
	private String originDesc;

	/**
	 * 初始化值
	 *
	 * @see String
	 */
	private String parentPath = ConfigUtil.getContextProperty("cr.local.upload.path");

	/**
	 * 附件路径
	 *
	 * @param code 代号
	 * @param desc 描述
	 */
	AttachmentPath(Integer code, String desc) {
		this.code = code;
		this.originDesc = desc;
		this.desc = parentPath + File.separator +desc;
	}

}
