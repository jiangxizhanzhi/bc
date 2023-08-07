package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.Getter;

import java.io.File;

/**
 * 目录划分
 */
public enum AttachmentPath implements GenericEnum {

	HANDOUT(0, "oj/course/%s/handout/"),
	VIDEO(1, "oj/course/%s/video/"),
	/** 课程附件*/
	ATTACHMENT(2, "oj/course_attachment/"),
	MARKDOWN(3, "oj/course/%s/markdown/"),
	OUTLINE(4, "oj/outline/"),
	/** 课后练习解题思路附件存放地址*/
	PRACTICE_KEY(6, "oj/lesson_practice_key/"),
	/** 笔记附件存放地址*/
	NOTE(7, "oj/note/"),
	/** 实验报告文件存放地址*/
	REPORT(8, "oj/report/"),
	/** 资源logo存放地址*/
	LOGO(9, "logo/"),
	/** 练习附件存放地址*/
	PRACTICE_ATTACHMENT(10, "oj/practice_attachment/"),
	MISSION(11, "cr/mission/"),
	UPLOAD_IMAGE(12,"upload/images/"),
	SINGLE_HTML_TEMPLATE(13, "html_template/RussiaCopy/Individual/"),
	TEAM_HTML_TEMPLATE(13, "html_template/RussiaCopy/Team/"),
	RED_BLUE_HTML_TEMPLATE(13, "html_template/RussiaCopy/RedBlue/"),
	EVAL_HTML_TEMPLATE(13, "html_template/RussiaCopy/Eval/"),
	EVAL_RULE_HTML_TEMPLATE(13, "C:/Users/cyberpeace/Desktop/cpms/cpmshtml_template/RussiaCopy/EvalRule"),
	UPLOAD_ATTACHMENT(14, "upload/attachment/"),
	UPLOAD_SITUATION(15,"upload/situation/"),
	LESSON_HTML_TEMPLATE(16,"html_template/RussiaCopy/Lesson"),
	OTHER(99, "other/");
	

	@Getter
	private Integer code;

	@Getter
	private String desc;

	@Getter
	private String originDesc;

	/** 初始化值*/
	private String parentPath = ConfigUtil.getContextProperty("cr.local.upload.path");

	AttachmentPath(Integer code, String desc) {
		this.code = code;
		this.originDesc = desc;
		this.desc = parentPath + File.separator +desc;
	}

}
