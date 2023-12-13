package com.cyberpeace.cs.constant;

public final class FileTypeContant {

	/** 文件类型后缀*/
	public static final String PICTURE_TYPE = "JPEG,PNG,JPG";

	/** 课时讲义允许上传类型*/
	public static final String LESSON_HANDOUT_TYPE = "ZIP,PDF,DOC,DOCX,PPT,PPTX,XLS,XLSX";

	/** 课程、习题集导入文件上传类型*/
	public static final String RESOURCE_DATA_IMPORT_TYPE = "ZIP";

	/** markdown编辑器附件上传类型*/
	public static final String MD_ATTACHMENT_TYPE = "ZIP,RAR";

	/** 课时附件上传限制类型*/
	public static final String LESSON_ATTACHMENT = "ZIP,RAR";

	/** 实操附件上传限制类型*/
	public static final String OPERATION_PRACTICE_ATTACHMENT = "ZIP";

	/** 工具库文件上传*/
	public static final String TOOL_ATTACHMENT = "ZIP,RAR";

	/**
	 * 课时讲义文件上传
	 */
	public static final String VIDEO_ATTACHMENT_TYPE = "MP4,M4V";

	/**
	 * 课时视频文件上传
	 */
	public static final String HANDOUT_ATTACHMENT_TYPE = "PDF,ZIP,DOC,DOCX,PPT,PPTX";

	/**
	 * 后台测试模板
	 */
	public static final String MISSION_SCHEME_TYPE = "DOC,DOCX,XML,PDF,HTML";

	private FileTypeContant() {}

}
