package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImageUploadRsp implements Serializable {

	@ApiModelProperty("图片id，请在后台创建和修改任务接口附上")
	private String imageId;

	@ApiModelProperty("用于插入write up 文档的图片链接")
	private String url;

	public ImageUploadRsp(String imageId, String url) {
		this.imageId = imageId;
		this.url = url;
	}
}
