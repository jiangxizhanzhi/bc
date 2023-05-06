package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * 导入颜色主题请求
 *
 * @Author xuxz
 * @Date 13/12/2022 下午5:32
 */
@Data
public class ColorThemeImportReq implements Serializable {

	/**
	 * 颜色主题名称
	 */
	private String name;

	/**
	 * 颜色主题文件
	 */
	private MultipartFile file;
}
