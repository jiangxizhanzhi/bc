package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.situation.dao.entity.SituationDbTool;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据库工具返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DbToolRes extends SituationDbTool implements Serializable {

	/**
	 * 文件名称
	 */
	@ApiModelProperty("文件名称")
	private String fileName;

	/**
	 * 文件路径
	 */
	@ApiModelProperty("文件路径")
	private String filePath;

	/**
	 * 设置文件路径
	 *
	 * @param filePath 文件路径
	 */
	public void setFilePath(String filePath) {
		this.filePath = ConfigUtil.getContextProperty("cr.local.file.prefix") + filePath;
	}

}