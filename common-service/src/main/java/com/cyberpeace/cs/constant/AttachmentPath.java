package com.cyberpeace.cs.constant;

import java.io.File;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.ConfigUtil;

import lombok.Getter;

/**
 * 目录划分
 */
public enum AttachmentPath implements GenericEnum {

	HANDOUT(0, "course/%s/handout/"),
	VIDEO(1, "course/%s/video/"),
	/** 课程附件*/
	ATTACHMENT(2, "course_attachment/"),
	MARKDOWN(3, "course/%s/markdown/"),
	OUTLINE(4, "outline/"),
	/** 课后练习解题思路附件存放地址*/
	PRACTICE_KEY(6, "lesson_practice_key/"),
	/** 笔记附件存放地址*/
	NOTE(7, "note/"),
	/** 实验报告存放地址*/
	REPORT(8, "report/"),
	/** 资源logo存放地址*/
	LOGO(9, "logo/"),
	/** 练习附件存放地址*/
	PRACTICE_ATTACHMENT(10, "practice_attachment/"),
	MISSION(11, "cr/mission/"),
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
