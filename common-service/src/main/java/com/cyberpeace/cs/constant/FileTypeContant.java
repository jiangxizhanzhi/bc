package com.cyberpeace.cs.constant;

/**
 * 文件类型 contant
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface FileTypeContant {

	/**
	 * 文件类型后缀
	 *
	 * @see String
	 */
	String PICTURE_TYPE = "JPEG,PNG,JPG";

	/**
	 * 课时讲义允许上传类型
	 *
	 * @see String
	 */
	String LESSON_HANDOUT_TYPE = "ZIP,PDF,DOC,DOCX,PPT,PPTX,XLS,XLSX";

	/**
	 * 课程、习题集导入文件上传类型
	 *
	 * @see String
	 */
	String RESOURCE_DATA_IMPORT_TYPE = "ZIP";

	/**
	 * markdown编辑器附件上传类型
	 *
	 * @see String
	 */
	String MD_ATTACHMENT_TYPE = "ZIP,RAR";

	/**
	 * 课时附件上传限制类型
	 *
	 * @see String
	 */
	String LESSON_ATTACHMENT = "ZIP,RAR";

	/**
	 * 实操附件上传限制类型
	 *
	 * @see String
	 */
	String OPERATION_PRACTICE_ATTACHMENT = "ZIP";

	/**
	 * 工具库文件上传
	 *
	 * @see String
	 */
	String TOOL_ATTACHMENT = "ZIP,RAR";

	/**
	 * 课时讲义文件上传
	 *
	 * @see String
	 */
	String VIDEO_ATTACHMENT_TYPE = "MP4,M4V";

	/**
	 * 课时视频文件上传
	 *
	 * @see String
	 */
	String HANDOUT_ATTACHMENT_TYPE = "PDF,ZIP,DOC,DOCX,PPT,PPTX";

}
