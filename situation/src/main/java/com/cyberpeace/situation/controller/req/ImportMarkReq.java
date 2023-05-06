package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导入标绘请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class ImportMarkReq implements Serializable {

	/**
	 * 文件
	 */
	@ApiModelProperty(value = "文件", required = true)
	private MultipartFile file;

	/**
	 * 大屏ID
	 */
	@ApiModelProperty(value = "大屏ID", name = "dashboard_id", required = true)
	@NotBlank
	private String dashboard_id;

	/**
	 * 获取大屏ID
	 *
	 * @return {@code String}
	 */
	public String getDashboardId() {
		return this.dashboard_id;
	}

}
