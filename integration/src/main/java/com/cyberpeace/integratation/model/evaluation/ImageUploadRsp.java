package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Data;

/**
 * 图像上传响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ImageUploadRsp implements Serializable {

	/**
	 * 网址
	 *
	 * @see String
	 */
	private String url;
}
