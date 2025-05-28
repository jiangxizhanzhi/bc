/**
 * 这段代码定义了一个名为 `ResourceDumpConstant` 的接口，用于存储与资源导入相关的常量。这些常量包括各种文件名、文件夹名以及一些路径。以下是对这段代码的简要分析： 
 * ### 主要功能 
 * - 定义了一系列常量，用于描述资源导入过程中涉及的各种文件名和文件夹名。 
 * - 提供了一组路径，用于指定文件和文件夹的存储位置。 
 * ### 关键点 
 * 1. **文件名常量** 
 * - 包括 `README_NAME`, `OUTLINE_NAME`, `STAGE_EXAM`, `FINAL_EXAM`, `HANDOUT_FILE_NAME`, `DOC_FILE_NAME`, 等等。 
 * 2. **文件夹名常量** 
 * - 包括 `IMAGE_NAME`, `HANDOUT`, `ATTACHMENT`, `VIDEO`, `DOC`, `WRITEUP`, `PRACTICE_ATTACHMENT`, 等等。 
 * 3. **路径常量** 
 * - 包括 `DUMP_ALL_FILE_STORE_PATH`, `DUMP_COURSE_DEFAULT_LOGO_STORE_PATH`, `DUMP_ALL_COURSE_FILE_STORE_PATH`, `DUMP_OJ_FILE_STORE_PATH`, `DEFAULT_LOGO_PATH`。 
 * ### 示例 
 * - `README_NAME = "README.md";` 
 * - 表示资源导入中的一个文件名是 `README.md`。 
 * - `DUMP_ALL_FILE_STORE_PATH = "/data/resource/export";` 
 * - 表示所有资源导入导出文件将存放在 `/data/resource/export` 目录下。 
 * ### 总结 
 * 这段代码的主要目的是提供一组常量，方便在代码中引用特定的文件名、文件夹名和路径。这有助于提高代码的可读性和可维护性，避免硬编码带来的问题。 
 * ### 可能的改进 
 * - 可以考虑使用枚举（`enum`）来替代接口，因为这些常量之间没有方法实现，只是简单的字符串值。 
 * - 如果某些路径或文件名需要根据环境变量进行动态修改，可以考虑使用配置文件或者系统属性来进行管理。 
 * ```java 
 * // 改进后的代码示例 
 * public enum ResourceDumpConstant { 
 * README_NAME("README.md"), 
 * OUTLINE_NAME("课程大纲.md"), 
 * // 其他常量... 
 * DUMP_ALL_FILE_STORE_PATH("/data/resource/export"), 
 * DUMP_COURSE_DEFAULT_LOGO_STORE_PATH("/cpms/built_in/logo.png"); 
 * private final String value; 
 * ResourceDumpConstant(String value) { 
 * this.value = value; 
 * } 
 * public String getValue() { 
 * return value; 
 * } 
 * } 
 * ``` 
 * 这种方式可以使代码更加简洁，并且更容易管理和扩展。 
**/
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
