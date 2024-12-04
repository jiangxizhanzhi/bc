package com.cyberpeace.common.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 文件 sunffix 常量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum FileSunffixConstant implements GenericEnum {

	/**
	 * 文件后缀类型枚举
	 *
	 * @see FileSunffixConstant
	 */
	MD(0, "md"),
	/**
	 * PDF格式
	 *
	 * @see FileSunffixConstant
	 */
	PDF(1, "pdf"),
	/**
	 * HTML格式
	 *
	 * @see FileSunffixConstant
	 */
	HTML(2, "html"),
	/**
	 * 邮编
	 *
	 * @see FileSunffixConstant
	 */
	ZIP(3, "zip"),
	/**
	 * XLS
	 *
	 * @see FileSunffixConstant
	 */
	XLS(4, "xls"),
	/**
	 * XLSX
	 *
	 * @see FileSunffixConstant
	 */
	XLSX(5, "xlsx"),
	/**
	 * PPTX
	 *
	 * @see FileSunffixConstant
	 */
	PPTX(6, "pptx"),
	/**
	 * PPT
	 *
	 * @see FileSunffixConstant
	 */
	PPT(7, "ppt"),
	/**
	 * docx
	 *
	 * @see FileSunffixConstant
	 */
	DOCX(8, "docx"),
	/**
	 * 医生
	 *
	 * @see FileSunffixConstant
	 */
	DOC(9,"doc"),
	/**
	 * PNG 格式
	 *
	 * @see FileSunffixConstant
	 */
	PNG(10, "png"),
	/**
	 * jpeg 格式
	 *
	 * @see FileSunffixConstant
	 */
	JPEG(11, "jpeg"),
	/**
	 * jpg 格式
	 *
	 * @see FileSunffixConstant
	 */
	JPG(12, "jpg"),
	/**
	 * MP4
	 *
	 * @see FileSunffixConstant
	 */
	MP4(13, "mp4"),
	/**
	 * M4V
	 *
	 * @see FileSunffixConstant
	 */
	M4V(13, "m4v"),
	/**
	 * RAR
	 *
	 * @see FileSunffixConstant
	 */
	RAR(15,"rar");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	FileSunffixConstant(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
