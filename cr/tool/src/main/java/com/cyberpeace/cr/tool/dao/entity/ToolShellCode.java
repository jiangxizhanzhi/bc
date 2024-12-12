package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 工具壳代号
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_tool_shellcode")
public class ToolShellCode extends BaseToolTable {

	/**
	 * 大小
	 */
	private String size;

	/**
	 * 发表
	 */
	private String published;

	/**
	 * EDB 验证
	 */
	private Boolean edbVerified;

	/**
	 * shell代码
	 */
	private String shellcode;

	/**
	 * 下载 URL
	 */
	@TableField(exist = false)
	private String downloadUrl;

	public String getDownloadUrl() {
		if (StringUtils.isBlank(shellcode)) {
			return "";
		}
		return ConfigUtil.getContextProperty("cr.local.file.prefix") + shellcode;
	}

	/**
	 * 串行版本 UID
	 */
	private static final long serialVersionUID = 1L;
}