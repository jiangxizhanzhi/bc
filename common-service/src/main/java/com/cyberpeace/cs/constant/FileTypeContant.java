package com.cyberpeace.cs.constant;

/**
 * 文件类型 contant
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `FileTypeContant` 的 Java 接口，用于存储不同类型的文件后缀常量。以下是代码的简要分析： 
 * 1. **接口定义**： 
 * - 接口 `FileTypeContant` 是一个常量接口，其中所有字段都是 `public static final` 的字符串常量。 
 * - 这种设计通常用于集中管理应用程序中使用的文件类型常量。 
 * 2. **字段说明**： 
 * - 每个字段都描述了一组允许的文件类型后缀，用逗号分隔。 
 * - 字段名称具有语义化，便于理解其用途，例如 `PICTURE_TYPE` 表示图片类型，`LESSON_HANDOUT_TYPE` 表示课时讲义类型等。 
 * 3. **具体字段**： 
 * - `PICTURE_TYPE`：支持的图片格式（JPEG, PNG, JPG, SVG）。 
 * - `LESSON_HANDOUT_TYPE`：课时讲义支持的格式（ZIP, PDF, DOC, DOCX, PPT, PPTX, XLS, XLSX）。 
 * - `RESOURCE_DATA_IMPORT_TYPE`：课程或习题集导入支持的格式（ZIP）。 
 * - `MD_ATTACHMENT_TYPE`：Markdown 编辑器附件支持的格式（ZIP, RAR）。 
 * - `LESSON_ATTACHMENT`：课时附件支持的格式（ZIP, RAR）。 
 * - `OPERATION_PRACTICE_ATTACHMENT`：实操附件支持的格式（ZIP）。 
 * - `TOOL_ATTACHMENT`：工具库文件支持的格式（ZIP, RAR）。 
 * - `VIDEO_ATTACHMENT_TYPE`：课时视频支持的格式（MP4, M4V）。 
 * - `HANDOUT_ATTACHMENT_TYPE`：课时讲义附件支持的格式（PDF, ZIP, DOC, DOCX, PPT, PPTX）。 
 * 4. **优点**： 
 * - 集中管理文件类型常量，便于维护和统一使用。 
 * - 提高代码的可读性和可维护性。 
 * 5. **潜在改进**： 
 * - 可以考虑将这些常量放入枚举类中，以提供更强的类型安全性和灵活性。 
 * - 如果某些字段可能动态变化，可以考虑将其存储在配置文件中，而不是硬编码。 
 * 总结：该接口通过常量字段集中管理了多种文件类型的后缀，便于在项目中统一使用和维护。 
**/
public interface FileTypeContant {

	/**
	 * 文件类型后缀
	 */
	String PICTURE_TYPE = "JPEG,PNG,JPG,SVG";

	/**
	 * 课时讲义允许上传类型
	 */
	String LESSON_HANDOUT_TYPE = "ZIP,PDF,DOC,DOCX,PPT,PPTX,XLS,XLSX";

	/**
	 * 课程、习题集导入文件上传类型
	 */
	String RESOURCE_DATA_IMPORT_TYPE = "ZIP";

	/**
	 * markdown编辑器附件上传类型
	 */
	String MD_ATTACHMENT_TYPE = "ZIP,RAR";

	/**
	 * 课时附件上传限制类型
	 */
	String LESSON_ATTACHMENT = "ZIP,RAR";

	/**
	 * 实操附件上传限制类型
	 */
	String OPERATION_PRACTICE_ATTACHMENT = "ZIP";

	/**
	 * 工具库文件上传
	 */
	String TOOL_ATTACHMENT = "ZIP,RAR";

	/**
	 * 课时讲义文件上传
	 */
	String VIDEO_ATTACHMENT_TYPE = "MP4,M4V";

	/**
	 * 课时视频文件上传
	 */
	String HANDOUT_ATTACHMENT_TYPE = "PDF,ZIP,DOC,DOCX,PPT,PPTX";

}

