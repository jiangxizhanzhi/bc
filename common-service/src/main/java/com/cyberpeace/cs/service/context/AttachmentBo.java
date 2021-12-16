package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.cyberpeace.common.util.ConfigUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("文件名称")
	private String fileName;

	@ApiModelProperty("文件路径")
	private String filePath;

	@ApiModelProperty("附件公开与隐藏状态 true=公开 false=隐藏")
	private Boolean isPublic;

	public String getFilePath() {
		return ConfigUtil.getContextProperty("cr.local.file.prefix") + filePath;
	}

}
