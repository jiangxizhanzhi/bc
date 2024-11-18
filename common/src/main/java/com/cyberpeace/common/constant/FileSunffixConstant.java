package com.cyberpeace.common.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 文件 sunffix 常量
 *
 * @author SN
 * @version 1.0.0
 * @see Enum
 * @see Enum
 * @since 1.0.0
 * @date 2024/11/07
 */
public enum FileSunffixConstant implements GenericEnum {

	/**
	 * 文件后缀类型枚举
	 */
	MD(0, "md"),
	/**
	 * PDF格式
	 */
	PDF(1, "pdf"),
	/**
	 * HTML格式
	 */
	HTML(2, "html"),
	/**
	 * 邮编
	 */
	ZIP(3, "zip"),
	/**
	 * XLS
	 */
	XLS(4, "xls"),
	/**
	 * XLSX
	 */
	XLSX(5, "xlsx"),
	/**
	 * PPTX
	 */
	PPTX(6, "pptx"),
	/**
	 * PPT
	 */
	PPT(7, "ppt"),
	/**
	 * docx
	 */
	DOCX(8, "docx"),
	/**
	 * 医生
	 */
	DOC(9,"doc"),
	/**
	 * PNG 格式
	 */
	PNG(10, "png"),
	/**
	 * jpeg 格式
	 */
	JPEG(11, "jpeg"),
	/**
	 * jpg 格式
	 */
	JPG(12, "jpg"),
	/**
	 * MP4
	 */
	MP4(13, "mp4"),
	/**
	 * M4V
	 */
	M4V(13, "m4v"),
	/**
	 * RAR
	 */
	RAR(15,"rar");

	/**
	 * 代号
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	/**
	 * 文件 sunffix 常量
	 *
	 * @param code 代号
	 * @param desc 描述
	 * @throws
	 * @see Integer
	 * @see Integer
	 */
	FileSunffixConstant(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
