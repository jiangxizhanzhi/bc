package com.cyberpeace.cr.tool.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.tool.dao.entity.ToolFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工具响应
 *
 * @author SN
 * @date 2024/11/14
 */
@ApiModel("工具库信息")
@Data
public class ToolRsp implements Serializable {

	/**
	 * 多个分类i18n名称逗号分割
	 */
	@ApiModelProperty("多个分类i18n名称逗号分割")
	private String categoryI18nName;

	/**
	 * 是否破解
	 */
	@ApiModelProperty("是否破解")
	private Boolean cracked;

	/**
	 * 靶标名称
	 */
	@ApiModelProperty("靶标名称")
	private String deviceName;

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 工具语言
	 */
	@ApiModelProperty("工具语言")
	private List<String> language;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 平台名称
	 */
	@ApiModelProperty("平台名称")
	private List<String> platforms;

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private String resourceId;

	/**
	 * 工具类型
	 */
	@ApiModelProperty("工具类型")
	private Integer toolType;

	/**
	 * 工具分类
	 */
	@ApiModelProperty("工具分类")
	private List<String> category;

	/**
	 * 封面
	 */
	@ApiModelProperty("封面")
	private String cover;

	/**
	 * 封面连接
	 */
	@ApiModelProperty("封面连接")
	private String coverUrl;

	/**
	 * 附件列表
	 */
	@ApiModelProperty("附件列表")
	private String files;

	/**
	 * 附件详细列表
	 */
	@ApiModelProperty("附件详细列表")
	private List<ToolFile> toolFiles;

	/**
	 * 介绍
	 */
	@ApiModelProperty("介绍")
	private String introduction;

	/**
	 * 版本
	 */
	@ApiModelProperty("版本")
	private String version;

	/**
	 * 是否可见
	 */
	@ApiModelProperty("是否可见")
	private Boolean visible;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private String createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private String updateTime;

	/**
	 * 设备资源ID
	 */
	@ApiModelProperty("设备资源ID")
	private String deviceResourceId = "";

	/**
	 * 主页
	 */
	@ApiModelProperty("主页")
	private String homePage = "";

	/**
	 * 镜像资源ID
	 */
	@ApiModelProperty("镜像资源ID")
	private String imageResourceId = "";

	/**
	 * 手册
	 */
	@ApiModelProperty("手册")
	private String manual;

	/**
	 * 场景ID
	 */
	@ApiModelProperty("场景ID")
	private String scene;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景数据")
	private String sceneData;

	/**
	 * 标签
	 */
	@ApiModelProperty("标签")
	private String tag;
}
