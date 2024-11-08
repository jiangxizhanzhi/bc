package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Data;

/**
 * 图像上传响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ImageUploadRsp implements Serializable {

	/**
	 * 网址
	 */
	private String url;
}
