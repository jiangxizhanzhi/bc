package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImportMarkReq implements Serializable {

	@ApiModelProperty(value = "文件", required = true)
	private MultipartFile file;

	@ApiModelProperty(value = "大屏ID", name = "dashboard_id", required = true)
	@NotBlank
	private String dashboard_id;

	public String getDashboardId() {
		return this.dashboard_id;
	}

}
