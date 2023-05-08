package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导入动效方案请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class ImportDynamicSchemeReq implements Serializable {

	/**
	 * 文件
	 */
	@ApiModelProperty(value = "文件", required = true)
	private MultipartFile file;

	/**
	 * 场景id
	 */
	@ApiModelProperty(value = "场景id", name = "scene_id", required = true)
	@NotBlank
	private String scene_id;

	/**
	 * 是否为实例场景
	 */
	@ApiModelProperty(value = "是否为实例场景", name = "is_instance")
	private Boolean is_instance;

	/**
	 * 获取场景id
	 *
	 * @return {@code String}
	 */
	public String getSceneId() {
		return this.scene_id;
	}

	/**
	 * 是否为实例场景
	 *
	 * @return {@code Boolean}
	 */
	public Boolean getIsInstance() {
		return this.is_instance;
	}

}
