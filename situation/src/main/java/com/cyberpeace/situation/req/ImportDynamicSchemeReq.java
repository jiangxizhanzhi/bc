package com.cyberpeace.situation.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class ImportDynamicSchemeReq {

	@ApiModelProperty(value = "文件", required = true)
	private MultipartFile file;

	@ApiModelProperty(value = "场景id", name = "scene_id", required = true)
	@NotBlank
	private String scene_id;

	@ApiModelProperty(value = "是否为实例场景", name = "is_instance")
	private Boolean is_instance;

	public String getSceneId() {
		return this.scene_id;
	}

	public Boolean getIsInstance() {
		return this.is_instance;
	}

}
