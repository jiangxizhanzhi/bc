package com.cyberpeace.cs.constant;

import java.io.File;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.ConfigUtil;

import lombok.Getter;

/**
 * 附件路径
 * 目录划分
 *
 * @author cyberpeace
 * @date 2023/05/06
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
	 * 减价
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
	UPLOAD_IMAGE(12, "upload/images/"),
	/**
	 * 单一html模板
	 */
	SINGLE_HTML_TEMPLATE(13, "html_template/RussiaCopy/Individual/"),
	/**
	 * 团队html模板
	 */
	TEAM_HTML_TEMPLATE(13, "html_template/RussiaCopy/Team/"),
	/**
	 * 红蓝html模板
	 */
	RED_BLUE_HTML_TEMPLATE(13, "html_template/RussiaCopy/RedBlue/"),
	/**
	 * eval html模板
	 */
	EVAL_HTML_TEMPLATE(13, "html_template/RussiaCopy/Eval/"),
	/**
	 * 上传附件
	 */
	UPLOAD_ATTACHMENT(14, "upload/attachment/"),
	/**
	 * 上传情况
	 */
	UPLOAD_SITUATION(15, "upload/situation/"),
	/**
	 * 其他
	 */
	OTHER(99, "other/");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	@Getter
	private String originDesc;

	/**
	 * 初始化值
	 */
	private String parentPath = ConfigUtil.getContextProperty("cr.local.upload.path");

	AttachmentPath(Integer code, String desc) {
		this.code = code;
		this.originDesc = desc;
		this.desc = parentPath + File.separator + desc;
	}

}
