package com.cyberpeace.cr.tool.service.context;

import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 战术工具
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TacticTool {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	private String type;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "工具类型")
	private String category;

	/**
	 * 工具类型名称
	 */
	@ApiModelProperty(value = "工具类型")
	private String categoryName;

	/**
	 * 工具类型名称
	 */
	@ApiModelProperty(value = "工具类型名称")
	private String categoryI18nName;

	/**
	 * 介绍
	 */
	@ApiModelProperty("介绍")
	private String introduction;

	@ApiModelProperty("平台名称")
	private List<String> platforms;

	@ApiModelProperty("工具文件")
	private List<ToolFile> toolFiles;
}
