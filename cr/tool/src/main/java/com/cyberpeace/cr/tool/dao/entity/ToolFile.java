package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.util.ConfigUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
@TableName("cr_tool_toolfile")
public class ToolFile {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty("文件名称")
	private String fileName;

	@ApiModelProperty("文件保存路径")
	private String savePath;

	@ApiModelProperty("文件链接")
	@TableField(exist = false)
	private String fileUrl;

	@ApiModelProperty("版本")
	private String version;

	@ApiModelProperty("创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty("更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;


	public String getFileUrl() {
		if (StringUtils.isBlank(savePath)) {
			return "";
		}
		return ConfigUtil.getContextProperty("cr.local.file.prefix") + savePath;
	}
}
