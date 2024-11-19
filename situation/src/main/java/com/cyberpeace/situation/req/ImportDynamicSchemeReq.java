package com.cyberpeace.situation.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导入动态方案请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class ImportDynamicSchemeReq {

	/**
	 * 文件
	 *
	 * @see MultipartFile
	 */
	@ApiModelProperty(value = "文件", required = true)
	private MultipartFile file;

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景id", name = "scene_id", required = true)
	@NotBlank
	private String scene_id;

	/**
	 * 是否为实例场景
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否为实例场景", name = "is_instance")
	private Boolean is_instance;

	public String getSceneId() {
		return this.scene_id;
	}

	public Boolean getIsInstance() {
		return this.is_instance;
	}

}
