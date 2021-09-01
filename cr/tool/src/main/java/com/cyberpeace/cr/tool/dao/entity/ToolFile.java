package com.cyberpeace.cr.tool.dao.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
@TableName("cr_tool_toolfile")
public class ToolFile implements Serializable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String fileName;

	private String savePath;

	@TableField(exist = false)
	private String fileUrl;

	public String getFileUrl() {
		if (StringUtils.isBlank(savePath)) {
			return "";
		}
		return ConfigUtil.getContextProperty("cr.uploaded_file_prefix") + savePath;
	}
}
