package com.cyberpeace.cr.tool.dao.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.ConfigUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
@TableName("cr_tool_toolfile")
public class ToolFile implements Serializable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty("文件名称")
	private String fileName;

	@ApiModelProperty("文件保存路径")
	private String savePath;

	@ApiModelProperty("文件链接")
	@TableField(exist = false)
	private String fileUrl;

	public String getFileUrl() {
		if (StringUtils.isBlank(savePath)) {
			return "";
		}
		return ConfigUtil.getContextProperty("cr.local.file.prefix") + savePath;
	}
}
