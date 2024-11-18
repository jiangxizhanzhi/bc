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

/**
 * 工具文件
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@TableName("cr_tool_toolfile")
public class ToolFile implements Serializable {

	/**
	 * id
	 *
	 * @see Integer
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 文件名称
	 *
	 * @see String
	 */
	@ApiModelProperty("文件名称")
	private String fileName;

	/**
	 * 保存路径
	 *
	 * @see String
	 */
	@ApiModelProperty("文件保存路径")
	private String savePath;

	/**
	 * 文件 URL
	 *
	 * @see String
	 */
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
