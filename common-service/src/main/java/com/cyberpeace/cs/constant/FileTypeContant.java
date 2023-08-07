package com.cyberpeace.cs.constant;

public interface FileTypeContant {

	/** 文件类型后缀*/
	String PICTURE_TYPE = "JPEG,PNG,JPG";

	/** 课时讲义允许上传类型*/
	String LESSON_HANDOUT_TYPE = "ZIP,PDF,DOC,DOCX,PPT,PPTX,XLS,XLSX";

	/** 课程、习题集导入文件上传类型*/
	String RESOURCE_DATA_IMPORT_TYPE = "ZIP";

	/** markdown编辑器附件上传类型*/
	String MD_ATTACHMENT_TYPE = "ZIP,RAR";

	/** 课时附件上传限制类型*/
	String LESSON_ATTACHMENT = "ZIP,RAR";

	/** 实操附件上传限制类型*/
	String OPERATION_PRACTICE_ATTACHMENT = "ZIP";

	/** 工具库文件上传*/
	String TOOL_ATTACHMENT = "ZIP,RAR";

	/**
	 * 课时讲义文件上传
	 */
	String VIDEO_ATTACHMENT_TYPE = "MP4,M4V";

	/**
	 * 课时视频文件上传
	 */
	String HANDOUT_ATTACHMENT_TYPE = "PDF,ZIP,DOC,DOCX,PPT,PPTX";

	/** 训练公告附件上传*/
	String TRAIN_NOTICE_ATTACHMENT = "ZIP";

	/** 后台评估指标项文件上传 */
	String CR_EVAL_INDICATOR_ATTACHMENT = "JSON";

	/** flag任务限制类型*/
	String TASK_FLAG_ATTACHMENT = "ZIP";
}
