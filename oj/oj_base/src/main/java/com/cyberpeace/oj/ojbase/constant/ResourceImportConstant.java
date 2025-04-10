package com.cyberpeace.oj.ojbase.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 资源导入文件名称配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface ResourceImportConstant {

	/**
	 * 资源导入 资源数据存储文件名称
	 *
	 * @see String
	 */
	String README_NAME = "README.md";

	/**
	 * 课程数据存储文件名称
	 *
	 * @see String
	 */
	String OUTLINE_NAME = "课程大纲.md";

	/**
	 * 课程镜像文件目录
	 *
	 * @see String
	 */
	String IMAGE_NAME = "images";

	/**
	 * 课程考试目录
	 *
	 * @see String
	 */
	String COURSE_PRATICES = "coursePratices";

	/**
	 * logo.png文件名称
	 *
	 * @see String
	 */
	String PNG_LOGO = "logo.png";

	/**
	 * logo.jpg文件名称
	 *
	 * @see String
	 */
	String JPG_LOGO = "logo.jpg";

	/**
	 * 讲义文件夹名称
	 *
	 * @see String
	 */
	String HANDOUT = "handout";

	/**
	 * 附件文件夹名称
	 *
	 * @see String
	 */
	String ATTACHMENT = "attachments";

	/**
	 * 理论题文件夹名称
	 *
	 * @see String
	 */
	String PRACTICES = "practices";

	/**
	 * 实操题文件夹名称
	 *
	 * @see String
	 */
	String OPERATION = "practices/operation";

	/**
	 * 讲义文件名称
	 *
	 * @see String
	 */
	String HANDOUT_FILE_NAME = "讲义.md";

	/**
	 * 指导书文件目录
	 *
	 * @see String
	 */
	String DOC = "doc";

	/**
	 * 指导书文件名称
	 *
	 * @see String
	 */
	String DOC_FILE_NAME = "指导书.md";

	/**
	 * 理论题文件名称
	 *
	 * @see String
	 */
	String PRACTICE_FILE_NAME = "练习.md";

	/**
	 * 课时视频文件夹名称
	 *
	 * @see String
	 */
	String VIDEO = "video";

	/**
	 * 动态环境地址
	 *
	 * @see String
	 */
	String ENV = "env";

	/**
	 * write文件夹名称
	 *
	 * @see String
	 */
	String WRITEUP = "writeup";

	/**
	 * 习题附件文件夹
	 *
	 * @see String
	 */
	String PRACTICE_ATTACHMENT = "attachment";

	/**
	 * markdown文件中 标题和标签与数据库字段映射关系
	 *
	 * @see String
	 */
	String COURSE_DATA_MAPPING = "{\"作者\":\"create_user_id\",\"课程名称\":\"name\",\"课程编号\": \"resource_id,course_num\", \"方向\": \"category_id\", \"LOGO\":\"logo\", \"课程难度\": \"difficulty\",\"简介\":\"description\"}";

	/**
	 * markdown文件中 课时数据映射关系
	 *
	 * @see String
	 */
	String LESSON_DATA_MAPPING = "{\"课时名称\":\"name\", \"类型\":\"type\", \"知识点\":\"knowledge\", \"难度\":\"difficulty\", \"学习时长\":\"duration\", \"学习类型\":\"compulsory\"}";

	/**
	 * markdown文件中 习题集映射关系数据映射关系
	 *
	 * @see String
	 */
	String PRACTICE_SET_DATA_MAPPING = "{\"题库名称\":\"name\", \"题库编号\":\"resource_id\", \"题库类型\":\"practice_type\", \"LOGO\":\"logo\"}";

	/**
	 * markdown文件中 实操习题与数据映射关系
	 *
	 * @see String
	 */
	String OPERATION_PRACTICE_DATA_MAPPING = "{\"标题\":\"title\", \"作者\":\"create_user_id\", \"方向\":\"category_id\", \"类型\":\"type\", \"知识点\":\"knowledge\", \"难度\":\"difficulty\", \"内容\":\"content\", \"是否可共享\":\"scene_share_mode\", \"FLAG\":\"answer\"}";

	/**
	 * 内置导入超级管理员编号
	 *
	 * @see String
	 */
	String SUPER_ADMIN_HASH = "3f655d87112f4ae094bbaf70830e20aa";

	/**
	 * 资源导入导出文件存放目录
	 *
	 * @see String
	 */
	String DUMP_LOAD_FILE_STORE_PATH = "file";

	/**
	 * 页面导入导出数据存储文件地址
	 *
	 * @see String
	 */
	String DATA_JSON = "data.json";

	/**
	 * 脚本
	 *
	 * @see String
	 */
	String SCRIPT = "script";

	/**
	 * Writeup MD
	 *
	 * @see String
	 */
	String WRITEUP_MD = "writeup.md";

	/**
	 * 实践设置图标
	 *
	 * @see List<String>
	 */
	List<String> PRACTICE_SET_LOGO = Arrays.asList("/cpms/built_in/SetLogo1.png", "/cpms/built_in/SetLogo2.png", "/cpms/built_in/SetLogo3.png", "/cpms/built_in/SetLogo4.png", "/cpms/built_in/SetLogo5.png");
}
