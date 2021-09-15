package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@TableName("cr_tool_shellcode")
public class ToolShellCode extends BaseToolTable {

	private String size;

	private String published;

	private Boolean edbVerified;

	private String shellcode;

	@TableField(exist = false)
	private String downloadUrl;

	public String getDownloadUrl() {
		if (StringUtils.isBlank(shellcode)) {
			return "";
		}
		return ConfigUtil.getContextProperty("cr.local.file.prefix") + shellcode;
	}

	private static final long serialVersionUID = 1L;
}