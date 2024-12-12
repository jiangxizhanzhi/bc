package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 图像上传响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ImageUploadRsp implements Serializable {

	/**
	 * 网址
	 */
	private String url;
}
