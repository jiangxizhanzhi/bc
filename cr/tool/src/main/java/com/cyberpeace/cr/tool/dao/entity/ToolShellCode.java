package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 工具壳代号
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseToolTable
 * @since 1.0.0
 */
@Data
@TableName("cr_tool_shellcode")
public class ToolShellCode extends BaseToolTable {

	/**
	 * 大小
	 *
	 * @see String
	 */
	private String size;

	/**
	 * 发表
	 *
	 * @see String
	 */
	private String published;

	/**
	 * EDB 验证
	 *
	 * @see Boolean
	 */
	private Boolean edbVerified;

	/**
	 * shell代码
	 *
	 * @see String
	 */
	private String shellcode;

	/**
	 * 下载 URL
	 *
	 * @see String
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