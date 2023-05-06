package com.cyberpeace.common.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 文件sunffix常数
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum FileSunffixConstant implements GenericEnum {

	/**
	 * 文件后缀类型枚举
	 */
	MD(0, "md"), PDF(1, "pdf"), HTML(2, "html"), ZIP(3, "zip"), XLS(4, "xls"), XLSX(5,
			"xlsx"), PPTX(6, "pptx"), PPT(7, "ppt"), DOCX(8, "docx"), DOC(9,
					"doc"), PNG(10, "png"), JPEG(11, "jpeg"), JPG(12,
							"jpg"), MP4(13, "mp4"), M4V(13, "m4v"), JSON(15, "json");

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * desc
	 */
	@Getter
	private String desc;

	/**
	 * 文件sunffix常数
	 *
	 * @param code 代码
	 * @param desc desc
	 */
	FileSunffixConstant(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
