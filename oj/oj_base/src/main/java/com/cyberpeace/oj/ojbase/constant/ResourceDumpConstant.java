package com.cyberpeace.oj.ojbase.constant;

/**
 * 资源导入文件名称配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface ResourceDumpConstant {

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
	 * 课程数据存储文件名称
	 *
	 * @see String
	 */
	String STAGE_EXAM = "阶段考试";

	/**
	 * 课程数据存储文件名称
	 *
	 * @see String
	 */
	String FINAL_EXAM = "结业考试";

	/**
	 * 课程镜像文件目录
	 *
	 * @see String
	 */
	String IMAGE_NAME = "images";

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
	 * 讲义文件名称
	 *
	 * @see String
	 */
	String HANDOUT_FILE_NAME = "讲义.md";

	/**
	 * 讲义md以外文件名称
	 *
	 * @see String
	 */
	String HANDOUT_OTHER_FILE_NAME = "讲义";

	/**
	 * 指导书md以外文件名称
	 *
	 * @see String
	 */
	String DOC_OTHER_FILE_NAME = "指导书";

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
	 * 版本信息文件
	 *
	 * @see String
	 */
	String VERSION_FILE = "version.txt";


	/**
	 * 资源导入导出文件存放根目录
	 *
	 * @see String
	 */
	String DUMP_ALL_FILE_STORE_PATH = "/data/resource/export";

	/**
	 * 课程默认logo
	 *
	 * @see String
	 */
	String DUMP_COURSE_DEFAULT_LOGO_STORE_PATH = "/cpms/built_in/logo.png";

	/**
	 * 课程资源导出文件存放根目录
	 *
	 * @see String
	 */
	String DUMP_ALL_COURSE_FILE_STORE_PATH = "/course";

	/**
	 * 课程资源导出文件存放根目录
	 *
	 * @see String
	 */
	String DUMP_OJ_FILE_STORE_PATH = "/oj";

	/**
	 * 默认封面路径
	 *
	 * @see String
	 */
	String DEFAULT_LOGO_PATH = "/cpms/built_in/logo.png";
}
