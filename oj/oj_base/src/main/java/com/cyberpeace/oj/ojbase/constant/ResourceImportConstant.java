package com.cyberpeace.oj.ojbase.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 资源导入文件名称配置
 *
 * @author SN
 * @date 2024/11/14
 */
public final class ResourceImportConstant {

	private ResourceImportConstant() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 资源导入 资源数据存储文件名称
	 */
	public static final String README_NAME = "README.md";

	/**
	 * 课程数据存储文件名称
	 */
	public static final String OUTLINE_NAME = "课程大纲.md";

	/**
	 * 课程镜像文件目录
	 */
	public static final String IMAGE_NAME = "images";

	/**
	 * 课程阶段性考试考试目录 该目录在v5.4.1之前版本都是只定义，未使用
	 */
	public static final String COURSE_PRATICES = "coursePratices";

	/**
	 * logo.png文件名称
	 */
	public static final String PNG_LOGO = "logo.png";

	/**
	 * logo.jpg文件名称
	 */
	public static final String JPG_LOGO = "logo.jpg";

	/**
	 * 讲义文件夹名称
	 */
	public static final String HANDOUT = "handout";

	/**
	 * 附件文件夹名称
	 */
	public static final String ATTACHMENT = "attachments";

	/**
	 * 理论题文件夹名称
	 */
	public static final String PRACTICES = "practices";

	/**
	 * 实操题文件夹名称
	 */
	public static final String OPERATION = "practices/operation";

	/**
	 * 讲义文件名称
	 */
	public static final String HANDOUT_FILE_NAME = "讲义.md";

	/**
	 * 指导书文件目录
	 */
	public static final String DOC = "doc";

	/**
	 * 指导书文件名称
	 */
	public static final String DOC_FILE_NAME = "指导书.md";

	/**
	 * 理论题文件名称
	 */
	public static final String PRACTICE_FILE_NAME = "练习.md";

	/**
	 * 课时视频文件夹名称
	 */
	public static final String VIDEO = "video";

	/**
	 * 动态环境地址
	 */
	public static final String ENV = "env";

	/**
	 * write文件夹名称
	 */
	public static final String WRITEUP = "writeup";

	/**
	 * 习题附件文件夹
	 */
	public static final String PRACTICE_ATTACHMENT = "attachment";

	/**
	 * markdown文件中 标题和标签与数据库字段映射关系
	 */
	public static final String COURSE_DATA_MAPPING = "{\"作者\":\"create_user_id\",\"课程名称\":\"name\",\"课程编号\": \"resource_id,course_num\", \"方向\": \"category_id\", \"LOGO\":\"logo\", \"课程难度\": \"difficulty\",\"简介\":\"description\"}";

	/**
	 * markdown文件中 课时数据映射关系
	 */
	public static final String LESSON_DATA_MAPPING = "{\"课时名称\":\"name\", \"类型\":\"type\", \"知识点\":\"knowledge\", \"难度\":\"difficulty\", \"学习时长\":\"duration\", \"学习类型\":\"compulsory\"}";

	/**
	 * markdown文件中 习题集映射关系数据映射关系
	 */
	public static final String PRACTICE_SET_DATA_MAPPING = "{\"题库名称\":\"name\", \"题库编号\":\"resource_id\", \"题库类型\":\"practice_type\", \"LOGO\":\"logo\"}";

	/**
	 * markdown文件中 实操习题与数据映射关系
	 */
	public static final String OPERATION_PRACTICE_DATA_MAPPING = "{\"标题\":\"title\", \"作者\":\"create_user_id\", \"方向\":\"category_id\", \"知识点\":\"knowledge\", \"难度\":\"difficulty\", \"内容\":\"content\", \"是否可共享\":\"scene_share_mode\", \"FLAG\":\"answer\"}";

	/**
	 * 内置导入超级管理员编号
	 */
	public static final String SUPER_ADMIN_HASH = "3f655d87112f4ae094bbaf70830e20aa";

	/**
	 * 资源导入导出文件存放目录
	 */
	public static final String DUMP_LOAD_FILE_STORE_PATH = "file";

	/**
	 * 页面导入导出数据存储文件地址
	 */
	public static final String DATA_JSON = "data.json";

	/**
	 * 脚本
	 */
	public static final String SCRIPT = "script";

	/**
	 * 题解markdown
	 */
	public static final String WRITEUP_MD = "writeup.md";

	/**
	 * 练习设置图标
	 */
	public static final List<String> PRACTICE_SET_LOGO = Arrays.asList("/cpms/built_in/SetLogo1.png", "/cpms/built_in/SetLogo2.png", "/cpms/built_in/SetLogo3.png", "/cpms/built_in/SetLogo4.png", "/cpms/built_in/SetLogo5.png");
}
