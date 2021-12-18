package com.cyberpeace.oj.ojbase.constant;

/**
 * 资源导入文件名称配置
 */
public interface ResourceImportConstant {

	/** 资源导入 资源数据存储文件名称*/
	String README_NAME = "README.md";

	/** 课程数据存储文件名称*/
	String OUTLINE_NAME = "课程大纲.md";

	/** 课程镜像文件目录*/
	String IMAGE_NAME = "images";

	/** logo.png文件名称*/
	String PNG_LOGO = "logo.png";

	/** logo.jpg文件名称*/
	String JPG_LOGO = "logo.jpg";

	/** 讲义文件夹名称*/
	String HANDOUT = "handout";

	/** 附件文件夹名称*/
	String ATTACHMENT = "attachments";

	/** 讲义文件名称*/
	String HANDOUT_FILE_NAME = "讲义.md";

	/** 指导书文件目录*/
	String DOC = "doc";

	/** 指导书文件名称*/
	String DOC_FILE_NAME = "指导书.md";

	/** 课时视频文件夹名称*/
	String VIDEO = "video";

	/** 动态环境地址*/
	String ENV = "env";

	/** markdown文件中 标题和标签与数据库字段映射关系*/
	String COURSE_DATA_MAPPING = "{\"作者\":\"create_user_id\",\"课程名称\":\"name\",\"课程编号\": \"resource_id,course_num\", \"方向\": \"category_id\", \"LOGO\":\"logo\", \"课程难度\": \"difficulty\",\"简介\":\"description\"}";

	/** markdown文件中 课时数据映射关系*/
	String LESSON_DATA_MAPPING = "{\"课时名称\":\"name\", \"类型\":\"type\", \"知识点\":\"knowledge\", \"难度\":\"difficulty\", \"学习时长\":\"duration\", \"学习类型\":\"compulsory\"}";

	/** 内置导入超级管理员编号*/
	String SUPER_ADMIN_HASH = "3f655d87112f4ae094bbaf70830e20aa";
}
