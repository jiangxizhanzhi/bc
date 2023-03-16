package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.situation.dao.entity.SituationDbTool;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DbToolRes extends SituationDbTool implements Serializable {

	@ApiModelProperty("文件名称")
	private String fileName;

	@ApiModelProperty("文件路径")
	private String filePath;

	public void setFilePath(String filePath) {
		this.filePath = ConfigUtil.getContextProperty("cr.local.file.prefix") + filePath;
	}

}