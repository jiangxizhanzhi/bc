package com.cyberpeace.cr.tool.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.tool.dao.entity.ToolFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("工具库信息")
@Data
public class ToolRsp implements Serializable {

	@ApiModelProperty("多个分类i18n名称逗号分割")
	private String categoryI18nName;

	@ApiModelProperty("是否破解")
	private Boolean cracked;

	@ApiModelProperty("靶标名称")
	private String deviceName;

	private Integer id;

	@ApiModelProperty("工具语言")
	private List<String> language;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("平台名称")
	private List<String> platforms;

	@ApiModelProperty("发布时间")
	private String publishTime;

	private String resourceId;

	@ApiModelProperty("工具类型")
	private Integer toolType;

	@ApiModelProperty("工具分类")
	private List<String> category;

	@ApiModelProperty("封面")
	private String cover;

	@ApiModelProperty("封面连接")
	private String coverUrl;

	@ApiModelProperty("附件列表")
	private List<Integer> files;

	@ApiModelProperty("附件详细列表")
	private List<ToolFile> toolFiles;

	@ApiModelProperty("介绍")
	private String introduction;

	@ApiModelProperty("版本")
	private String version;

	@ApiModelProperty("是否可见")
	private Boolean visible;

	private String createTime;

	private String updateTime;

	private String deviceResourceId = "";

	private String homePage = "";

	private String imageResourceId = "";

	private String manual;

	private String scene;

	private String sceneData;

	private String tag;
}
