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
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@ApiModel("工具库信息")
@Data
public class ToolRsp implements Serializable {

	/**
	 * 类别i18n名称
	 *
	 * @see String
	 */
	@ApiModelProperty("多个分类i18n名称逗号分割")
	private String categoryI18nName;

	/**
	 * 破获
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否破解")
	private Boolean cracked;

	/**
	 * 装置名称
	 *
	 * @see String
	 */
	@ApiModelProperty("靶标名称")
	private String deviceName;

	/**
	 * id
	 *
	 * @see Integer
	 */
	private Integer id;

	/**
	 * 语言
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("工具语言")
	private List<String> language;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 平台
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("平台名称")
	private List<String> platforms;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("ID")
	private String resourceId;

	/**
	 * 工具类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("工具类型")
	private Integer toolType;

	/**
	 * 类别
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("工具分类")
	private List<String> category;

	/**
	 * 盖
	 *
	 * @see String
	 */
	@ApiModelProperty("封面")
	private String cover;

	/**
	 * 封面 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("封面连接")
	private String coverUrl;

	/**
	 * 文件
	 *
	 * @see String
	 */
	@ApiModelProperty("附件列表")
	private String files;

	/**
	 * 工具文件
	 *
	 * @see List<ToolFile>
	 */
	@ApiModelProperty("附件详细列表")
	private List<ToolFile> toolFiles;

	/**
	 * 介绍
	 *
	 * @see String
	 */
	@ApiModelProperty("介绍")
	private String introduction;

	/**
	 * 版本
	 *
	 * @see String
	 */
	@ApiModelProperty("版本")
	private String version;

	/**
	 * 可见
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否可见")
	private Boolean visible;

	/**
	 * 创建时间
	 *
	 * @see String
	 */
	@ApiModelProperty("创建时间")
	private String createTime;

	/**
	 * 更新时间
	 *
	 * @see String
	 */
	@ApiModelProperty("更新时间")
	private String updateTime;

	/**
	 * 装置资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("设备资源ID")
	private String deviceResourceId = "";

	/**
	 * 家分页
	 *
	 * @see String
	 */
	@ApiModelProperty("主页")
	private String homePage = "";

	/**
	 * 图像资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("镜像资源ID")
	private String imageResourceId = "";

	/**
	 * 手动
	 *
	 * @see String
	 */
	@ApiModelProperty("手册")
	private String manual;

	/**
	 * 场景
	 *
	 * @see String
	 */
	@ApiModelProperty("场景ID")
	private String scene;

	/**
	 * 场景数据
	 *
	 * @see String
	 */
	@ApiModelProperty("场景数据")
	private String sceneData;

	/**
	 * 标记
	 *
	 * @see String
	 */
	@ApiModelProperty("标签")
	private String tag;
}
